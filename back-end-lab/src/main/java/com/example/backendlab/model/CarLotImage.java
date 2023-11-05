package com.example.backendlab.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;

@Getter
@Setter
@Entity
@Table(name = "CarLotImage")
public class CarLotImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "data")
    private Blob data;

    @ManyToOne
    @JoinColumn(name = "car_lot_id", nullable = false)
    private CarLot carLot;
}