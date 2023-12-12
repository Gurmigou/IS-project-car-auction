package com.example.backendlab.repository;

import com.example.backendlab.model.CarAuction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarAuctionRepository extends JpaRepository<CarAuction, Long> {

    @Query("""
            SELECT CA FROM CarAuction CA
            WHERE CA.isFinished = false
            """)
    List<CarAuction> findAllNotFinished();

    @Query("""
            SELECT CA FROM CarAuction CA
            WHERE CA.isFinished = true
            """)
    List<CarAuction> findAllFinished();
}