package com.checkmate.matches.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class RabbitMqConfig {

    public static final String MATCHES_MATCH_EVENT_QUEUE = "matches_match_event_queue";
    public static final String MATCH_EVENT_EXCHANGE = "match_event_exchange";

    @Bean
    Queue matchesMatchEventQueue() {
        return new Queue(MATCHES_MATCH_EVENT_QUEUE, false, false, true);
    }

    @Bean
    FanoutExchange matchEventExchange() {
        return new FanoutExchange(MATCH_EVENT_EXCHANGE);
    }

    @Bean
    Binding bindingMatchEvent(Queue matchesMatchEventQueue, FanoutExchange matchEventExchange) {
        return BindingBuilder.bind(matchesMatchEventQueue).to(matchEventExchange);
    }

    public static final String MATCH_MICROSERVICE_QUEUE = UUID.randomUUID().toString();
    public static final String MATCH_MICROSERVICE_EXCHANGE = "match_microservice_exchange";

    @Bean
    Queue matchMicroserviceQueue() {
        return new Queue(MATCH_MICROSERVICE_QUEUE, false, false, true);
    }
    @Bean
    DirectExchange matchMicroserviceExchange() {
        return new DirectExchange(MATCH_MICROSERVICE_EXCHANGE);
    }
    @Bean
    Binding bindingMatchMicroservice(Queue matchMicroserviceQueue, DirectExchange matchMicroserviceExchange) {
        return BindingBuilder.bind(matchMicroserviceQueue).to(matchMicroserviceExchange).withQueueName();
    }


    // JSON Message Converter
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
