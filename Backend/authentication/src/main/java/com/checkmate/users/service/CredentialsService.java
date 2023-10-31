package com.checkmate.users.service;

import com.checkmate.users.config.RabbitMqConfig;
import com.checkmate.users.model.dto.responses.loginResponseDTO;
import com.checkmate.users.model.dto.responses.registrationResponseDTO;
import com.checkmate.users.model.entity.UserCredentials;
import com.checkmate.users.model.entity.UserTokens;
import com.checkmate.users.model.rabbitmq.VerificationMessage;
import com.checkmate.users.repository.UserCredentialsRepository;
import com.checkmate.users.repository.UserTokensRepository;
import com.checkmate.users.util.PasswordUtil;
import com.checkmate.users.util.RandomStringGenerator;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
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

@Service
public class CredentialsService {

    @Autowired
    private UserCredentialsRepository credentialsRepository;

    @Autowired
    private UserTokensRepository userTokensRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private PasswordUtil passwordUtility;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final String privateKey = "TokenServicePrivateKey";

    @PostConstruct
    public void init() {
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    }

    // Registration
    @Transactional
    public registrationResponseDTO register(String username, String password, String emailAddress) {
        registrationResponseDTO response = new registrationResponseDTO();
        response.setFields(new HashMap<String, String>());
        if (!isValidEmail(emailAddress)) {
            response.setFailure(true);
            response.getFields().put("emailAddress", "The email address provided is invalid.");
        }

        Optional<UserCredentials> existingUser = credentialsRepository.findByUsername(username);

        if (existingUser.isPresent()) {
            // username exists already, return failure
            response.setFailure(true);
            response.getFields().put("username", "The username provided is taken.");

        }

        existingUser = credentialsRepository.findByEmailAddress(emailAddress);
        if (existingUser.isPresent()) {
            // email is already in use, return failure
            response.setFailure(true);
            response.getFields().put("emailAddress", "The email address provided is already in use.");
        }

        if (response.isFailure()) {
            return response;
        }

        String hashedPassword = passwordUtility.encryptPassword(password);

        UserCredentials newUser = new UserCredentials();

        newUser.setUsername(username);
        newUser.setEmailAddress(emailAddress);
        newUser.setPassword(hashedPassword);
        newUser.setEmailVerified(false);

        credentialsRepository.save(newUser);
        registerUserToken(newUser);

        System.out.println("Attempting to send message to user-service");
        webClientBuilder
                .build()
                .get()
                .uri("http://USERS-SERVICE/api/users")
                .retrieve().bodyToMono(String.class)
                .block();

        String tfaCode = generate2FACode(newUser);

        VerificationMessage m = new VerificationMessage();
        m.setEmail(emailAddress);
        m.setToken(tfaCode);


        rabbitTemplate.convertAndSend(RabbitMqConfig.VERIFICATION_QUEUE, m);

        return response;
    }

    // Authentication Methods


    private String generateRefreshToken(UserCredentials user) {
        return "";
    }


    private String generateAccessToken(String refreshToken) {
        return "";
    }

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

    //
    public void ValidateEmail(UserCredentials User) {
        User.setEmailVerified(true);
        credentialsRepository.save(User);
    }

    public void registerUserToken(UserCredentials User) {
        System.out.println("Registering User Tokens");
        UserTokens newUserTokens = new UserTokens();
        newUserTokens.setUser(User);
        newUserTokens.setRefreshToken("ABCD");
        userTokensRepository.save(newUserTokens);
    }

    public boolean verify2FAToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(privateKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("Authentication")
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);

            // Compare the claim with the provided code
            String storedCode = decodedJWT.getClaim("code").asString();
            long storedUserId = decodedJWT.getClaim("userId").asLong();
            Optional<UserTokens> oUToken = userTokensRepository.findByUser_UserId(storedUserId);

            if (oUToken.isEmpty()) {
                return false;
            }

            UserTokens userTokens = oUToken.get();

            if (!userTokens.getTfaToken().equals(token)) {
                return false;
            }

            userTokens.setTfaToken("");
            ValidateEmail(userTokens.getUser());
            return true;

        } catch (TokenExpiredException e) {
            // Handle token expiry
            System.out.println("Token has expired");
            return false;

        } catch (JWTVerificationException e) {
            // Token verification failed
            System.out.println("Token verification failed");
            return false;
        }

    }

    public boolean verify2FACode(UserCredentials User, String providedCode) {
        Optional<UserTokens> oUToken = userTokensRepository.findByUser_UserId(User.getUserId());
        if (oUToken.isEmpty()) {
            return false;
        }

        UserTokens userTokens = oUToken.get();

        String tfaToken = userTokens.getTfaToken();

        // Decode the token
        try {
            Algorithm algorithm = Algorithm.HMAC256(privateKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("Authentication")
                    .build();

            DecodedJWT decodedJWT = verifier.verify(tfaToken);

            // Compare the claim with the provided code
            String storedCode = decodedJWT.getClaim("code").asString();


            if (storedCode.equals(providedCode)) {
                return true;
            } else {
                return false;
            }

        } catch (TokenExpiredException e) {
            // Handle token expiry
            System.out.println("Token has expired");
            return false;

        } catch (JWTVerificationException e) {
            // Token verification failed
            System.out.println("Token verification failed");
            return false;
        }

    }

    public String generate2FACode(UserCredentials User) {
        Optional<UserTokens> oUToken = userTokensRepository.findByUser_UserId(User.getUserId());

        if (oUToken.isEmpty()) {
            // Handle error, maybe create a new UserToken for this user?
            return null;
        }

        UserTokens userTokens = oUToken.get();

        // Generate random 6 characters long code
        String code;
        try {
            code = RandomStringGenerator.generateRandomString(6);
        } catch (Exception e) {
            // Handle error
            System.out.println("Error generating random string: " + e.getMessage());
            return null;
        }

        Date issueDate = new Date(System.currentTimeMillis());
        long expireMillis = issueDate.getTime() + TimeUnit.MINUTES.toMillis(10);
        Date expireDate = new Date(expireMillis);

        String token;
        try {
            Algorithm algorithm = Algorithm.HMAC256(privateKey);
            token = JWT.create()
                    .withIssuer("Authentication")
                    .withClaim("code", code)
                    .withClaim("userId", User.getUserId())
                    .withIssuedAt(issueDate)
                    .withExpiresAt(expireDate)
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            // Handle error
            System.out.println("Error creating JWT: " + e.getMessage());
            return null;
        }

        userTokens.setTfaToken(token);

        // Save the updated userTokens to the database
        userTokensRepository.save(userTokens);

        return token;
    }

}
