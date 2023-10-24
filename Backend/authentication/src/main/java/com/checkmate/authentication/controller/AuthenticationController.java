package com.checkmate.authentication.controller;

import com.checkmate.authentication.model.dto.requests.loginRequestDTO;
import com.checkmate.authentication.model.dto.requests.registrationRequestDTO;
import com.checkmate.authentication.model.dto.requests.verificationRequestDTO;
import com.checkmate.authentication.model.dto.responses.loginResponseDTO;
import com.checkmate.authentication.model.dto.responses.registrationResponseDTO;
import com.checkmate.authentication.model.error.ErrorResponse;
import com.checkmate.authentication.service.CredentialsService;
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
        System.out.println("Registering");
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

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody verificationRequestDTO body) {
        System.out.println("Recieved Token: " + body.getToken());
        boolean verified = authService.verify2FAToken(body.getToken());
        if (!verified) {
            return ResponseEntity.status(404).body("problem");
        }
        return ResponseEntity.ok("Verified");
    }

}
