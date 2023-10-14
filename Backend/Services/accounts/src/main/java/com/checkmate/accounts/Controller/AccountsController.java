package com.checkmate.accounts.Controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountsController {

    // Register -> (Refresh Token, Access Token)
    @PostMapping("/register")
    public String processRegistration() {
        System.out.println("register");
        return "";
        
    }

    // Login -> (Refresh Token, Access Token)
    @PostMapping("/login")
    public String processLogin() {
        System.out.println("login");
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
