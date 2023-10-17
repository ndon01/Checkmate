package com.checkmate.authentication.controller;

import com.checkmate.authentication.service.AuthService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register() {
        // Similar to the login, you'd have some logic here to handle registration,
        // saving the user to a database, etc.

        return ResponseEntity.ok("");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login() {
        // You would typically have some authentication logic here,
        // maybe using the AuthService, JWT creation, etc.

        // Here's a very simple check:
        return ResponseEntity.ok("");
    }
}
