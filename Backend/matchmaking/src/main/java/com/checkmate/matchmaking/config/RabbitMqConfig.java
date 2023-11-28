package com.checkmate.matchmaking.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class RabbitMqConfig {

    public static final String MATCH_EVENT_EXCHANGE = "match_event_exchange";

    public static final String MATCHMAKING_MATCH_EVENT_QUEUE = "matches_match_event_queue";


    @Bean
    Queue matchmakingMatchEventQueue() {
        return new Queue(MATCHMAKING_MATCH_EVENT_QUEUE, false, false, true);
    }

    @Bean
    FanoutExchange matchEventExchange() {
        return new FanoutExchange(MATCH_EVENT_EXCHANGE);
    }

    @Bean
    Binding bindingMatchEvent(Queue matchmakingMatchEventQueue, FanoutExchange matchEventExchange) {
        return BindingBuilder.bind(matchmakingMatchEventQueue).to(matchEventExchange);
    }

    public static final String MATCHMAKING_MICROSERVICE_QUEUE = UUID.randomUUID().toString();
    public static final String MATCHMAKING_MICROSERVICE_EXCHANGE = "matchmaking_microservice_exchange";

    @Bean
    Queue matchmakingMicroserviceQueue() {
        return new Queue(MATCHMAKING_MICROSERVICE_QUEUE, true);
    }

    @Bean
    DirectExchange matchmakingMicroserviceExchange() {
        return new DirectExchange(MATCHMAKING_MICROSERVICE_EXCHANGE);
    }

    @Bean
    Binding bindingMatchmakingMicroservice(Queue matchmakingMicroserviceQueue, DirectExchange matchmakingMicroserviceExchange) {
        return BindingBuilder.bind(matchmakingMicroserviceQueue).to(matchmakingMicroserviceExchange).withQueueName();
    }

    // Match Microservice Direct

    public static final String MATCH_MICROSERVICE_QUEUE = "match_microservice_queue";
    public static final String MATCH_MICROSERVICE_EXCHANGE = "match_microservice_exchange";

    @Bean
    Queue matchMicroserviceQueue() {
        return new Queue(MATCH_MICROSERVICE_QUEUE, true);
    }
    @Bean
    DirectExchange matchMicroserviceExchange() {
        return new DirectExchange(MATCH_MICROSERVICE_EXCHANGE);
    }
    @Bean
    Binding bindingMatchMicroservice(Queue matchMicroserviceQueue, DirectExchange matchMicroserviceExchange) {
        return BindingBuilder.bind(matchMicroserviceQueue).to(matchMicroserviceExchange).withQueueName();
    }


    // Queues
    public static final String MATCHMAKING_USER_EVENTS_QUEUE = "matchmaking_user_events_queue";
    public static final String USER_EVENTS_EXCHANGE = "user_events_exchange";

    @Bean
    Queue userEventsQueue() {
        return new Queue(MATCHMAKING_USER_EVENTS_QUEUE);
    }

    // Fanout Exchange for User Events
    @Bean
    FanoutExchange userEventsExchange() {
        return new FanoutExchange(USER_EVENTS_EXCHANGE);
    }

    // Binding User Events Queue to User Events Exchange
    @Bean
    Binding bindingUserEvents(Queue userEventsQueue, FanoutExchange userEventsExchange) {
        return BindingBuilder.bind(userEventsQueue).to(userEventsExchange);
    }

    // JSON Message Converter
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
