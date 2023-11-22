package com.example.backendlab.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarAuctionCardDto {
    private Long lotId;
    private String carMake;
    private String carModel;
    private String image;
    private String damageDescription;
    private Integer currentPrice;
    private String timeLeft;
    private String insuranceCompany;
}
