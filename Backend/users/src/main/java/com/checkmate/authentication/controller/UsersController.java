package com.checkmate.authentication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public void test() {
        System.out.println("Gotten");
    }

    @PostMapping("/CreateUserProfile")
    public void cup() {
        System.out.println("Creating new user profile");
    }
}
