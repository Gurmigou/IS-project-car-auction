package com.example.backendlab.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarBidCardDto {
    private Long lotId;
    private String carMake;
    private String carModel;
    private String image;
    private String damageDescription;
    private Integer bid;
    private String timeLeft;
    private String insuranceCompany;
}
