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
public class CarDetailedInfo {
    private List<String> images; // images are in Base64 format
    private CarLotDetailedInfo lotInfo;
    private CarAuctionDetailedInfo auctionInfo;
}
