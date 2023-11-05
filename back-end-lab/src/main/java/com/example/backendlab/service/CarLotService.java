package com.example.backendlab.service;

import com.example.backendlab.dto.CarLotDto;
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

    @Autowired
    public CarLotService(CarLotRepository carLotRepository,
                         InsuranceCompanyRepository insuranceCompanyRepository,
                         UserRepository userRepository,
                         CarMakeRepository carMakeRepository,
                         CarModelRepository carModelRepository,
                         CarLotImageService carLotImageService) {
        this.carLotRepository = carLotRepository;
        this.insuranceCompanyRepository = insuranceCompanyRepository;
        this.userRepository = userRepository;
        this.carMakeRepository = carMakeRepository;
        this.carModelRepository = carModelRepository;
        this.carLotImageService = carLotImageService;
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
    public Long saveCarLot(CarLotDto carLotDto) {
        CarLot carLot = mapToCarLot(carLotDto);
        if (carLotDto.getWithoutPublish()) {
            CarLot saved = carLotRepository.save(carLot);
            return saved.getId();
        } else {
            CarAuction carAuction = mapToCarAuction(carLotDto);
            carAuction.setCarLot(carLot);
            carLot.setCarAuction(carAuction);
            CarLot saved = carLotRepository.save(carLot);
            return saved.getId();
        }
    }

    @Transactional
    public void saveCarLotImages(Long carLotId, List<MultipartFile> images) {
        CarLot carLot = carLotRepository
                .findById(carLotId)
                .orElseThrow(() -> new EntityNotFoundException("Car lot with id " + carLotId + " not found"));
        List<CarLotImage> savedCarLotImages = carLotImageService.saveCarLotImages(images);
        carLot.setCarLotImages(savedCarLotImages);
        carLotRepository.save(carLot);
    }

    @Transactional
    public void updateCarLot(Long id, CarLotDto updatedCarLot) {
        CarLot updated = mapToCarLot(updatedCarLot);
        updated.setId(id);
        carLotRepository.save(updated);
    }

    @Transactional
    public void deleteCarLot(Long id) {
        carLotRepository.deleteById(id);
    }

    private CarLot mapToCarLot(CarLotDto carLotDto) {
        CarLot carLot = new CarLot();
        carLot.setVin(carLotDto.getVin());
        carLot.setDamageDescription(carLotDto.getDamageDescription());
        carLot.setCarState(carLotDto.getCarState());

        carLot.setIsActive(!carLotDto.getWithoutPublish());

        var insuranceCompany = insuranceCompanyRepository.findById(carLotDto.getInsuranceCompanyId());
        if (insuranceCompany.isPresent()) {
            carLot.setInsuranceCompany(insuranceCompany.get());
        } else {
            throw new EntityNotFoundException("Insurance company with id " +
                    carLotDto.getInsuranceCompanyId() + " not found");
        }

        var carModel = carModelRepository.findByName(carLotDto.getCarModel());
        carLot.setCarModel(carModel);
        return carLot;
    }

    private CarAuction mapToCarAuction(CarLotDto carLotDto) {
        CarAuction carAuction = new CarAuction();
        carAuction.setInitialPrice(carLotDto.getInitialPrice());
        carAuction.setCurrentPrice(carLotDto.getInitialPrice());
        carAuction.setAuctionDurationHours(carLotDto.getAuctionDurationHours());
        carAuction.setAuctionStart(carLotDto.getAuctionStart());
        return carAuction;
    }
}
