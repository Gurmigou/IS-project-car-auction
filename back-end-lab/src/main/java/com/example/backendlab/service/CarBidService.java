package com.example.backendlab.service;

import com.example.backendlab.dto.MyBidDto;
import com.example.backendlab.model.CarAuction;
import com.example.backendlab.model.CarBid;
import com.example.backendlab.repository.CarAuctionRepository;
import com.example.backendlab.repository.CarBidRepository;
import com.example.backendlab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CarBidService {
    private final CarBidRepository carBidRepository;
    private final CarAuctionRepository carAuctionRepository;
    private final UserRepository userRepository;

    @Autowired
    public CarBidService(CarBidRepository carBidRepository,
                         CarAuctionRepository carAuctionRepository,
                         UserRepository userRepository) {
        this.carBidRepository = carBidRepository;
        this.carAuctionRepository = carAuctionRepository;
        this.userRepository = userRepository;
    }


    @Transactional
    public void createNewCarBid(String userEmail, Long auctionId, Integer bid) {
        var carAuction = carAuctionRepository.findById(auctionId).orElseThrow();
        if (!CarCommonUtil.carAuctionIsNotEnded(carAuction)) {
            throw new RuntimeException("Auction is not active");
        }

        Integer maxBidAmount = carBidRepository.findMaxBidForCarAuctionId(carAuction.getId());
        if (Objects.nonNull(maxBidAmount) && maxBidAmount >= bid) {
            throw new RuntimeException("Bid amount must be greater than current price");
        }

        var user = userRepository.findByEmail(userEmail).orElseThrow();

        var carBid = new CarBid();
        carBid.setCarAuction(carAuction);
        carBid.setUser(user);
        carBid.setBidAmount(bid);
        carBidRepository.save(carBid);
    }

    public Integer getMaxBidForCarAuctionId(Long carAuctionId) {
        return carBidRepository.findMaxBidForCarAuctionId(carAuctionId);
    }

    public List<MyBidDto> getMyBids(String userEmail) {
        var user = userRepository.findByEmail(userEmail).orElseThrow();
        Map<Long, MyBidDto> grouper = new HashMap<>();
        user.getCarBids()
                .forEach(carBid -> mapToMyBidDto(grouper, carBid));
        return new ArrayList<>(grouper.values());
    }

    private void mapToMyBidDto(Map<Long, MyBidDto> grouper, CarBid carBid) {
        var carAuction = carBid.getCarAuction();
        if (grouper.containsKey(carAuction.getId())) {
            var myBidDto = grouper.get(carAuction.getId());
            myBidDto.getBidAmounts().add(carBid.getBidAmount());
        } else {
            var carLot = carAuction.getCarLot();
            var carModel = carLot.getCarModel();
            var carMake = carModel.getCarMake();
            var insuranceCompany = carLot.getInsuranceCompany();
            var image = carLot.getCarLotImages().get(0);
            var myBidDto = new MyBidDto(
                    carLot.getId(),
                    carAuction.getId(),
                    carMake.getName(),
                    carModel.getName(),
                    CarCommonUtil.getCarImagedBase64(image),
                    insuranceCompany.getName(),
                    new ArrayList<>()
            );
            myBidDto.getBidAmounts().add(carBid.getBidAmount());
            grouper.put(carAuction.getId(), myBidDto);
        }
    }
}
