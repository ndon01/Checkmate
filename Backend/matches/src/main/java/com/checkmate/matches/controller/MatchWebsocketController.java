package com.checkmate.matches.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class MatchWebsocketController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

}
