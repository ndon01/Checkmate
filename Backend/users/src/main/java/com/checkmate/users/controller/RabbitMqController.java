package com.checkmate.users.controller;

import com.checkmate.users.model.rabbitmq.UserEventDTO;
import com.checkmate.users.model.rabbitmq.UserEventType;
import com.checkmate.users.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.checkmate.users.config.RabbitMqConfig;



@Controller
public class RabbitMqController {

    @Autowired
    private UserService userService;

    @RabbitListener(queues = RabbitMqConfig.USERS_USER_EVENTS_QUEUE)
    public void handleRegistrationEvent(UserEventDTO event) {
        if (event.getEventType() == UserEventType.USER_REGISTRATION) {
            System.out.println("Received registration event for user: " + event.getAdditionalDetails().get("username"));
            userService.registerUser(event.getUserId(), event.getAdditionalDetails().get("username"));
        }

        // Event Type is Remove Tokens

        // Event Type is Add Tokens
    }
}
