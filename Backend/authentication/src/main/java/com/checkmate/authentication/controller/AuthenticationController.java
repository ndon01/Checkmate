package com.checkmate.authentication.controller;

import com.checkmate.authentication.model.dto.requests.*;
import com.checkmate.authentication.model.dto.responses.*;
import com.checkmate.authentication.model.error.ErrorResponse;
import com.checkmate.authentication.service.CredentialsService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private CredentialsService credentialsService;



    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequestDTO registrationData, HttpServletResponse response, HttpServletRequest request){
        System.out.println("Registering");


        RegistrationResponseDTO regAttempt = credentialsService.register(
                registrationData.getUsername(),
                registrationData.getPassword(),
                registrationData.getEmailAddress(),
                registrationData.getDateOfBirth()
        );

        if (regAttempt.isFailure()) {
            System.out.println(regAttempt);
            return ResponseEntity.status(409).body(regAttempt);
        }

        return ResponseEntity.status(200).body(regAttempt);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginData, HttpServletResponse response, HttpServletRequest request) {
        String[] loginAttempt;
        try {
            loginAttempt = credentialsService.authenticateWithIdentifier(loginData.getIdentifier(), loginData.getPassword());
        } catch (Exception e) {
            ErrorResponse err = new ErrorResponse();
            err.setStatus(409);
            err.setError("Conflict");
            err.setMessage("Authentication Failure");
            return ResponseEntity.status(409).body(err);
        }
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();

        // login attempt will return credentials
        // create response entity

        return ResponseEntity.status(201).body(loginAttempt);
    }

    @PostMapping("/request-password-change")
    public ResponseEntity<?> requestPasswordChange(@RequestBody RequestPasswordChangeDTO body, HttpServletResponse response, HttpServletRequest request) {
        RequestPasswordChangeResponseDTO requestPasswordChangeResponseDTO = credentialsService.RequestPasswordChange(body.getIdentifier());

        if (requestPasswordChangeResponseDTO.isFailure()) {
            ErrorResponse err = new ErrorResponse();
            err.setStatus(409);
            err.setError("Conflict");
            err.setMessage("Authentication Failure");

            return ResponseEntity.status(409).body(err);
        }
        // create response entity
        return ResponseEntity.status(201).body(requestPasswordChangeResponseDTO);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequestDTO request,
            @RequestHeader(value = "Authorization") String authorizationHeader, // Extracting the token
            HttpServletResponse response) {


        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Extract the token (assuming it's a Bearer token)

            try {
                credentialsService.ChangePassword(token, request.getPassword()); // Assume ChangePassword method now also takes the token as a parameter
            } catch (Exception e) {
                ErrorResponse err = new ErrorResponse();
                err.setStatus(409);
                err.setError("Conflict");
                err.setMessage("Authentication Failure");

                return ResponseEntity.status(409).body(err);
            }
            // create response entity
            return ResponseEntity.ok(new ChangePasswordResponseDTO());

        } else {

            ErrorResponse err = new ErrorResponse();
            err.setStatus(401); // Unauthorized or bad request depending on your API design
            err.setError("Unauthorized");
            err.setMessage("No Authorization header or invalid format");

            return ResponseEntity.status(401).body(err);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {

        return ResponseEntity.status(201).body("Logged out");
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody VerificationRequestDTO verificationData) {
        VerificationResponseDTO verificationAttempt;
        try {
            verificationAttempt = credentialsService.TFAVerification(verificationData.getToken(), verificationData.getUserId());
        } catch (Exception e) {
            ErrorResponse err = new ErrorResponse();
            err.setStatus(409);
            err.setError("Conflict");
            err.setMessage("Authentication Failure");

            return ResponseEntity.status(409).body(err);
        }

        System.out.println(verificationAttempt);

        return ResponseEntity.status(200).body(verificationAttempt);
    }


}
