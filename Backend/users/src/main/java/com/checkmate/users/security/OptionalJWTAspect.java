package com.checkmate.users.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.checkmate.users.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class OptionalJWTAspect {

    private static final Logger logger = LoggerFactory.getLogger(OptionalJWTAspect.class);

    @Around("@annotation(optionalJWT)")
    public Object validateAndPassJWT(ProceedingJoinPoint joinPoint, OptionalJWT optionalJWT) throws Throwable {

        logger.info("OptionalJWT invoked...");


        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String authorization = request.getHeader("Authorization");

        // Check if Authorization header is present and bearer token is there
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            System.out.println("Authorization token is missing or not Bearer.");
            return joinPoint.proceed();
        }

        // Extract token
        String token = authorization.substring(7); // Skip "Bearer "
        DecodedJWT decodedJWT = TokenUtil.validateToken(token);

        // Check if token is valid
        if (decodedJWT == null) {
            System.out.println("Invalid Authorization token.");
            return joinPoint.proceed();
        }

        System.out.println("Valid JWT token.");

        request.setAttribute("decodedJWT", decodedJWT);

        // Proceed with the modified arguments
        return joinPoint.proceed();
    }

}