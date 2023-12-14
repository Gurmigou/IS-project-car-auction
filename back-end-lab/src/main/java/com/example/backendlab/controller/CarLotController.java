package com.example.backendlab.controller;


import com.example.backendlab.dto.CarLotAuctionDto;
import com.example.backendlab.service.CarLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/car-lot")
public class CarLotController {
    private final CarLotService carLotService;

    @Autowired
    public CarLotController(CarLotService carLotService) {
        this.carLotService = carLotService;
    }

    // IC Lots [IC]
    @GetMapping("/lots-for-ic")
    public ResponseEntity<?> getCarLotsForInsuranceCompany(Principal principal) {
        return ResponseEntity.ok(carLotService.getCarLotsForInsuranceCompany(principal.getName()));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCarLot(@RequestParam Long lotId) {
        carLotService.deleteCarLot(lotId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/lot-detailed")
    public ResponseEntity<?> getDetailedInfoByLotId(@RequestParam Long lotId) {
        return ResponseEntity.ok(carLotService.getDetailedInfoByLotId(lotId));
    }

    @PostMapping
    public ResponseEntity<?> createCarLot(@RequestBody CarLotAuctionDto carLotAuctionDto, Principal principal) {
        Long savedCarLotId = carLotService.saveCarLot(carLotAuctionDto, principal.getName());
        return ResponseEntity.ok(savedCarLotId);
    }

    @PostMapping("/images")
    public ResponseEntity<?> uploadCarLotImages(@RequestParam("carLotId") Long carLotId,
                                                @RequestParam("images") List<MultipartFile> images) {
        carLotService.saveCarLotImages(carLotId, images);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<?> updateCarLot(@RequestBody CarLotAuctionDto carLotAuctionDto, Principal principal) {
        Long updatedCarLotId = carLotService.updateCarLot(carLotAuctionDto.getId(), carLotAuctionDto, principal.getName());
        return ResponseEntity.ok(updatedCarLotId);
    }
}
