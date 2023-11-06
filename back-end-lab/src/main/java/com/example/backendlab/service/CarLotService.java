package com.example.backendlab.service;

import com.example.backendlab.dto.CarLotAuctionDto;
import com.example.backendlab.model.CarAuction;
import com.example.backendlab.model.CarLot;
import com.example.backendlab.model.CarLotImage;
import com.example.backendlab.model.InsuranceCompany;
import com.example.backendlab.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class CarLotService {
    private final CarLotRepository carLotRepository;
    private final InsuranceCompanyRepository insuranceCompanyRepository;
    private final UserRepository userRepository;
    private final CarMakeRepository carMakeRepository;
    private final CarModelRepository carModelRepository;
    private final CarLotImageService carLotImageService;
    private final CarAuctionRepository carAuctionRepository;

    @Autowired
    public CarLotService(CarLotRepository carLotRepository,
                         InsuranceCompanyRepository insuranceCompanyRepository,
                         UserRepository userRepository,
                         CarMakeRepository carMakeRepository,
                         CarModelRepository carModelRepository,
                         CarLotImageService carLotImageService, CarAuctionRepository carAuctionRepository) {
        this.carLotRepository = carLotRepository;
        this.insuranceCompanyRepository = insuranceCompanyRepository;
        this.userRepository = userRepository;
        this.carMakeRepository = carMakeRepository;
        this.carModelRepository = carModelRepository;
        this.carLotImageService = carLotImageService;
        this.carAuctionRepository = carAuctionRepository;
    }

    public CarLot getCarLotById(Long id) {
        return carLotRepository.findById(id).orElse(null);
    }

    public List<CarLot> getAllCarLots() {
        return carLotRepository.findAll();
    }

    public List<CarLot> getCarLotsByInsuranceCompanyId(Long insuranceCompanyId) {
        Optional<InsuranceCompany> insuranceCompany = insuranceCompanyRepository.findById(insuranceCompanyId);
        return insuranceCompany.map(InsuranceCompany::getCarLots).orElse(null);
    }

    @Transactional
    public Long saveCarLot(CarLotAuctionDto carLotAuctionDto) {
        CarLot carLot = mapToCarLot(carLotAuctionDto);
        if (carLotAuctionDto.getWithoutPublish()) {
            CarLot saved = carLotRepository.save(carLot);
            return saved.getId();
        } else {
            CarAuction carAuction = mapToCarAuction(carLotAuctionDto);
            CarLot savedCarLot = carLotRepository.save(carLot);
            carAuction.setCarLot(savedCarLot);
            carAuctionRepository.save(carAuction);
            return savedCarLot.getId();
        }
    }

    @Transactional
    public void saveCarLotImages(Long carLotId, List<MultipartFile> images) {
        CarLot carLot = carLotRepository
                .findById(carLotId)
                .orElseThrow(() -> new EntityNotFoundException("Car lot with id " + carLotId + " not found"));
        carLotImageService.saveCarLotImages(images, carLot);
    }

    @Transactional
    public void updateCarLot(Long id, CarLotAuctionDto updatedCarLot) {
        CarLot updated = mapToCarLot(updatedCarLot);
        updated.setId(id);
        carLotRepository.save(updated);
    }

    @Transactional
    public void deleteCarLot(Long id) {
        carLotRepository.deleteById(id);
    }

    private CarLot mapToCarLot(CarLotAuctionDto carLotAuctionDto) {
        CarLot carLot = new CarLot();
        carLot.setVin(carLotAuctionDto.getVin());
        carLot.setDamageDescription(carLotAuctionDto.getDamageDescription());
        carLot.setCarState(carLotAuctionDto.getCarState());

        carLot.setIsActive(!carLotAuctionDto.getWithoutPublish());

        var insuranceCompany = insuranceCompanyRepository.findById(carLotAuctionDto.getInsuranceCompanyId());
        if (insuranceCompany.isPresent()) {
            carLot.setInsuranceCompany(insuranceCompany.get());
        } else {
            throw new EntityNotFoundException("Insurance company with id " +
                    carLotAuctionDto.getInsuranceCompanyId() + " not found");
        }

        var carModel = carModelRepository.findCarModelByName(carLotAuctionDto.getCarModel());
        carLot.setCarModel(carModel);

        carLot.setIsActive(true);
        return carLot;
    }

    private CarAuction mapToCarAuction(CarLotAuctionDto carLotAuctionDto) {
        CarAuction carAuction = new CarAuction();
        carAuction.setInitialPrice(carLotAuctionDto.getInitialPrice());
        carAuction.setAuctionDurationHours(carLotAuctionDto.getAuctionDurationHours());
        carAuction.setAuctionStart(carLotAuctionDto.getAuctionStart());
        return carAuction;
    }
}
