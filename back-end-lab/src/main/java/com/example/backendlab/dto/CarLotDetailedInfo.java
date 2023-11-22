package com.example.backendlab.dto;

import com.example.backendlab.model.CarState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarLotDetailedInfo {
    private String carMake;
    private String carModel;
    private String vin;
    private String damageDescription;
    private CarState carState;
    private String insuranceCompany;
}
