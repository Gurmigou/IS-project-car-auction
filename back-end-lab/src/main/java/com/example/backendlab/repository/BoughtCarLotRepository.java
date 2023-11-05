package com.example.backendlab.repository;

import com.example.backendlab.model.BoughtCarLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoughtCarLotRepository extends JpaRepository<BoughtCarLot, Long> {
}