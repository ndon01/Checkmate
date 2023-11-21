package com.checkmate.users.service;

import com.checkmate.users.model.entity.User;
import com.checkmate.users.model.entity.UserProfile;
import com.checkmate.users.repository.UserProfileRepository;
import com.checkmate.users.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserProfileRepository userProfileRepository) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
    }

    @Transactional
    public User registerUser(long credentialId, String username) {
        logger.info("Attempting to register user with credential ID: {}", credentialId);

        if (userRepository.findByCredentialId(credentialId).isPresent()) {
            logger.warn("User registration attempted with existing credential ID: {}", credentialId);
            throw new RuntimeException("User already exists with credential ID: " + credentialId);
        }

        User newUser = new User();
        newUser.setUserId(credentialId);
        newUser.setCredentialId(credentialId);
        newUser.setUsername(username);
        newUser.setDisplayName(username);
        userRepository.save(newUser);

        UserProfile newUserProfile = new UserProfile();
        newUserProfile.setUser(newUser);

        userProfileRepository.save(newUserProfile);

        newUser.setUserProfile(newUserProfile);

        userRepository.save(newUser);


        logger.info("User registered with ID: {}", newUser.getUserId());

        return newUser;
    }

    public List<User> searchForUser(String searchQuery) {
        return userRepository.findUserByUsernameContainingIgnoreCase(searchQuery);
    }
}
