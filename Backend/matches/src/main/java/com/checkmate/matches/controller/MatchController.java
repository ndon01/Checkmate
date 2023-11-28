package com.checkmate.matches.controller;

import com.checkmate.matches.*;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.checkmate.matches.model.dto.requests.createMatchRequestDTO;
import com.checkmate.matches.model.entity.Match;
import com.checkmate.matches.repository.MatchRepository;
import com.checkmate.matches.security.PermissionRequired;
import com.checkmate.matches.security.RequiresJWT;
import com.checkmate.matches.service.MatchService;
import com.netflix.discovery.converters.Auto;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
public class MatchController {

    private static final Logger logger = LoggerFactory.getLogger(MatchController.class);

    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private MatchService matchService;

    @GetMapping("/getCurrentMatch")
    @RequiresJWT
    public ResponseEntity<?> getCurrentMatch(HttpServletRequest request) {
        DecodedJWT decodedJWT = (DecodedJWT) request.getAttribute("decodedJWT");

        if (decodedJWT == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing JWT token.");
        }

        Optional<Match> match = matchRepository.findActiveMatchByUserId(decodedJWT.getClaim("userId").asLong());

        if (match.isEmpty()) {
            matchService.debugUserMatches(decodedJWT.getClaim("userId").asLong());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No active match found.");
        }

        return ResponseEntity.ok(match.get().getMatchId());
    }

    @PostMapping("/pingMatch")
    @RequiresJWT
    @Transactional
    public ResponseEntity<?> pingMatch(@RequestParam("matchId") long matchId, HttpServletRequest request) {
        DecodedJWT decodedJWT = (DecodedJWT) request.getAttribute("decodedJWT");

        if (decodedJWT == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing JWT token.");
        }

        long userId = decodedJWT.getClaim("userId").asLong();

        Match match = matchService.pingMatch(matchId, userId);

        return ResponseEntity.ok(match);
    }

    @PostMapping("/makeMove")
    @RequiresJWT
    public ResponseEntity<?> makeMove(HttpServletRequest request)
    {
        DecodedJWT decodedJWT = (DecodedJWT) request.getAttribute("decodedJWT");


        return ResponseEntity.ok("Move made.");

    }

    @PostMapping("/forfeitMatch")
    @RequiresJWT
    public ResponseEntity<?> forfeitMatch(HttpServletRequest request)
    {
        DecodedJWT decodedJWT = (DecodedJWT) request.getAttribute("decodedJWT");

        return ResponseEntity.ok("Match forfeited.");

    }

    @PostMapping("/requestDraw")
    @RequiresJWT
    public ResponseEntity<?> requestDraw(HttpServletRequest request)
    {
        DecodedJWT decodedJWT = (DecodedJWT) request.getAttribute("decodedJWT");

        return ResponseEntity.ok("Draw request response received.");

    }

    @PostMapping("/respondToDrawRequest")
    @RequiresJWT
    public ResponseEntity<?> respondToDrawRequest(HttpServletRequest request)
    {
        DecodedJWT decodedJWT = (DecodedJWT) request.getAttribute("decodedJWT");

        return ResponseEntity.ok("Draw request response received.");

    }

}
