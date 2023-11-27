package com.checkmate.matchmaking.controller;

import com.checkmate.matchmaking.config.RabbitMqConfig;
import com.checkmate.matchmaking.model.rabbitmq.MatchEventDTO;
import com.checkmate.matchmaking.model.rabbitmq.MatchEventType;
import com.checkmate.matchmaking.model.rabbitmq.UserEventDTO;
import com.checkmate.matchmaking.model.rabbitmq.UserEventType;
import com.checkmate.matchmaking.service.QueueService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
public class RabbitMqController {

    @Autowired
    private QueueService queueService;

    @RabbitListener(queues = RabbitMqConfig.MATCHMAKING_USER_EVENTS_QUEUE)
    public void handleRegistrationEvent(UserEventDTO event) {
        if (event.getEventType() == UserEventType.USER_REGISTRATION) {
            System.out.println("Received registration event for user: " + event.getAdditionalDetails().get("username"));
            queueService.createQueuer(event.getUserId());
        }
    }

}
