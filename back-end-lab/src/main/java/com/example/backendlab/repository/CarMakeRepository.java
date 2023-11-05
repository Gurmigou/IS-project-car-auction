package com.example.backendlab.repository;

import com.example.backendlab.model.CarMake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarMakeRepository extends JpaRepository<CarMake, Long> {
    CarMake findByName(String name);
}