package com.example.backendlab.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "CarAuction")
public class CarAuction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer initialPrice;

    private Integer auctionDurationHours;

    private LocalDateTime auctionStart;

    @OneToOne
    @JoinColumn(name = "car_lot_id", nullable = false)
    private CarLot carLot;

    @OneToMany(mappedBy = "carAuction")
    private List<CarBid> carBids;

    private Boolean isFinished = false;
}
