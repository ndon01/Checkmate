package com.checkmate.users.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    public static final String VERIFICATION_QUEUE = "verification_email_queue";
    @Bean
    public Queue verificationEmailQueue() {
        return new Queue(VERIFICATION_QUEUE);
    }

}
