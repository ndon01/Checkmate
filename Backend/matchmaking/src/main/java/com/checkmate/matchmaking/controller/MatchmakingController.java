package com.checkmate.matchmaking.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.checkmate.matchmaking.model.dto.requests.EnterQueueRequest;
import com.checkmate.matchmaking.model.dto.requests.LeaveQueueRequest;
import com.checkmate.matchmaking.model.dto.requests.QueueDetailsRequest;
import com.checkmate.matchmaking.service.QueueService;
import com.checkmate.matchmaking.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MatchmakingController {

    private static final Logger logger = LoggerFactory.getLogger(MatchmakingController.class);

    @Autowired
    private QueueService queueService;

    @GetMapping("/ping")
    public String getPing() {
        return "pong";
    }

    @PostMapping("/enter")
    public ResponseEntity<?> enterQueue(@RequestBody EnterQueueRequest enterQueueRequest) {
        DecodedJWT decodedJWT = TokenUtil.validateToken(enterQueueRequest.getToken());

        System.out.println(enterQueueRequest.getToken());

        System.out.println(decodedJWT);

        if (decodedJWT == null) {
            return ResponseEntity.status(401).body("fail");
        }

        System.out.println(decodedJWT.getClaim("userId"));

        queueService.queueUserId(decodedJWT.getClaim("userId").asLong());

        return ResponseEntity.status(200).body("success");
    }

    @PostMapping("/leave")
    public ResponseEntity<?> leaveQueue(@RequestBody LeaveQueueRequest leaveQueueRequest) {
        DecodedJWT decodedJWT = TokenUtil.validateToken(leaveQueueRequest.getToken());

        if (decodedJWT == null) {
            return ResponseEntity.status(401).body("fail");
        }

        queueService.dequeueUserId(decodedJWT.getClaim("userId").asLong());

        return ResponseEntity.status(200).body("success");
    }

    @GetMapping("/queue")
    public ResponseEntity<?> checkUserQueueDetails(@RequestBody QueueDetailsRequest queueDetailsRequest) {
        DecodedJWT decodedJWT = TokenUtil.validateToken(queueDetailsRequest.getToken());

        if (decodedJWT == null) {
            return ResponseEntity.status(401).body("fail");
        }

        queueService.dequeueUserId(decodedJWT.getClaim("userId").asLong());

        return ResponseEntity.status(200).body("success");
    }

}
