package com.example.backendlab.service;

import com.example.backendlab.dto.*;
import com.example.backendlab.model.*;
import com.example.backendlab.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
    private final CarLotImagesRepository carLotImagesRepository;

    @Autowired
    public CarLotService(CarLotRepository carLotRepository,
                         InsuranceCompanyRepository insuranceCompanyRepository,
                         UserRepository userRepository,
                         CarMakeRepository carMakeRepository,
                         CarModelRepository carModelRepository,
                         CarLotImageService carLotImageService,
                         CarBidRepository carBidRepository,
                         CarAuctionRepository carAuctionRepository,
                         CarAuctionService carAuctionService,
                         CarLotImagesRepository carLotImagesRepository) {
        this.carLotRepository = carLotRepository;
        this.insuranceCompanyRepository = insuranceCompanyRepository;
        this.userRepository = userRepository;
        this.carMakeRepository = carMakeRepository;
        this.carModelRepository = carModelRepository;
        this.carLotImageService = carLotImageService;
        this.carBidRepository = carBidRepository;
        this.carAuctionRepository = carAuctionRepository;
        this.carAuctionService = carAuctionService;
        this.carLotImagesRepository = carLotImagesRepository;
    }

    public CarLot getCarLotById(Long id) {
        return carLotRepository.findById(id).orElse(null);
    }

    // Get auctions
    @Transactional
    public List<CarAuctionCardDto> getAllActiveCarLots() {
        return carLotRepository.findAllActiveCarLots()
                .stream()
                .filter(carLot -> carAuctionIsNotEnded(carLot.getCarAuction()))
                .peek(carLot -> carAuctionRepository.save(carLot.getCarAuction()))
                .map(this::mapToCarAuctionCardDto)
                .toList();
    }

    @Transactional
    public List<CarAuctionInfoForIC> getActiveForInsuranceCompany(String insuranceCompanyName) {
        return carLotRepository.findAllActiveCarLots()
                .stream()
                .filter(carLot -> carAuctionIsNotEnded(carLot.getCarAuction()))
                .peek(carLot -> carAuctionRepository.save(carLot.getCarAuction()))
                .filter(carLot -> carLot.getInsuranceCompany().getName().equals(insuranceCompanyName))
                .map(this::mapToCarAuctionInfoForIC)
                .toList();
    }

    public List<CarAuctionApproval> getFinishedForInsuranceCompany(String icName) {
        return carLotRepository.findAllActiveCarLots()
                .stream()
                .filter(carLot -> carLot.getInsuranceCompany().getName().equals(icName))
                .filter(carLot -> carLot.getCarAuction().getIsFinished())
                .map(this::mapToCarAuctionApproval)
                .filter(Objects::nonNull)
                .toList();
    }

    private CarAuctionApproval mapToCarAuctionApproval(CarLot carLot) {
        var dto = new CarAuctionApproval();
        dto.setAuctionId(carLot.getCarAuction().getId());
        dto.setCarMake(carLot.getCarModel().getCarMake().getName());
        dto.setCarModel(carLot.getCarModel().getName());
        dto.setVin(carLot.getVin());
        dto.setDamageDescription(carLot.getDamageDescription());
        dto.setCarState(carLot.getCarState().name());
        dto.setInsuranceCompany(carLot.getInsuranceCompany().getName());

        CarBid maxBid = carBidRepository.findMaxCarBidByCarAuctionId(carLot.getCarAuction().getId());
        if (maxBid == null)
            return null;

        dto.setUserName(maxBid.getUser().getFirstName() + " " + maxBid.getUser().getLastName());

        dto.setFinalPrice(carBidRepository.findMaxBidForCarAuctionId(carLot.getCarAuction().getId()));
        dto.setStatus(carLot.getCarAuction().getStatus());
        dto.setImage(getCarImagedBase64(carLot.getCarLotImages().get(0)));
        return dto;
    }

    private CarAuctionInfoForIC mapToCarAuctionInfoForIC(CarLot carLot) {
        var dto = new CarAuctionInfoForIC();
        dto.setAuctionId(carLot.getCarAuction().getId());
        dto.setLotId(carLot.getId());
        dto.setCarMake(carLot.getCarModel().getCarMake().getName());
        dto.setCarModel(carLot.getCarModel().getName());
        dto.setVin(carLot.getVin());
        dto.setDamageDescription(carLot.getDamageDescription());
        dto.setCarState(carLot.getCarState().name());
        dto.setImage(getCarImagedBase64(carLot.getCarLotImages().get(0)));
        dto.setInitialPrice(carLot.getCarAuction().getInitialPrice());
        dto.setAuctionStartDate(carLot.getCarAuction().getAuctionStart().toString());
        dto.setAuctionDuration(carLot.getCarAuction().getAuctionDurationHours().toString());
        dto.setTimeLeft(getAuctionTimeLeft(LocalDateTime.now(), carLot.getCarAuction().getAuctionStart(),
                carLot.getCarAuction().getAuctionDurationHours()));
        dto.setBidsList(carBidRepository.findAll()
                .stream()
                .filter(carBid -> carBid.getCarAuction().getId().equals(carLot.getCarAuction().getId()))
                .map(carBid -> new BidDtoForAuctionIC(carBid.getBidAmount(),
                        carBid.getUser().getFirstName() + " " + carBid.getUser().getLastName()))
                .toList());
        return dto;
    }

    public List<CarLot> getCarLotsByInsuranceCompanyId(Long insuranceCompanyId) {
        Optional<InsuranceCompany> insuranceCompany = insuranceCompanyRepository.findById(insuranceCompanyId);
        return insuranceCompany.map(InsuranceCompany::getCarLots).orElse(null);
    }

    @Transactional
    public Long saveCarLot(CarLotAuctionDto carLotAuctionDto, String icName) {
        carLotAuctionDto.setInsuranceCompanyId(insuranceCompanyRepository.findByName(icName)
                .orElseThrow(() -> new EntityNotFoundException("Insurance company with name " + icName + " not found"))
                .getId());

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
    public Long updateCarLot(Long id, CarLotAuctionDto updatedCarLot, String icName) {
        InsuranceCompany insuranceCompany = insuranceCompanyRepository.findByName(icName)
                .orElseThrow(() -> new EntityNotFoundException("Insurance company with name " + icName + " not found"));
        updatedCarLot.setInsuranceCompanyId(insuranceCompany.getId());

        CarLot carLot = carLotRepository.findById(id).orElseThrow();
        CarLot mapped = mapToCarLot(updatedCarLot);

        carLot.setCarModel(mapped.getCarModel());
        carLot.setVin(mapped.getVin());
        carLot.setDamageDescription(mapped.getDamageDescription());
        carLot.setCarState(mapped.getCarState());
        carLot.setInsuranceCompany(mapped.getInsuranceCompany());
        carLot.setIsActive(mapped.getIsActive());
        carLot.setCarLotImages(Collections.emptyList());

        carLotImagesRepository.deleteAllByCarLotId(id);

        CarLot saved = carLotRepository.save(carLot);

        if (!updatedCarLot.getWithoutPublish()) {
            CarAuction carAuction = mapToCarAuction(updatedCarLot);
            carAuction.setCarLot(saved);
            carAuctionRepository.save(carAuction);
        }

        return saved.getId();
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

    public List<CarLotWithoutAuctionForICDto> getCarLotsForInsuranceCompany(String icName) {
        var lotsWithoutAuction = carLotRepository.findAll()
                .stream()
                .filter(carLot -> !carLot.getIsActive())
                .filter(carLot -> carLot.getInsuranceCompany().getName().equals(icName))
                .toList();
        return lotsWithoutAuction.stream()
                .map(carLot -> CarLotWithoutAuctionForICDto
                        .builder()
                        .lotId(carLot.getId())
                        .carMake(carLot.getCarModel().getCarMake().getName())
                        .carModel(carLot.getCarModel().getName())
                        .damageDescription(carLot.getDamageDescription())
                        .image(CarCommonUtil.getCarImagedBase64(carLot.getCarLotImages().get(0)))
                        .vin(carLot.getVin())
                        .carState(carLot.getCarState().name())
                        .insuranceCompany(carLot.getInsuranceCompany().getName())
                        .build())
                .toList();
    }

    public List<CarAuctionApproval> getFinishedForUser(String userEmail) {
        return carAuctionRepository.findAllFinished()
                .stream()
                .map(carAuction -> carBidRepository.findMaxCarBidByCarAuctionId(carAuction.getId()))
                .filter(Objects::nonNull)
                .filter(carBid -> carBid.getUser().getEmail().equals(userEmail))
                .map(carBid -> mapToCarAuctionApproval(carBid.getCarAuction().getCarLot()))
                .filter(Objects::nonNull)
                .toList();
    }
}
