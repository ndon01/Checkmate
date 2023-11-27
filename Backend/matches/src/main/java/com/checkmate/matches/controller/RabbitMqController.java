package com.checkmate.matches.controller;

import com.checkmate.matches.config.RabbitMqConfig;
import com.checkmate.matches.model.rabbitmq.MatchEventDTO;
import com.checkmate.matches.model.rabbitmq.MatchEventType;
import com.checkmate.matches.service.MatchService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.amqp.core.Queue;


@Controller
public class RabbitMqController {

    @Autowired
    private MatchService matchService;

    @RabbitListener(queues = RabbitMqConfig.MATCHES_MATCH_EVENT_QUEUE)
    public void handleMatchEvent(MatchEventDTO event) {

        if (event.getEventType() == MatchEventType.MATCH_CREATED) {
            System.out.println("Received match created event for match: ");

            Long User1 = Long.valueOf(event.getAdditionalDetails().get("user1"));
            Long User2 = Long.valueOf(event.getAdditionalDetails().get("user2"));

            matchService.createMatch(User1, User2);

        }

    }
}
