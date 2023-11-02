package com.checkmate.authentication.service;

import com.checkmate.authentication.config.RabbitMqConfig;
import com.checkmate.authentication.model.dto.responses.loginResponseDTO;
import com.checkmate.authentication.model.dto.responses.registrationResponseDTO;
import com.checkmate.authentication.model.entity.UserCredentials;
import com.checkmate.authentication.model.entity.UserTokens;
import com.checkmate.authentication.model.rabbitmq.VerificationMessage;
import com.checkmate.authentication.repository.UserCredentialsRepository;
import com.checkmate.authentication.repository.UserTokensRepository;
import com.checkmate.authentication.util.PasswordUtil;
import com.checkmate.authentication.util.RandomStringGenerator;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.checkmate.authentication.util.TokenUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import jakarta.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import com.checkmate.authentication.util.ValidationUtil;

@Service
public class CredentialsService {

    @Autowired
    private UserCredentialsRepository credentialsRepository;

    @Autowired
    private UserTokensRepository userTokensRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    }

    // Registration
    @Transactional
    public registrationResponseDTO register(String username, String password, String emailAddress) {
        registrationResponseDTO response = new registrationResponseDTO();
        HashMap<String, String> fields = response.getFields();

        if (!ValidationUtil.isValidEmail(emailAddress)) { // email is invalid, registration failed
            response.setFailure(true);
            fields.put("emailAddress", "The email address provided is invalid.");
        }

        Optional<UserCredentials> existingUser = credentialsRepository.findByUsername(username);

        if (existingUser.isPresent()) { // username exists, registration failed
            response.setFailure(true);
            response.getFields().put("username", "The username provided is taken.");
        }

        existingUser = credentialsRepository.findByEmailAddress(emailAddress);

        if (existingUser.isPresent()) { // email is in use, registration failed
            response.setFailure(true);
            response.getFields().put("emailAddress", "The email address provided is already in use.");
        }

        if (response.isFailure()) { // if failure occurred then return response
            return response;
        }

        // no failure occurred, create account
        UserCredentials newUser = new UserCredentials();


        String hashedPassword = PasswordUtil.encryptPassword(password); // encrypt the password

        // set fields
        newUser.setUsername(username);
        newUser.setEmailAddress(emailAddress);
        newUser.setPassword(hashedPassword);
        newUser.setEmailVerified(false);

        credentialsRepository.save(newUser); // save the user
        registerUserToken(newUser); // register their tokens

        // tell the user service
        System.out.println("Attempting to send message to user-service");
        webClientBuilder
                .build()
                .get()
                .uri("http://USERS-SERVICE/api/users")
                .retrieve().bodyToMono(String.class)
                .block();

        /*
            String tfaCode = generate2FACode(newUser);
            VerificationMessage m = new VerificationMessage();
            m.setEmail(emailAddress);
            m.setToken(tfaCode);
            rabbitTemplate.convertAndSend(RabbitMqConfig.VERIFICATION_QUEUE, m);
        */

        return response;
    }

    public void registerUserToken(UserCredentials User) {
        System.out.println("Registering User Tokens");
        UserTokens newUserTokens = new UserTokens();
        newUserTokens.setUser(User);
        newUserTokens.setRefreshToken("ABCD");
        userTokensRepository.save(newUserTokens);
    }

    private UserTokens getUserTokensFromUserCredentials(UserCredentials user) {
        Optional<UserTokens> oUToken = userTokensRepository.findByUser_UserId(user.getUserId());
        return oUToken.orElse(null);
    }




    // Authentication Methods
    private loginResponseDTO authenticateWithOptionalProfile(Optional<UserCredentials> optionalAuthProfile, String password) {
        loginResponseDTO response = new loginResponseDTO();
        HashMap<String, String> fields = response.getFields();

        if (optionalAuthProfile.isEmpty()) {
            response.setFailure(true);
            fields.put("Authentication Failure", "Something went wrong.");
            return response;
        }

        UserCredentials userCredentials = optionalAuthProfile.get();
        if (!PasswordUtil.matchPassword(password, userCredentials.getPassword())) {
            response.setFailure(true);
            fields.put("Authentication Failure", "Something went wrong.");
            return response;
        }


        // 1) generate refresh token
        String refreshToken = TokenUtil.generateRefreshToken(userCredentials.getUserId());
        // 2) generate access token
        String accessToken = TokenUtil.generateAccessToken(userCredentials.getUserId());
        // 3) append these tokens into the response
        response.setRefreshToken(refreshToken);
        response.setAccessToken(accessToken);

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
        if (ValidationUtil.isValidEmail(identifier)) {
            return authenticateWithEmail(identifier, password);
        } else {
            return authenticateWithUsername(identifier, password);
        }
    }

    //
}
