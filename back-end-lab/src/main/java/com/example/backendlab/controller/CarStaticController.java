package com.example.backendlab.controller;

import com.example.backendlab.model.CarMake;
import com.example.backendlab.model.CarModel;
import com.example.backendlab.model.CarState;
import com.example.backendlab.repository.CarMakeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/car/static")
public class CarStaticController {
    private final CarMakeRepository carMakeRepository;

    @Autowired
    public CarStaticController(CarMakeRepository carMakeRepository) {
        this.carMakeRepository = carMakeRepository;
    }

    @GetMapping
    public ResponseEntity<Map<String, List<String>>> getCarMakeModelStaticData() {
        List<CarMake> carMakeList = carMakeRepository.findAll();
        Map<String, List<String>> carMakeModelMap = carMakeList.stream()
                .collect(Collectors.toMap(CarMake::getName, carMake -> carMake.getCarModels().stream()
                        .map(CarModel::getName)
                        .collect(Collectors.toList())));
        return ResponseEntity.ok(carMakeModelMap);
    }

    @GetMapping("/states")
    public ResponseEntity<List<String>> getCarStateStaticData() {
        return ResponseEntity.ok(Stream.of(CarState.values())
                .map(CarState::name)
                .map(s -> s.replace("_", " "))
                .map(String::toLowerCase)
                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))
                .collect(Collectors.toList()));
    }
}
