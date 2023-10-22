package com.checkmate.users.service;

import com.checkmate.users.model.dto.responses.loginResponseDTO;
import com.checkmate.users.model.dto.responses.registrationResponseDTO;
import com.checkmate.users.model.entity.UserCredentials;
import com.checkmate.users.repository.UserCredentialsRepository;
import com.checkmate.users.util.PasswordUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class CredentialsService {

    @Autowired
    private UserCredentialsRepository credentialsRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private PasswordUtil passwordUtility;


    // Registration

    @Transactional
    public registrationResponseDTO register(String username, String password, String emailAddress) {
        registrationResponseDTO response = new registrationResponseDTO();

        if (!isValidEmail(emailAddress)) {
            response.setFailure(true);
            response.setFailReason("The email address provided is invalid.");
            return response;
        }

        Optional<UserCredentials> existingUser = credentialsRepository.findByUsername(username);

        if (existingUser.isPresent()) {
            // username exists already, return failure
            response.setFailure(true);
            response.setFailReason("The username provided is already taken.");

            return response;
        }


        existingUser = credentialsRepository.findByEmailAddress(emailAddress);
        if (existingUser.isPresent()) {
            // email is already in use, return failure
            response.setFailure(true);
            response.setFailReason("The email address provided is already in use.");

            return response;
        }

        String hashedPassword = passwordUtility.encryptPassword(password);

        UserCredentials newUser = new UserCredentials();

        newUser.setUsername(username);
        newUser.setEmailAddress(emailAddress);
        newUser.setPassword(hashedPassword);
        newUser.setEmailVerified(false);

        credentialsRepository.save(newUser);
        tokenService.registerUserToken(newUser);

        System.out.println("Attempting to send message to user-service");
        webClientBuilder
                .build()
                .get()
                .uri("http://USERS-SERVICE/api/users")
                .retrieve().bodyToMono(String.class)
                .block();

        return response;
    }

    // Authentication Methods
    private loginResponseDTO authenticateWithOptionalProfile(Optional<UserCredentials> optionalAuthProfile, String password) {
        loginResponseDTO response = new loginResponseDTO();

        if (optionalAuthProfile.isEmpty()) {
            response.setFailure(true);
            response.setFailReason("Authentication Failure");
            return response;
        }

        UserCredentials authProfile = optionalAuthProfile.get();
        if (!passwordUtility.matchPassword(password, authProfile.getPassword())) {
            response.setFailure(true);
            response.setFailReason("Authentication Failure");
            return response;
        }

        return response;
    }

    public loginResponseDTO authenticateWithUsername(String username, String password) {
        Optional<UserCredentials> optionalAuthProfile = credentialsRepository.findByUsername(username);
        return authenticateWithOptionalProfile(optionalAuthProfile, password);
    }

    public loginResponseDTO authenticateWithEmail(String email, String password) {
        Optional<UserCredentials> optionalAuthProfile = credentialsRepository.findByEmailAddress(email);
        return authenticateWithOptionalProfile(optionalAuthProfile, password);
    }

    public loginResponseDTO authenticateWithIdentifier(String identifier, String password) {
        if (isValidEmail(identifier)) {
            return authenticateWithEmail(identifier, password);
        } else {
            return authenticateWithUsername(identifier, password);
        }
    }

    // email check method
    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).matches();
    }
}
