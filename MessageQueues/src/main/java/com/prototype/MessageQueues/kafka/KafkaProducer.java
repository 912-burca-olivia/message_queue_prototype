package com.prototype.MessageQueues.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendAlert(String message) {
        kafkaTemplate.send("alerts_topic", message);
        System.out.println("Kafka: Sent alert -> " + message);
    }

    public void sendMultipleAlerts(int messageCount) {
        for (int i = 1; i <= messageCount; i++) {
            String message = "Emergency Alert #" + i + " - " + System.currentTimeMillis();
            kafkaTemplate.send("alerts_topic", message);
            System.out.println("Kafka: Sent alert -> " + message);
        }
    }
}
