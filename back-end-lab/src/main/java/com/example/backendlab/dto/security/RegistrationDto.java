package com.example.backendlab.dto.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDto {
    private String insuranceCompanyName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
}
