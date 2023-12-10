package com.example.backendlab.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarAuctionInfoForIC {
    private Long auctionId;
    private Long lotId;
    private String carMake;
    private String carModel;
    private String vin;
    private String damageDescription;
    private String carState;
    private String image;
    private Integer initialPrice;
    private String auctionStartDate;
    private String auctionDuration;
    private String timeLeft;
    private List<BidDtoForAuctionIC> bidsList;
}
