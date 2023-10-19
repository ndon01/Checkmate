package com.checkmate.authentication.controller;

import com.checkmate.authentication.model.dto.requests.loginRequestDTO;
import com.checkmate.authentication.model.dto.requests.registrationRequestDTO;
import com.checkmate.authentication.service.AuthService;
import com.checkmate.authentication.model.entity.AuthProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
@RestController
public class AuthenticationController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody registrationRequestDTO registrationData) {
        try {

            AuthProfile registeredUser = authService.register(
                    registrationData.getUsername(),
                    registrationData.getPassword(),
                    registrationData.getEmail());
            // You can return the AuthProfile entity here, or maybe just a success message.
            // Do not return sensitive information like password hashes.
            return ResponseEntity.ok(registeredUser);
        } catch (Exception e) {
            // Handle exceptions like user already exists, invalid data, etc.
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody loginRequestDTO loginData) {
        Optional<AuthProfile> authProfile = authService.authenticateWithIdentifier(loginData.getIdentifier(), loginData.getPassword());

        if (authProfile.isPresent()) {
            // Generate JWT token and send it back to the user
            String jwtToken = "YOUR_JWT_TOKEN";  // Generate this using your JWT library
            return ResponseEntity.ok(jwtToken);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
