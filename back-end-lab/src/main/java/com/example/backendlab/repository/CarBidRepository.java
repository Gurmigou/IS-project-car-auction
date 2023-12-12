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

    @Query("""
            SELECT CB FROM CarBid CB
            WHERE CB.carAuction.id = ?1
            AND CB.bidAmount = (SELECT MAX(CB2.bidAmount) FROM CarBid CB2 WHERE CB2.carAuction.id = ?1)
            """)
    CarBid findMaxCarBidByCarAuctionId(Long carAuctionId);
}
