package com.example.backendlab.repository;

import com.example.backendlab.model.InsuranceCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InsuranceCompanyRepository extends JpaRepository<InsuranceCompany, Long> {
    @Query("SELECT ic FROM InsuranceCompany ic WHERE ic.name = ?1")
    Optional<InsuranceCompany> findByName(String icName);
}