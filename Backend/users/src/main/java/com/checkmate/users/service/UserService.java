package com.checkmate.users.service;

import com.checkmate.users.model.entity.User;
import com.checkmate.users.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUserProfile(long credentialId) {
        logger.info("Attempting to register user with credential ID: {}", credentialId);

        if (userRepository.findByCredentialId(credentialId).isPresent()) {
            logger.warn("User registration attempted with existing credential ID: {}", credentialId);
            throw new RuntimeException("User already exists with credential ID: " + credentialId);
        }

        // Create a new User entity and set properties from the provided parameters
        User newUser = new User();
        newUser.setCredentialId(credentialId);

        // newUser.setUsername(username);
        // newUser.setEmail(email);
        // ... set other fields ...

        // Save the new User entity to the repository
        User savedUser = userRepository.save(newUser);
        logger.info("User registered with ID: {}", savedUser.getUserId());

        return savedUser;
    }
}
