package com.example.backendlab.controller;

import com.example.backendlab.dto.security.LoginDto;
import com.example.backendlab.dto.security.RegistrationDto;
import com.example.backendlab.dto.security.TokenDto;
import com.example.backendlab.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/security")
@CrossOrigin("*")
public class SecurityController {
    private final SecurityService userService;

    @Autowired
    public SecurityController(SecurityService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUsingEmail(@RequestBody LoginDto loginDto) {
        try {
            TokenDto tokenDto = userService.loginUser(loginDto);
            return ResponseEntity.ok(tokenDto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationDto registrationDto) {
        try {
            userService.registerUser(registrationDto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .build();
        }
    }

    @PostMapping("/update-token")
    public ResponseEntity<?> authenticateUsingJwtToken(Principal principal) {
        try {
            TokenDto tokenDto = userService.generateToken(principal.getName());
            return ResponseEntity.ok(tokenDto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .build();
        }
    }
}