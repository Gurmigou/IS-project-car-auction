package com.example.backendlab.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarLotWithoutAuctionForICDto {
    private Long lotId;
    private String carMake;
    private String carModel;
    private String image;
    private String damageDescription;
    private String insuranceCompany;
    private String carState;
    private String vin;
}
