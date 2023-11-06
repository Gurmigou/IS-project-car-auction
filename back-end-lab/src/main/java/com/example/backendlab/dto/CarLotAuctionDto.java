package com.example.backendlab.dto;

import com.example.backendlab.model.CarState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarLotAuctionDto {
    private Long id;
    private String carMake;
    private String carModel;
    private String vin;
    private String damageDescription;
    private CarState carState;
    private Integer initialPrice;
    private Integer auctionDurationHours;
    private LocalDateTime auctionStart;
    private Long insuranceCompanyId;
    private Boolean withoutPublish;
}
