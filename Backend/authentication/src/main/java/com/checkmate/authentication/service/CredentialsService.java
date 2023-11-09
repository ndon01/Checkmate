package com.checkmate.authentication.service;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.checkmate.authentication.config.RabbitMqConfig;
import com.checkmate.authentication.model.UserService.dto.requests.CreateUserRequestDTO;
import com.checkmate.authentication.model.UserService.dto.responses.CreateUserResponseDTO;
import com.checkmate.authentication.model.dto.responses.*;
import com.checkmate.authentication.model.entity.UserToken;
import com.checkmate.authentication.model.rabbitmq.VerificationMessage;
import com.checkmate.authentication.repository.UserCredentialsRepository;
import com.checkmate.authentication.repository.UserTokensRepository;
import com.checkmate.authentication.util.PasswordUtil;
import com.checkmate.authentication.util.TokenUtil;
import com.checkmate.authentication.util.ValidationUtil;
import com.checkmate.authentication.model.entity.UserCredential;
import org.apache.catalina.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import jakarta.annotation.PostConstruct;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class CredentialsService {

    private static final long ACCESS_TOKEN_EXPIRY_TIME = TimeUnit.HOURS.toMillis(1);
    private static final long REFRESH_TOKEN_EXPIRY_TIME= TimeUnit.DAYS.toMillis(14);

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
    public RegistrationResponseDTO register(String username, String password, String emailAddress, String dateOfBirth) {
        RegistrationResponseDTO response = new RegistrationResponseDTO();
        HashMap<String, String> fields = response.getFields();

        if (!ValidationUtil.isValidEmail(emailAddress)) { // email is invalid, registration failed
            response.setFailure(true);
            fields.put("emailAddress", "The email address provided is invalid.");
        }

        Optional<UserCredential> existingUser = credentialsRepository.findByUsername(username);

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
        UserCredential newUser = new UserCredential();


        String hashedPassword = PasswordUtil.encryptPassword(password); // encrypt the password

        // set fields
        newUser.setUsername(username);
        newUser.setEmailAddress(emailAddress);
        newUser.setPassword(hashedPassword);
        newUser.setEmailVerified(false);

        credentialsRepository.save(newUser); // save the user

        CreateUserRequestDTO createUserRequestDTO = new CreateUserRequestDTO();
        createUserRequestDTO.setCredentialsId(String.valueOf(newUser.getCredentialId()));
        createUserRequestDTO.setUsername(username);
        createUserRequestDTO.setEmailAddress(emailAddress);
        createUserRequestDTO.setDateOfBirth(dateOfBirth);


        // tell the user service
        System.out.println("Attempting to send message to user-service");
        CreateUserResponseDTO responseDTO = webClientBuilder
                .build()
                .post()
                .uri("http://USERS-SERVICE/api/users/create-user-profile")
                .header("Authorization", "Hidf231dfasa")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(createUserRequestDTO)
                .retrieve()
                .bodyToMono(CreateUserResponseDTO.class)
                .block();

        if (responseDTO == null) {
            System.out.println("Failed to send message to user-service");
            throw new RuntimeException("Failed to send message to user-service");
        }

        System.out.println("Sent message to user-service");

        if (!responseDTO.isFailure()) {
            Long userId = Long.parseLong(responseDTO.getUserId());
            newUser.setUserId(userId);
            credentialsRepository.save(newUser); // save the user
        }


        /*
            String tfaCode = generate2FACode(newUser);
            VerificationMessage m = new VerificationMessage();
            m.setEmail(emailAddress);
            m.setToken(tfaCode);
            rabbitTemplate.convertAndSend(RabbitMqConfig.VERIFICATION_QUEUE, m);
        */

        return response;
    }

    // get user credentials from identifier
    public Optional<UserCredential> getUserCredentialsFromIdentifier(String identifier) {
        if (ValidationUtil.isValidEmail(identifier)) {
            return credentialsRepository.findByEmailAddress(identifier);
        } else {
            return credentialsRepository.findByUsername(identifier);
        }
    }

    // Refresh Token Generation
    private String generateRefreshToken(UserCredential userCredential) {
        Date issueDate = new Date(System.currentTimeMillis());
        Date expireDate = new Date(issueDate.getTime() + REFRESH_TOKEN_EXPIRY_TIME);

        List<String> userRoles = new ArrayList<>();
        List<String> userPermissions = new ArrayList<>();
        UUID uuid = UUID.randomUUID();

        userPermissions.add("GENERATE_ACCESS_TOKEN");
        String refreshToken = TokenUtil.generateToken(issueDate, expireDate, userCredential.getUserId(), userRoles, userPermissions, uuid.toString());

        UserToken userToken = new UserToken();
        userToken.setCredential(userCredential);
        userToken.setTokenJwtId(uuid.toString());
        userToken.setCreatedAt(issueDate);
        userToken.setExpiresAt(expireDate);
        userToken.setTokenType(UserToken.TokenType.REFRESH);
        userToken.setTokenStatus(UserToken.TokenStatus.ACTIVE);

        userTokensRepository.save(userToken);

        return refreshToken;
    }

    // Access Token Generation
    private String generateAccessToken(UserCredential userCredential) {
        Date issueDate = new Date(System.currentTimeMillis());
        Date expireDate = new Date(issueDate.getTime() + ACCESS_TOKEN_EXPIRY_TIME);

        List<String> userRoles = new ArrayList<>();
        List<String> userPermissions = new ArrayList<>();
        UUID uuid = UUID.randomUUID();

        userRoles.add("Basic");

        String accessToken = TokenUtil.generateToken(issueDate, expireDate, userCredential.getUserId(), userRoles, userPermissions, uuid.toString());

        UserToken userToken = new UserToken();
        userToken.setCredential(userCredential);
        userToken.setTokenJwtId(uuid.toString());
        userToken.setCreatedAt(issueDate);
        userToken.setExpiresAt(expireDate);
        userToken.setTokenType(UserToken.TokenType.ACCESS);
        userToken.setTokenStatus(UserToken.TokenStatus.ACTIVE);

        userTokensRepository.save(userToken);

        return accessToken;
    }

    // Login Methods ( Infront of Refresh Token Generation )
    public String[] authenticateWithIdentifier(String Identifier, String password) {
        Optional<UserCredential> optionalUserCredentials = getUserCredentialsFromIdentifier(Identifier);

        if (optionalUserCredentials.isEmpty()) {
            throw new RuntimeException("Authentication Failure: Invalid Credentials");
        }

        UserCredential userCredentials = optionalUserCredentials.get();
        if (!PasswordUtil.matchPassword(password, userCredentials.getPassword())) {
            throw new RuntimeException("Authentication Failure: Invalid Credentials");
        }

        // 1) generate refresh token
        String refreshToken = generateRefreshToken(userCredentials);
        // 2) generate access token
        String accessToken = generateAccessToken(userCredentials);

        return new String[] { refreshToken, accessToken };
    }

    // Forgot Password
    public RequestPasswordChangeResponseDTO RequestPasswordChange(String identifier){
        RequestPasswordChangeResponseDTO response = new RequestPasswordChangeResponseDTO();
        HashMap<String, String> fields = response.getFields();

        Optional<UserCredential> optionalAuthProfile = getUserCredentialsFromIdentifier(identifier);

        if (optionalAuthProfile.isEmpty()) {
            response.setFailure(true);
            fields.put("Authentication Failure", "Something went wrong.");
            return response;
        }

        UserCredential userCredential = optionalAuthProfile.get();

        String tfaToken = TokenUtil.generateTFAToken(userCredential.getUserId(), "/change-password");

        UserToken userToken = new UserToken();

        userToken.setCredential(userCredential);
        userToken.setTokenType(UserToken.TokenType.TFA);


        userTokensRepository.save(userToken);

        VerificationMessage m = new VerificationMessage();
        m.setEmail(userCredential.getEmailAddress());
        m.setToken(tfaToken);
        m.setUserId(userCredential.getUserId().toString());


        rabbitTemplate.convertAndSend(RabbitMqConfig.VERIFICATION_QUEUE, m);

        return response;
    }

    public ChangePasswordResponseDTO ChangePassword(String token, String password) {
        ChangePasswordResponseDTO response = new ChangePasswordResponseDTO();
        HashMap<String, String> fields = response.getFields();

        DecodedJWT decodedJWT = TokenUtil.validateToken(token);

        if (decodedJWT == null) {
            throw new RuntimeException("Authentication Failure: Invalid Token.");
        }

        Long userId = decodedJWT.getClaim("userId").asLong();
        String jwtId = decodedJWT.getId();

        if (userId == null || jwtId == null) {
            throw new RuntimeException("Authentication Failure: Invalid Token.");
        }

        Optional<UserCredential> userCredentialOptional = credentialsRepository.findByUserId(userId);

        if (userCredentialOptional.isEmpty()) {
            throw new RuntimeException("Authentication Failure: Invalid Token.");
        }

        UserCredential userCredential = userCredentialOptional.get();

        Optional<UserToken> optionalUserTokens = userTokensRepository.findUserTokenByCredentialAndTokenJwtId(userCredential, jwtId);

        if (optionalUserTokens.isEmpty()) {
            response.setFailure(true);
            fields.put("Authentication Failure", "Something went wrong.");
            return response;
        }

        UserToken userTokens = optionalUserTokens.get();
        String hashedPassword = PasswordUtil.encryptPassword(password); // encrypt the password

        userCredential.setPassword(hashedPassword);

        credentialsRepository.save(userCredential); // save the user

        return response;
    }

  
    // 2FA
    public VerificationResponseDTO TFAVerification(String token, String userId) {
        VerificationResponseDTO response = new VerificationResponseDTO();
        Optional<UserCredential> optionalAuthProfile = credentialsRepository.findById(Long.parseLong(userId));

        System.out.println("Attempting to verify token");

        System.out.println(token);

        System.out.println(userId);

        if (optionalAuthProfile.isEmpty()) {
            throw new RuntimeException("Failed to validate Token");
        }
        System.out.println("Found user credentials");
        UserCredential userCredentials = optionalAuthProfile.get();

        // decrypt token
        DecodedJWT decodedJWT = TokenUtil.validateToken(token);
        System.out.println(decodedJWT);
        if (decodedJWT == null) {
            System.out.println("Failed to validate token");
            throw new RuntimeException("Failed to validate Token");
        }

        System.out.println("Decoded token");

        // generate new access token
        String refreshToken = generateRefreshToken(userCredentials);
        String accessToken = generateAccessToken(userCredentials);

        response.getFields().put("refreshToken", refreshToken);
        response.getFields().put("accessToken", accessToken);
        response.getFields().put("userId", userCredentials.getUserId().toString());
        response.getFields().put("username", userCredentials.getUsername());
        response.getFields().put("redirect", decodedJWT.getClaim("redirect").asString());

        return response;
    }

}
