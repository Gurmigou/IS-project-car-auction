package com.example.backendlab.controller;


import com.example.backendlab.dto.CarLotDto;
import com.example.backendlab.service.CarLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping
    public ResponseEntity<?> getAllActiveCarLots() {
        return ResponseEntity.ok(carLotService.getAllCarLots());
    }

    @PostMapping
    public ResponseEntity<?> createCarLot(@RequestBody CarLotDto carLotDto) {
        Long savedCarLotId = carLotService.saveCarLot(carLotDto);
        return ResponseEntity.ok(savedCarLotId);
    }

    @PostMapping("/images")
    public ResponseEntity<?> uploadCarLotImages(@RequestParam("carLotId") Long carLotId,
                                                @RequestParam("images") List<MultipartFile> images) {
        carLotService.saveCarLotImages(carLotId, images);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<?> updateCarLot(@RequestBody CarLotDto carLotDto) {
        carLotService.updateCarLot(carLotDto.getId(), carLotDto);
        return ResponseEntity.ok().build();
    }
}
