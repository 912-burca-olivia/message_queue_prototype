package com.prototype.MessageQueues.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic alertsTopic() {
        return new NewTopic("alerts_topic", 1, (short) 1);
    }
}
