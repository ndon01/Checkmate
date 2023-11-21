package com.checkmate.matchmaking.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebsocketController {
    private static final Logger logger = LoggerFactory.getLogger(WebsocketController.class);
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public WebsocketController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/joinQueue")
    public void handleQueueMessage(@Payload String message) {
        // Process the message here
        System.out.println("Received message: " + message);
        // You can deserialize the JSON message here and perform actions based on its content
        simpMessagingTemplate.convertAndSend(message);
    }


}
