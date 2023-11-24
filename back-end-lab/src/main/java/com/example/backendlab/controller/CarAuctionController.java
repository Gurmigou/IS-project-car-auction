package com.example.backendlab.controller;

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

    @PostMapping("/bid")
    public ResponseEntity<?> bidForCarLot(@RequestParam Long carAuctionId,
                                          @RequestParam Integer bidAmount,
                                          Principal principal) {
        // TODO: use principal
        carAuctionService.makeBidForCarLot(carAuctionId, bidAmount, "some@gmail.com");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/max-bid")
    public ResponseEntity<?> getMaxBidForCarLot(@RequestParam Long carAuctionId) {
        return ResponseEntity.ok(carAuctionService.getMaxBidForCarAuction(carAuctionId));
    }

    // My bids [U]
    @GetMapping ("/bids-for-user")
    public ResponseEntity<?> getBidsForUser() {
        return ResponseEntity.ok(carLotService.getMyBids());
    }

    // IC Auctions [IC]
    @GetMapping("/active-for-ic")
    public ResponseEntity<?> getActiveForInsuranceCompany(Principal principal) {
        // TODO: use insurance company name from principal
        return ResponseEntity.ok(carLotService.getActiveForInsuranceCompany("Insurance Company 1"));
    }
}
