package com.example.backendlab.controller;

import com.example.backendlab.model.CarAuctionStatus;
import com.example.backendlab.service.CarAuctionService;
import com.example.backendlab.service.CarLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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

    // IC Auctions [IC]
    @GetMapping("/active-for-ic")
    public ResponseEntity<?> getActiveForInsuranceCompany(Principal principal) {
        // TODO: use insurance company name from principal
        return ResponseEntity.ok(carLotService.getActiveForInsuranceCompany("Insurance Company 1"));
    }

    // IC Auctions Approvals [IC]
    @GetMapping("/finished-for-ic")
    public ResponseEntity<?> getFinishedForInsuranceCompany(Principal principal) {
        // TODO: use insurance company name from principal
        return ResponseEntity.ok(carLotService.getFinishedForInsuranceCompany("Insurance Company 1"));
    }

    @PutMapping("/change-auction-status")
    public ResponseEntity<?> changeAuctionStatus(@RequestParam Long auctionId, @RequestParam CarAuctionStatus status) {
        carAuctionService.changeAuctionStatus(auctionId, status);
        return ResponseEntity.ok().build();
    }


    // IC Auctions Approvals [U]
    @GetMapping("/finished-for-user")
    public ResponseEntity<?> getFinishedForUser(Principal principal) {
        // TODO: use user email from principal
        return ResponseEntity.ok(carLotService.getFinishedForUser("some@gmail.com"));
    }
}
