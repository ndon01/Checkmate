package com.checkmate.authentication.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    public static final String VERIFICATION_QUEUE = "verification_email_queue";
    public static final String USER_EVENTS_EXCHANGE = "user_events_exchange";

    @Bean
    public Queue verificationEmailQueue() {
        return new Queue(VERIFICATION_QUEUE);
    }

    @Bean
    public FanoutExchange userEventsExchange() {
        return new FanoutExchange(USER_EVENTS_EXCHANGE);
    }

    @Bean
    public Binding binding(Queue verificationEmailQueue, FanoutExchange userEventsExchange) {
        return BindingBuilder.bind(verificationEmailQueue).to(userEventsExchange);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
