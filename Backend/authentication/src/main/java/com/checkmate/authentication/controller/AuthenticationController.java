package com.checkmate.authentication.controller;

import com.checkmate.authentication.model.dto.requests.loginRequestDTO;
import com.checkmate.authentication.model.dto.requests.registrationRequestDTO;
import com.checkmate.authentication.model.dto.requests.verificationRequestDTO;
import com.checkmate.authentication.model.dto.responses.loginResponseDTO;
import com.checkmate.authentication.model.dto.responses.registrationResponseDTO;
import com.checkmate.authentication.model.error.ErrorResponse;
import com.checkmate.authentication.service.CredentialsService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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

    private void setCookie(String name, String value, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");  // set path for cookie
        cookie.setHttpOnly(true);  // marks the cookie as HttpOnly
        cookie.setSecure(true);  // ensures the cookie is sent over HTTPS. Consider checking if you're in a production environment.
        cookie.setMaxAge(3600);  // cookie expiration time in seconds. Adjust as necessary.
        response.addCookie(cookie);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody loginRequestDTO loginData, HttpServletResponse response) {
        loginResponseDTO loginAttempt = credentialsService.authenticateWithIdentifier(loginData.getIdentifier(), loginData.getPassword());

        if (loginAttempt.isFailure()) {
            ErrorResponse err = new ErrorResponse();
            err.setStatus(409);
            err.setError("Conflict");
            err.setMessage("Authentication Failure");

            return ResponseEntity.status(409).body(err);
        }

        setCookie("accessToken", loginAttempt.getAccessToken(), response);
        setCookie("refreshToken", loginAttempt.getRefreshToken(), response);


        // create response entity
        return ResponseEntity.status(201).body(loginAttempt);
    }

}
