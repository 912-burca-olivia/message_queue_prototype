package com.prototype.MessageQueues.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "alerts_topic", groupId = "alert-group")
    public void processAlert(String message) {
        System.out.println("Kafka: Received alert -> " + message);
    }
}
