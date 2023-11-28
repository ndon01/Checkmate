package com.checkmate.matchmaking.controller;

import com.checkmate.matchmaking.config.RabbitMqConfig;
import com.checkmate.matchmaking.model.rabbitmq.MatchEventDTO;
import com.checkmate.matchmaking.model.rabbitmq.MatchEventType;
import com.checkmate.matchmaking.model.rabbitmq.UserEventDTO;
import com.checkmate.matchmaking.model.rabbitmq.UserEventType;
import com.checkmate.matchmaking.repository.QueuerRepository;
import com.checkmate.matchmaking.service.QueueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
public class RabbitMqController {

    @Autowired
    private QueueService queueService;

    @Autowired
    private QueuerRepository queuerRepository;

    private static final Logger logger = LoggerFactory.getLogger(RabbitMqController.class);

    @RabbitListener(queues = RabbitMqConfig.MATCHMAKING_USER_EVENTS_QUEUE)
    public void handleRegistrationEvent(UserEventDTO event) {
        if (event.getEventType() == UserEventType.USER_REGISTRATION) {
            System.out.println("Received registration event for user: " + event.getAdditionalDetails().get("username"));
            queueService.createQueuer(event.getUserId());
        }
    }

    @RabbitListener(queues = RabbitMqConfig.MATCHMAKING_MATCH_EVENT_QUEUE)
    public void handleMatchEvent(MatchEventDTO event) {

        if (event.getEventType() == MatchEventType.MATCH_FINISHED) {
            long matchId = Long.valueOf(event.getAdditionalDetails().get("matchId"));
            long whiteUserId = Long.valueOf(event.getAdditionalDetails().get("whiteUserId"));
            long blackUserId = Long.valueOf(event.getAdditionalDetails().get("blackUserId"));

            logger.info("Received match finished event for match: " + matchId);
            logger.info("Freeing White user: " + whiteUserId);

            queuerRepository.getQueuerByUserId(whiteUserId).ifPresent(queuer -> {
                queuer.setInQueue(false);
                queuer.setInMatch(false);
                queuerRepository.save(queuer);
            });

            logger.info("Freeing Black user: " + blackUserId);

            queuerRepository.getQueuerByUserId(blackUserId).ifPresent(queuer -> {
                queuer.setInQueue(false);
                queuer.setInMatch(false);
                queuerRepository.save(queuer);
            });

        }

    }

}
