package com.example.backendlab.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarAuctionDetailedInfo {
    private Long auctionId;
    private Integer initialPrice;
    private String auctionStartDate;
    private String auctionDuration;
    private String timeLeft;
    private Integer currentPrice;
}
