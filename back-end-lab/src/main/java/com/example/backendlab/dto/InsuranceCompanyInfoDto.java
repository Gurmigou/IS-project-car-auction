package com.example.backendlab.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceCompanyInfoDto {
    private String name;
    private Integer activeAuctions;
    private Integer totalAuctions;
    private Integer minSoldPrice;
    private Integer maxSoldPrice;
    private Double avgSoldPrice;
}

