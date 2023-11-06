package com.example.backendlab.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarAuctionShortInfoDto {
    private Long lotId;
    private String carMake;
    private String carModel;
    private String description;
    private Integer initialPrice;
    private Integer currentPrice;
    private String timeLeft;
    private String insuranceCompanyName;
}
