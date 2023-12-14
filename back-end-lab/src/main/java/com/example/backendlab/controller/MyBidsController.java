package com.example.backendlab.controller;

import com.example.backendlab.service.CarBidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/my-bids")
public class MyBidsController {
    private final CarBidService carBidService;

    @Autowired
    public MyBidsController(CarBidService carBidService) {
        this.carBidService = carBidService;
    }

    @GetMapping ("/bids-for-user")
    public ResponseEntity<?> getBidsForUser(Principal principal) {
        return ResponseEntity.ok(carBidService.getMyBids(principal.getName()));
    }

    @PostMapping
    public ResponseEntity<?> submitNewBid(Principal principal,
                                          @RequestParam Long auctionId,
                                          @RequestParam Integer bid) {
        carBidService.createNewCarBid(principal.getName(), auctionId, bid);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/max-bid")
    public ResponseEntity<?> getMaxBidForCarAuctionId(@RequestParam Long auctionId) {
        return ResponseEntity.ok(carBidService.getMaxBidForCarAuctionId(auctionId));
    }
}
