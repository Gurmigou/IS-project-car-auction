package com.example.backendlab.service;

import com.example.backendlab.dto.*;
import com.example.backendlab.model.CarAuction;
import com.example.backendlab.model.CarLot;
import com.example.backendlab.model.InsuranceCompany;
import com.example.backendlab.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.example.backendlab.service.CarCommonUtil.*;

@Service
public class CarLotService {
    private final CarLotRepository carLotRepository;
    private final InsuranceCompanyRepository insuranceCompanyRepository;
    private final UserRepository userRepository;
    private final CarMakeRepository carMakeRepository;
    private final CarModelRepository carModelRepository;
    private final CarLotImageService carLotImageService;
    private final CarBidRepository carBidRepository;
    private final CarAuctionRepository carAuctionRepository;
    private final CarAuctionService carAuctionService;

    @Autowired
    public CarLotService(CarLotRepository carLotRepository,
                         InsuranceCompanyRepository insuranceCompanyRepository,
                         UserRepository userRepository,
                         CarMakeRepository carMakeRepository,
                         CarModelRepository carModelRepository,
                         CarLotImageService carLotImageService,
                         CarBidRepository carBidRepository,
                         CarAuctionRepository carAuctionRepository,
                         CarAuctionService carAuctionService) {
        this.carLotRepository = carLotRepository;
        this.insuranceCompanyRepository = insuranceCompanyRepository;
        this.userRepository = userRepository;
        this.carMakeRepository = carMakeRepository;
        this.carModelRepository = carModelRepository;
        this.carLotImageService = carLotImageService;
        this.carBidRepository = carBidRepository;
        this.carAuctionRepository = carAuctionRepository;
        this.carAuctionService = carAuctionService;
    }

    public CarLot getCarLotById(Long id) {
        return carLotRepository.findById(id).orElse(null);
    }

    // Get auctions
    public List<CarAuctionCardDto> getAllActiveCarLots() {
        return carLotRepository.findAllActiveCarLots()
                .stream()
                .filter(carLot -> carAuctionIsNotEnded(carLot.getCarAuction()))
                .map(this::mapToCarAuctionCardDto)
                .toList();
    }

    public List<CarAuctionCardDto> getActiveForInsuranceCompany(String insuranceCompanyName) {
        return getAllActiveCarLots()
                .stream()
                .filter(dto -> dto.getInsuranceCompany().equals(insuranceCompanyName))
                .toList();
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

    public CarDetailedInfo getDetailedInfoByLotId(Long lotId) {
        var carLot = carLotRepository
                .findById(lotId)
                .orElseThrow(() -> new EntityNotFoundException("Car lot with id " + lotId + " not found"));
        var carAuction = carLot.getCarAuction();

        var carAuctionDetailedInfo = carAuctionService
                .mapToCarAuctionDetailedInfo(carAuction);
        var carLotDetailedInfo = mapToCarLotDetailedInfo(carLot);
        var images = carLot.getCarLotImages()
                .stream()
                .map(CarCommonUtil::getCarImagedBase64)
                .toList();

        var carDetailedInfo = new CarDetailedInfo();
        carDetailedInfo.setAuctionInfo(carAuctionDetailedInfo);
        carDetailedInfo.setLotInfo(carLotDetailedInfo);
        carDetailedInfo.setImages(images);
        return carDetailedInfo;
    }

    private CarAuction mapToCarAuction(CarLotAuctionDto carLotAuctionDto) {
        CarAuction carAuction = new CarAuction();
        carAuction.setInitialPrice(carLotAuctionDto.getInitialPrice());
        carAuction.setAuctionDurationHours(carLotAuctionDto.getAuctionDurationHours());
        carAuction.setAuctionStart(carLotAuctionDto.getAuctionStart());
        return carAuction;
    }

    private CarAuctionCardDto mapToCarAuctionCardDto(CarLot carLot) {
        var dto = new CarAuctionCardDto();
        dto.setLotId(carLot.getId());
        dto.setCarMake(carLot.getCarModel().getCarMake().getName());
        dto.setCarModel(carLot.getCarModel().getName());
        dto.setDamageDescription(carLot.getDamageDescription());

        var carAuction = carLot.getCarAuction();
        Integer currentPrice = carBidRepository.findMaxBidForCarAuctionId(carAuction.getId());

        dto.setCurrentPrice(currentPrice == null ? 0 : currentPrice);
        dto.setTimeLeft(getAuctionTimeLeft(LocalDateTime.now(), carAuction.getAuctionStart(), carAuction.getAuctionDurationHours()));
        dto.setInsuranceCompany(carLot.getInsuranceCompany().getName());

        dto.setImage(getCarImagedBase64(carLot.getCarLotImages().get(0)));
        return dto;
    }

    private CarLotDetailedInfo mapToCarLotDetailedInfo(CarLot carLot) {
        var carLotDetailedInfo = new CarLotDetailedInfo();
        carLotDetailedInfo.setCarMake(carLot.getCarModel().getCarMake().getName());
        carLotDetailedInfo.setCarModel(carLot.getCarModel().getName());
        carLotDetailedInfo.setVin(carLot.getVin());
        carLotDetailedInfo.setDamageDescription(carLot.getDamageDescription());
        carLotDetailedInfo.setCarState(carLot.getCarState());
        carLotDetailedInfo.setInsuranceCompany(carLot.getInsuranceCompany().getName());
        return carLotDetailedInfo;
    }

    public List<CarLotWithoutAuctionForICDto> getCarLotsForInsuranceCompany() {
        var lotsWithoutAuction = carLotRepository.findAll()
                .stream()
                .filter(carLot -> !carLot.getIsActive())
                .toList();
        return lotsWithoutAuction.stream()
                .map(carLot -> CarLotWithoutAuctionForICDto
                        .builder()
                        .lotId(carLot.getId())
                        .carMake(carLot.getCarModel().getCarMake().getName())
                        .carModel(carLot.getCarModel().getName())
                        .damageDescription(carLot.getDamageDescription())
                        .image(CarCommonUtil.getCarImagedBase64(carLot.getCarLotImages().get(0)))
                        .build())
                .toList();
    }
}
