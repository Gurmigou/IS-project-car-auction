package com.example.backendlab.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "CarModel")
@Getter
@Setter
public class CarModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "car_make_id")
    private CarMake carMake;

    @OneToMany(mappedBy = "carModel")
    private List<CarLot> carLots;
}