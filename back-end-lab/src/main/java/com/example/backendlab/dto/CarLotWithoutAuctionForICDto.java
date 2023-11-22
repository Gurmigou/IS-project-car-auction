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
}
