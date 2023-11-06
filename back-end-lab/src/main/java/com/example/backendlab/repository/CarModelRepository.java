package com.example.backendlab.repository;

import com.example.backendlab.model.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarModelRepository extends JpaRepository<CarModel, Long> {
    CarModel findCarModelByName(String name);
}

