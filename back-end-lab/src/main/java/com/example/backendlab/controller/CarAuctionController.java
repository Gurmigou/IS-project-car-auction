package com.example.backendlab.controller;

import com.example.backendlab.service.CarAuctionService;
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

    @Autowired
    public CarAuctionController(CarAuctionService carAuctionService) {
        this.carAuctionService = carAuctionService;
    }

    @GetMapping
    public ResponseEntity<?> getActiveAuctions() {
        return ResponseEntity.ok(carAuctionService.getActiveAuctions());
    }
}
