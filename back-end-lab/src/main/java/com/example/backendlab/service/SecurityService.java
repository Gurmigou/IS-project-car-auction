package com.example.backendlab.service;

import com.example.backendlab.dto.security.LoginDto;
import com.example.backendlab.dto.security.RegistrationDto;
import com.example.backendlab.dto.security.TokenDto;
import com.example.backendlab.model.InsuranceCompany;
import com.example.backendlab.model.User;
import com.example.backendlab.repository.InsuranceCompanyRepository;
import com.example.backendlab.repository.UserRepository;
import com.example.backendlab.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class SecurityService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final InsuranceCompanyRepository insuranceCompanyRepository;

    @Autowired
    public SecurityService(UserRepository userRepository, JwtProvider jwtProvider,
                           PasswordEncoder passwordEncoder,
                           InsuranceCompanyRepository insuranceCompanyRepository) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.insuranceCompanyRepository = insuranceCompanyRepository;
    }

    public void registerUser(RegistrationDto registrationDto) {
        if (isUserExistsByLogin(registrationDto.getEmail())) {
            if (Objects.nonNull(registrationDto.getInsuranceCompanyName())) {
                InsuranceCompany insuranceCompany = new InsuranceCompany();

                userRepository.findByEmail(registrationDto.getEmail())
                        .ifPresent(insuranceCompany::setOwner);
                insuranceCompany.setName(registrationDto.getInsuranceCompanyName());

                insuranceCompanyRepository.save(insuranceCompany);
            } else {
                throw new IllegalStateException(String.format(
                        "User with username %s already exists", registrationDto.getEmail()));
            }
        } else {
            User user = createNewUser(registrationDto);
            userRepository.save(user);
        }
    }

    public TokenDto loginUser(LoginDto loginDto) {
        User user = findUserByEmail(loginDto.getEmail())
                .orElseThrow(() -> new IllegalStateException(
                        "User with email " + loginDto.getEmail() + " does not exist."));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword()))
            throw new IllegalStateException("Passwords don't match");

        if (Objects.nonNull(loginDto.getInsuranceCompanyName())) {
            return new TokenDto(jwtProvider.generateToken(loginDto.getInsuranceCompanyName()));
        } else {
            return new TokenDto(jwtProvider.generateToken(user.getEmail()));
        }
    }

    public TokenDto generateToken(String email) {
        return new TokenDto(jwtProvider.generateToken(email));
    }

    private Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private boolean isUserExistsByLogin(String email) {
        return findUserByEmail(email).isPresent();
    }

    private User createNewUser(RegistrationDto registrationDto) {
        var newUser = new User();
        newUser.setEmail(registrationDto.getEmail());
        newUser.setPassword(encodePassword(registrationDto.getPassword()));
        newUser.setFirstName(registrationDto.getFirstName());
        newUser.setLastName(registrationDto.getLastName());
        return newUser;
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}