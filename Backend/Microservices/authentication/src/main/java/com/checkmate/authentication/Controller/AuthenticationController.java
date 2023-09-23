package com.checkmate.authentication.Controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @autowired
    private AuthenticationProfileRepository authenticationRepository;

    // Login -> (Refresh Token, Access Token)
    @PostMapping("/login")
    public String proccessLogin() {
        System.out.println("Login");
        return "";
        
    }
    // Refresh-Token -> (Refresh Token, Access Token)
    @PostMapping("/refresh-token")
    public String refreshToken() {
        return "";
    }
    // Request-Reset -> "Check your {recieveType}"
    @PostMapping("/request")
    public String requestReset() {
        return "";
    }
    // Reset-Password -> (Refresh Token, Access Token)
    @PostMapping("/reset")
    public String processResetRequest() {
        return "";
    }

}
