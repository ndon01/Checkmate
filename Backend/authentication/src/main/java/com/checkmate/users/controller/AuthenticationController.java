package com.checkmate.users.controller;

import com.checkmate.users.model.dto.requests.loginRequestDTO;
import com.checkmate.users.model.dto.requests.registrationRequestDTO;
import com.checkmate.users.model.dto.responses.loginResponseDTO;
import com.checkmate.users.model.dto.responses.registrationResponseDTO;
import com.checkmate.users.model.error.ErrorResponse;
import com.checkmate.users.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private CredentialsService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody registrationRequestDTO registrationData) {
        registrationResponseDTO regAttempt = authService.register(
                registrationData.getUsername(),
                registrationData.getPassword(),
                registrationData.getEmail()
        );

        if (regAttempt.isFailure()) {
            ErrorResponse err = new ErrorResponse();
            err.setStatus(409);
            err.setError("Conflict");
            err.setMessage(regAttempt.getFailReason());

            return ResponseEntity.status(409).body(err);
        }

        return ResponseEntity.ok("Registration Successfully Completed, Verify your Email");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody loginRequestDTO loginData) {
        loginResponseDTO loginAttempt = authService.authenticateWithIdentifier(loginData.getIdentifier(), loginData.getPassword());

        if (loginAttempt.isFailure()) {
            ErrorResponse err = new ErrorResponse();
            err.setStatus(409);
            err.setError("Conflict");
            err.setMessage("Authentication Failure");

            return ResponseEntity.status(409).body(err);
        }

        return ResponseEntity.ok("Authenticated");
    }
}
