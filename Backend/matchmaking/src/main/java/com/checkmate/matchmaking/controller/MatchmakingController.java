package com.checkmate.matchmaking.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.checkmate.matchmaking.model.dto.requests.EnterQueueRequest;
import com.checkmate.matchmaking.model.dto.requests.LeaveQueueRequest;
import com.checkmate.matchmaking.model.dto.requests.QueueDetailsRequest;
import com.checkmate.matchmaking.model.dto.responses.QueueDetailsResponse;
import com.checkmate.matchmaking.model.entity.Queuer;
import com.checkmate.matchmaking.security.RequiresJWT;
import com.checkmate.matchmaking.service.QueueService;
import com.checkmate.matchmaking.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
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
    @RequiresJWT
    public ResponseEntity<?> checkUserQueueDetails(HttpServletRequest request) {
        DecodedJWT decodedJWT = (DecodedJWT) request.getAttribute("decodedJWT");

        if (decodedJWT == null) {
            return ResponseEntity.status(401).build();
        }

        Queuer queuer = queueService.getQueuerByUserId(decodedJWT.getClaim("userId").asLong());

        if (queuer == null) {
            return ResponseEntity.status(404).build();
        }

        QueueDetailsResponse queueDetailsResponse = new QueueDetailsResponse();

        queueDetailsResponse.setMatchFound(queuer.isInMatch());

        return ResponseEntity.status(200).body(queueDetailsResponse);
    }

}
