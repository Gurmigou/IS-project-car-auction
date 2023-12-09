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
public class MyBidDto {
    private Long lotId;
    private Long auctionId;
    private String carMake;
    private String carModel;
    private String image;
    private String insuranceCompany;
    private List<Integer> bidAmounts;
}
