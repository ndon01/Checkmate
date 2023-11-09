package com.checkmate.users.controller;

import com.checkmate.users.model.dto.requests.CreateUserRequestDTO;
import com.checkmate.users.model.dto.responses.CreateUserResponseDTO;
import com.checkmate.users.model.entity.User;
import com.checkmate.users.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);
    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String test(HttpServletRequest request) {
        logger.info("Request received from IP: {}", request.getRemoteAddr());
        logger.info("Test endpoint called");
        return "API is up and running!";
    }

    @PostMapping("/create-user-profile")
    public ResponseEntity<CreateUserResponseDTO> createUserProfile(@RequestBody CreateUserRequestDTO body, @RequestHeader("Authorization") String token) {
        logger.info("Creating user profile for user ID: {}", body.getCredentialsId());
        logger.info("Request Body: {}", body);
        logger.info("Token: {}", token);

        // You would call a service method to handle the creation logic.
        User user = userService.registerUserProfile(Long.parseLong(body.getCredentialsId()));

        // Assuming registerUserProfile would return the created entity or DTO
        // You would usually return the created object or some acknowledgment of creation

        CreateUserResponseDTO response = new CreateUserResponseDTO();
        response.setUserId(user.getUserId().toString());

        return ResponseEntity.ok(response);
    }
}
