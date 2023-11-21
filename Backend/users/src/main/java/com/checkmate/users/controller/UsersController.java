package com.checkmate.users.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.checkmate.users.model.dto.responses.UserSearchResultDTO;
import com.checkmate.users.model.entity.User;
import com.checkmate.users.security.PermissionRequired;
import com.checkmate.users.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
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

    @GetMapping("/user-search")
    public List<UserSearchResultDTO> searchForUsers(@RequestParam("searchQuery") String searchQuery) {
        List<User> userList = userService.searchForUser(searchQuery);

        return userList.stream()
                .map(user -> new UserSearchResultDTO(user.getUserId(), user.getUsername()))
                .collect(Collectors.toList());

    }
}
