package com.example.backendlab.service;

import com.example.backendlab.dto.CarAuctionShortInfoDto;
import com.example.backendlab.model.CarAuction;
import com.example.backendlab.model.CarLot;
import com.example.backendlab.repository.CarAuctionRepository;
import com.example.backendlab.repository.CarBidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CarAuctionService {
    private final CarAuctionRepository carAuctionRepository;
    private final CarBidRepository carBidRepository;

    @Autowired
    public CarAuctionService(CarAuctionRepository carAuctionRepository,
                             CarBidRepository carBidRepository) {
        this.carAuctionRepository = carAuctionRepository;
        this.carBidRepository = carBidRepository;
    }

    public List<CarAuctionShortInfoDto> getActiveAuctions() {
        return carAuctionRepository.findAllNotFinished()
                .stream()
                .map(this::mapToCarAuctionShortInfoDto)
                .toList();
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
