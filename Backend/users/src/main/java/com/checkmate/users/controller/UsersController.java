package com.checkmate.users.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.checkmate.users.model.dto.responses.UserProfileDTO;
import com.checkmate.users.model.dto.responses.UserSearchResultDTO;
import com.checkmate.users.model.entity.User;
import com.checkmate.users.model.entity.UserProfile;
import com.checkmate.users.repository.UserRepository;
import com.checkmate.users.security.PermissionRequired;
import com.checkmate.users.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UsersController {

    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);
    private final UserService userService;

    private final UserRepository userRepository;


    public UsersController(UserService userService, UserRepository userRepository) {

        this.userService = userService;
        this.userRepository = userRepository;

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String test(HttpServletRequest request) {
        logger.info("Request received from IP: {}", request.getRemoteAddr());
        logger.info("Test endpoint called");
        return "API is up and running!";
    }

    @GetMapping("/searchForUsers")
    public List<UserSearchResultDTO> searchForUsers(@RequestParam("searchQuery") String searchQuery) {
        List<User> userList = userService.searchForUser(searchQuery);

        List<UserSearchResultDTO> userSearchList = new ArrayList<>();

        for (User user : userList) {
            UserSearchResultDTO thisUser = new UserSearchResultDTO();
            thisUser.setUserId(user.getUserId());
            thisUser.setUsername(user.getUsername());
            thisUser.setDisplayName(user.getDisplayName());

            userSearchList.add(thisUser);
        }

        return userSearchList;

    }


    @GetMapping("/getUserProfile")
    public ResponseEntity<?> getUserProfile(@RequestParam("userId") Long userId, @RequestHeader("Authorization") String authToken) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return ResponseEntity.status(404).body("User not found.");
        }

        UserProfile userProfile = user.getUserProfile();

        if (userProfile == null) {
            return ResponseEntity.status(404).body("User profile not found.");
        }

        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setUserId(user.getUserId());
        userProfileDTO.setDisplayName(user.getDisplayName());
        userProfileDTO.setBiography(userProfile.getUserBiography());



        return ResponseEntity.ok().body(user);
    }
}
