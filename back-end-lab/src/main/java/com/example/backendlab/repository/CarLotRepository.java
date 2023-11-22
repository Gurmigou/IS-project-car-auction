package com.example.backendlab.repository;

import com.example.backendlab.model.CarLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarLotRepository extends JpaRepository<CarLot, Long> {

    @Query("""
            SELECT CL FROM CarLot CL
            JOIN CL.carAuction CA
            WHERE CL.isActive = TRUE
            AND FUNCTION('DATE_ADD', CA.auctionStart, FUNCTION('INTERVAL', CA.auctionDurationHours, 'HOUR')) > CURRENT_TIMESTAMP
            """)
    List<CarLot> findAllActiveNotEndedCarLots();
}
