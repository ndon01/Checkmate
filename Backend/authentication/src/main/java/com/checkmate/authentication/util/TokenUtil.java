package com.checkmate.authentication.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.checkmate.authentication.model.entity.UserCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class TokenUtil {

    private static final Logger logger = LoggerFactory.getLogger(TokenUtil.class);

    private static final String ISSUER = "Authentication Service";
    private static final long ACCESS_TOKEN_EXPIRY_TIME = TimeUnit.HOURS.toMillis(1);
    private static final long REFRESH_TOKEN_EXPIRY_TIME= TimeUnit.DAYS.toMillis(14); //

    private static final String privateKey = "Authenticaton";

    public DecodedJWT validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(privateKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build();
            return verifier.verify(token);
        } catch (JWTDecodeException exception) {
            logger.error("Invalid token: {}", exception.getMessage());
            return null;
        }
    }

    public static String generateRefreshToken(long userId) {
        Date issueDate = new Date(System.currentTimeMillis());
        Date expireDate = new Date(issueDate.getTime() + REFRESH_TOKEN_EXPIRY_TIME);

        try {
            Algorithm algorithm = Algorithm.HMAC256(privateKey);
            return JWT.create()
                    .withIssuer("AuthService")
                    .withSubject(String.valueOf(userId))
                    .withAudience("all")
                    .withIssuedAt(issueDate)
                    .withExpiresAt(expireDate)

                    .withClaim("userId", userId)

                    .sign(algorithm);
        } catch (Exception e) {
            logger.error("Error creating refresh Token: {}", e.getMessage());
            throw new RuntimeException("Failed to create token", e);
        }
    }

    public static String generateAccessToken(Long userId) {
        Date issueDate = new Date(System.currentTimeMillis());
        Date expireDate = new Date(issueDate.getTime() + REFRESH_TOKEN_EXPIRY_TIME);

        try {
            Algorithm algorithm = Algorithm.HMAC256(privateKey);
            return JWT.create()
                    .withIssuer("AuthService")
                    .withSubject(String.valueOf(userId))
                    .withAudience("all")
                    .withIssuedAt(issueDate)
                    .withExpiresAt(expireDate)

                    .withClaim("userId", userId)
                    .withClaim("roles", "none")

                    .sign(algorithm);
        } catch (Exception e) {
            logger.error("Error creating access Token: {}", e.getMessage());
            throw new RuntimeException("Failed to create token", e);
        }
    }


}
