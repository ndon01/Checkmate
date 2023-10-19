package com.checkmate.authentication.service;

import com.checkmate.authentication.model.entity.AuthProfile;
import com.checkmate.authentication.repository.AuthProfileRepository;
import com.checkmate.authentication.util.PasswordUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class AuthService {

    @Autowired
    private AuthProfileRepository authProfileRepository;

    @Autowired
    private PasswordUtil passwordUtility;

    @Transactional
    public AuthProfile register(String username, String password, String email) {

        String hashedPassword = passwordUtility.encryptPassword(password);

        AuthProfile authProfile = AuthProfile.builder()
                .username(username)
                .password(hashedPassword)
                .emailAddress(email)
                .isEmailValid(false)
                .build();

        return authProfileRepository.save(authProfile);
    }

    public Optional<AuthProfile> authenticateWithUsername(String username, String password) {
        Optional<AuthProfile> optionalAuthProfile = authProfileRepository.findByUsername(username);
        if (optionalAuthProfile.isEmpty()) {
            return Optional.empty(); // no user found
        }

        AuthProfile authProfile = optionalAuthProfile.get();

        if (passwordUtility.matchPassword(password, authProfile.getPassword())) {
            return Optional.of(authProfile); // password matched
        } else {
            return Optional.empty(); // password didn't match
        }
    }

    public Optional<AuthProfile> authenticateWithEmail(String email, String password) {
        Optional<AuthProfile> optionalAuthProfile = authProfileRepository.findByEmailAddress(email);
        return authenticateWithOptionalProfile(optionalAuthProfile, password);
    }

    public Optional<AuthProfile> authenticateWithIdentifier(String identifier, String password) {
        if (isValidEmail(identifier)) {
            return authenticateWithEmail(identifier, password);
        } else {
            return authenticateWithUsername(identifier, password);
        }
    }

    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).matches();
    }

    private Optional<AuthProfile> authenticateWithOptionalProfile(Optional<AuthProfile> optionalAuthProfile, String password) {
        if (optionalAuthProfile.isEmpty()) {
            return Optional.empty();
        }

        AuthProfile authProfile = optionalAuthProfile.get();
        if (passwordUtility.matchPassword(password, authProfile.getPassword())) {
            return Optional.of(authProfile);
        } else {
            return Optional.empty();
        }
    }

    public String generateAuthToken(AuthProfile authProfile) {
        // Here you would typically generate a JWT or another kind of auth token.
        // For simplicity, I'm returning a mock token.
        return "MOCK_AUTH_TOKEN_FOR_" + authProfile.getUsername();
    }

    public String generateRefreshToken(AuthProfile authProfile) {
        // Again, in reality, you'd use a library for this.
        // This mock token is just for illustration.
        return "MOCK_REFRESH_TOKEN_FOR_" + authProfile.getUsername();
    }

    public boolean validateAuthToken(String token) {
        // Validate the token. In a real scenario, you'd decode the token and check its signature, etc.
        return token.startsWith("MOCK_AUTH_TOKEN_FOR_");
    }

    public boolean validateRefreshToken(String token) {
        // Validate the refresh token similarly.
        return token.startsWith("MOCK_REFRESH_TOKEN_FOR_");
    }
}
