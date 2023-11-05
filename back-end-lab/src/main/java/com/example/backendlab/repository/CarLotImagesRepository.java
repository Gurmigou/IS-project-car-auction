package com.example.backendlab.repository;

import com.example.backendlab.model.CarLotImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarLotImagesRepository extends JpaRepository<CarLotImage, Long> {
}
