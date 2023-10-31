package com.checkmate.users.controller;

import com.checkmate.users.model.dto.requests.loginRequestDTO;
import com.checkmate.users.model.dto.requests.registrationRequestDTO;
import com.checkmate.users.model.dto.requests.verificationRequestDTO;
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
    private CredentialsService credentialsService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody registrationRequestDTO registrationData) {
        System.out.println("Registering");
        registrationResponseDTO regAttempt = credentialsService.register(
                registrationData.getUsername(),
                registrationData.getPassword(),
                registrationData.getEmailAddress()
        );

        if (regAttempt.isFailure()) {
            System.out.println(regAttempt);
            return ResponseEntity.status(409).body(regAttempt);
        }

        return ResponseEntity.status(200).body(regAttempt);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody loginRequestDTO loginData) {
        loginResponseDTO loginAttempt = credentialsService.authenticateWithIdentifier(loginData.getIdentifier(), loginData.getPassword());

        if (loginAttempt.isFailure()) {
            ErrorResponse err = new ErrorResponse();
            err.setStatus(409);
            err.setError("Conflict");
            err.setMessage("Authentication Failure");

            return ResponseEntity.status(409).body(err);
        }

        return ResponseEntity.ok("Authenticated");
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody verificationRequestDTO body) {
        System.out.println("Recieved Token: " + body.getToken());
        boolean verified = credentialsService.verify2FAToken(body.getToken());
        if (!verified) {
            return ResponseEntity.status(404).body("problem");
        }
        return ResponseEntity.ok("Verified");
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody verificationRequestDTO body) {
        System.out.println("Recieved Token: " + body.getToken());
        boolean verified = credentialsService.verify2FAToken(body.getToken());
        if (!verified) {
            return ResponseEntity.status(404).body("problem");
        }
        return ResponseEntity.ok("Verified");
    }

}
