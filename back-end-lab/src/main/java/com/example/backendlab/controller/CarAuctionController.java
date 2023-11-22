package com.example.backendlab.controller;

import com.example.backendlab.service.CarAuctionService;
import com.example.backendlab.service.CarLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/car-auction")
public class CarAuctionController {
    private final CarAuctionService carAuctionService;
    private final CarLotService carLotService;


    @Autowired
    public CarAuctionController(CarAuctionService carAuctionService,
                                CarLotService carLotService) {
        this.carAuctionService = carAuctionService;
        this.carLotService = carLotService;
    }

    @GetMapping("/all-active")
    public ResponseEntity<?> getActiveAuctions() {
        return ResponseEntity.ok(carAuctionService.getActiveAuctions());
    }

    // Auctions [U]
    @GetMapping("/active-for-user")
    public ResponseEntity<?> getActiveAuctionsForUser() {
        return ResponseEntity.ok(carLotService.getAllActiveCarLots());
    }

    // My bids [U]
    @GetMapping ("/bids-for-user")
    public ResponseEntity<?> getBidsForUser() {
        return ResponseEntity.ok(carLotService.getMyBids());
    }

    // IC Auctions [IC]
    @GetMapping("/active-for-ic")
    public ResponseEntity<?> getActiveForInsuranceCompany() {
        return null;
    }
}
