package com.example.backendlab.service;

import com.example.backendlab.dto.CarAuctionDetailedInfo;
import com.example.backendlab.dto.CarAuctionShortInfoDto;
import com.example.backendlab.model.*;
import com.example.backendlab.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class CarAuctionService {
    private final CarAuctionRepository carAuctionRepository;
    private final CarLotRepository carLotRepository;
    private final CarBidRepository carBidRepository;
    private final UserRepository userRepository;
    private final BoughtCarLotRepository boughtCarLotRepository;

    @Autowired
    public CarAuctionService(CarAuctionRepository carAuctionRepository,
                             CarLotRepository carLotRepository,
                             CarBidRepository carBidRepository,
                             UserRepository userRepository,
                             BoughtCarLotRepository boughtCarLotRepository) {
        this.carAuctionRepository = carAuctionRepository;
        this.carLotRepository = carLotRepository;
        this.carBidRepository = carBidRepository;
        this.userRepository = userRepository;
        this.boughtCarLotRepository = boughtCarLotRepository;
    }

    public List<CarAuctionShortInfoDto> getActiveAuctions() {
        return carAuctionRepository.findAllNotFinished()
                .stream()
                .map(this::mapToCarAuctionShortInfoDto)
                .toList();
    }

    @Transactional
    public void changeAuctionStatus(Long auctionId, CarAuctionStatus status) {
        CarAuction carAuction = carAuctionRepository.findById(auctionId)
                .orElseThrow(() -> new RuntimeException("Car auction not found"));
        carAuction.setStatus(status);
        carAuctionRepository.save(carAuction);

        if (status == CarAuctionStatus.APPROVED) {
            CarBid maxCarBid = carBidRepository.findMaxCarBidByCarAuctionId(auctionId);
            var boughtCarLot = new BoughtCarLot();
            boughtCarLot.setCarLot(carAuction.getCarLot());
            boughtCarLot.setUser(maxCarBid.getUser());
            boughtCarLot.setFinalPrice(maxCarBid.getBidAmount());
            boughtCarLotRepository.save(boughtCarLot);
        }
    }

    protected CarAuctionDetailedInfo mapToCarAuctionDetailedInfo(CarAuction carAuction) {
        var carAuctionDto = new CarAuctionDetailedInfo();
        carAuctionDto.setAuctionId(carAuction.getId());
        carAuctionDto.setInitialPrice(carAuction.getInitialPrice());
        carAuctionDto.setAuctionStartDate(carAuction.getAuctionStart().toString());
        carAuctionDto.setAuctionDuration(carAuction.getAuctionDurationHours().toString() + " hours");
        carAuctionDto.setTimeLeft(CarCommonUtil.getAuctionTimeLeft(
                LocalDateTime.now(), carAuction.getAuctionStart(),
                carAuction.getAuctionDurationHours()));

        Integer currentPrice = carBidRepository
                .findMaxBidForCarAuctionId(carAuction.getId());
        carAuctionDto.setCurrentPrice(currentPrice == null ? 0 : currentPrice);
        return carAuctionDto;
    }

    private CarAuctionShortInfoDto mapToCarAuctionShortInfoDto(CarAuction carAuction) {
        CarLot carLot = carAuction.getCarLot();
        CarAuctionShortInfoDto shortInfoDto = new CarAuctionShortInfoDto();
        shortInfoDto.setLotId(carLot.getId());
        shortInfoDto.setCarMake(carLot.getCarModel().getCarMake().getName());
        shortInfoDto.setCarModel(carLot.getCarModel().getName());

        shortInfoDto.setInitialPrice(carAuction.getInitialPrice());
        shortInfoDto.setCurrentPrice(carBidRepository.findMaxBidForCarAuctionId(carAuction.getId()));

        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(carAuction.getAuctionStart())) {
            shortInfoDto.setTimeLeft("Not started yet");
        } else {
            shortInfoDto.setTimeLeft(
                    getAuctionTimeLeft(carAuction.getAuctionStart(),
                            carAuction.getAuctionDurationHours()) + " hours");
        }

        shortInfoDto.setInsuranceCompanyName(carLot.getInsuranceCompany().getName());
        return shortInfoDto;
    }

    private Long getAuctionTimeLeft(LocalDateTime auctionStart, Integer auctionDurationHours) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime auctionEndDateTime = auctionStart.plusHours(auctionDurationHours);

        Duration remainingTime = Duration.between(currentDateTime, auctionEndDateTime);
        return remainingTime.toHours();
    }
}
