package com.example.backendlab.service;

import com.example.backendlab.dto.InsuranceCompanyInfoDto;
import com.example.backendlab.model.BoughtCarLot;
import com.example.backendlab.model.CarAuction;
import com.example.backendlab.model.CarLot;
import com.example.backendlab.model.InsuranceCompany;
import com.example.backendlab.repository.BoughtCarLotRepository;
import com.example.backendlab.repository.InsuranceCompanyRepository;
import com.example.backendlab.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class StatsService {
    private final InsuranceCompanyRepository insuranceCompanyRepository;
    private final BoughtCarLotRepository boughtCarLotRepository;

    @Autowired
    public StatsService(InsuranceCompanyRepository insuranceCompanyRepository,
                        BoughtCarLotRepository boughtCarLotRepository) {
        this.insuranceCompanyRepository = insuranceCompanyRepository;
        this.boughtCarLotRepository = boughtCarLotRepository;
    }

    public List<InsuranceCompanyInfoDto> getAllInsuranceCompaniesStats() {
        return insuranceCompanyRepository.findAll()
                .stream()
                .map(this::mapToInsuranceCompanyInfoDto)
                .toList();
    }

    private InsuranceCompanyInfoDto mapToInsuranceCompanyInfoDto(InsuranceCompany ic) {
        var activeAuctionsNum = (int) ic.getCarLots()
                .stream()
                .filter(CarLot::getIsActive)
                .filter(carLot -> Objects.nonNull(carLot.getCarAuction().getIsFinished()) && !carLot.getCarAuction().getIsFinished())
                .count();

        var totalActionsNum = (int) ic.getCarLots()
                .stream()
                .filter(carLot -> Objects.nonNull(carLot.getIsActive()) && carLot.getIsActive())
                .count();

        var boughtLotsByInsuranceCompany = boughtCarLotRepository
                .findBoughtCarLotByInsuranceCompanyId(ic.getId());

        var minPrice = boughtLotsByInsuranceCompany
                .stream()
                .mapToInt(BoughtCarLot::getFinalPrice)
                .min()
                .orElse(0);
        var maxPrice = boughtLotsByInsuranceCompany
                .stream()
                .mapToInt(BoughtCarLot::getFinalPrice)
                .max()
                .orElse(0);
        var avgPrice = boughtLotsByInsuranceCompany
                .stream()
                .mapToInt(BoughtCarLot::getFinalPrice)
                .average()
                .orElse(0);

        var dto = new InsuranceCompanyInfoDto();
        dto.setName(ic.getName());
        dto.setActiveAuctions(activeAuctionsNum);
        dto.setTotalAuctions(totalActionsNum);
        dto.setMinSoldPrice(minPrice);
        dto.setMaxSoldPrice(maxPrice);
        dto.setAvgSoldPrice(avgPrice);
        return dto;
    }
}
