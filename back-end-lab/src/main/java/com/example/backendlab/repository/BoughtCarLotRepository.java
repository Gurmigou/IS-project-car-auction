package com.example.backendlab.repository;

import com.example.backendlab.model.BoughtCarLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoughtCarLotRepository extends JpaRepository<BoughtCarLot, Long> {

    @Query("""
            SELECT bcl FROM BoughtCarLot bcl
            JOIN FETCH bcl.carLot cl
            INNER JOIN cl.insuranceCompany ic
            WHERE ic.id = ?1
            """)
    List<BoughtCarLot> findBoughtCarLotByInsuranceCompanyId(Long id);
}