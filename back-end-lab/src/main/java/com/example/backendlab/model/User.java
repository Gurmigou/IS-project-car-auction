package com.example.backendlab.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "owner")
    private List<InsuranceCompany> insuranceCompanyList;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<BoughtCarLot> boughtCarLots;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<CarBid> carBids;

    @JsonIgnore
    private Boolean isInsuranceCompanyOwner() {
        return insuranceCompanyList != null && !insuranceCompanyList.isEmpty();
    }
}
