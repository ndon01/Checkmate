package com.checkmate.matches.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.checkmate.matches.model.dto.requests.createMatchRequestDTO;
import com.checkmate.matches.model.entity.Match;
import com.checkmate.matches.repository.MatchRepository;
import com.checkmate.matches.security.PermissionRequired;
import com.checkmate.matches.security.RequiresJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class MatchController {

    private static final Logger logger = LoggerFactory.getLogger(MatchController.class);

    @Autowired
    private MatchRepository matchRepository;

    @GetMapping("/getCurrentMatch")
    @RequiresJWT
    public ResponseEntity<?> getCurrentMatch(HttpServletRequest request) {
        DecodedJWT decodedJWT = (DecodedJWT) request.getAttribute("decodedJWT");

        if (decodedJWT == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing JWT token.");
        }



        System.out.println(decodedJWT.getClaim("userId").asLong());
        return ResponseEntity.ok("Match details...");
    }





}
