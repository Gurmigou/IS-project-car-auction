package com.example.backendlab.repository;

import com.example.backendlab.model.CarBid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CarBidRepository extends JpaRepository<CarBid, Long> {

    @Query("""
            SELECT MAX(CB.bidAmount) FROM CarBid CB
            WHERE CB.carAuction.id = ?1
            """)
    Integer findMaxBidForCarAuctionId(Long carAuctionId);
}
