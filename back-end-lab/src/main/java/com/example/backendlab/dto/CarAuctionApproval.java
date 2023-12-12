package com.example.backendlab.dto;

import com.example.backendlab.model.CarAuctionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarAuctionApproval {
    private Long auctionId;
    private String carMake;
    private String carModel;
    private String vin;
    private String damageDescription;
    private String carState;
    private String insuranceCompany;
    private Integer finalPrice;
    private String userName;
    private String image;
    private CarAuctionStatus status;
}
