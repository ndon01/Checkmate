package com.checkmate.matches.controller;

import com.checkmate.matches.model.dto.requests.createMatchRequestDTO;
import com.checkmate.matches.model.entity.Match;
import com.checkmate.matches.repository.MatchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private static final Logger logger = LoggerFactory.getLogger(MatchController.class);

    @Autowired
    private MatchRepository matchRepository;

    @PostMapping("/createMatch")
    private ResponseEntity<?> createMatch(@RequestBody createMatchRequestDTO body) {

        Optional<Match> whiteMatch = matchRepository.findActiveMatchByUserId(body.getWhiteUserId());
        Optional<Match> blackMatch = matchRepository.findActiveMatchByUserId(body.getBlackUserId());

        if (whiteMatch.isPresent() || blackMatch.isPresent()) {
            logger.info("A user is already in a match");
            return ResponseEntity.status(404).body("A user is already in match");
        }

        Match match = new Match();
        match.setWhiteUserId(body.getWhiteUserId());
        match.setBlackUserId(body.getBlackUserId());

        matchRepository.save(match);

        return ResponseEntity.ok(match.getMatchId());
    }



    @PostMapping("/makeMove")
    private void makeMove() {

    }

    @GetMapping("/getMatch")
    private void getMatch() {

    }





}
