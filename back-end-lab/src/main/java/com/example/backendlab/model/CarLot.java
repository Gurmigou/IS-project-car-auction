package com.example.backendlab.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "CarLot")
public class CarLot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String vin;

    @Column(length = 1000)
    private String damageDescription;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CarState carState;

    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "insurance_company_id", nullable = false)
    private InsuranceCompany insuranceCompany;

    @OneToMany(mappedBy = "carLot")
    private List<CarLotImage> carLotImages;

    @ManyToOne
    @JoinColumn(name = "car_model_id", nullable = false)
    private CarModel carModel;

    @OneToOne(mappedBy = "carLot")
    private CarAuction carAuction;
}