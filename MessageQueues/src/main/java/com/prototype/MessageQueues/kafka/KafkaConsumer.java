package com.prototype.MessageQueues.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.util.concurrent.CountDownLatch;

@Service
public class KafkaConsumer {
    private CountDownLatch latch;

    @KafkaListener(topics = "alerts_topic", groupId = "alert-group")
    public void processAlert(String message) {
        System.out.println("Kafka: Received alert -> " + message);
        if (latch != null) {
            latch.countDown(); // Signal that a message has been processed
        }
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }
}
