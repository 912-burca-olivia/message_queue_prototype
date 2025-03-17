package com.prototype.MessageQueues.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Service
public class RabbitMqProducer {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMqProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routingKey}")
    private String routingKey;

    public void sendAlert(String message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
        System.out.println("RabbitMQ: Sent alert -> " + message);
    }

    public void sendMultipleAlerts(int messageCount, CountDownLatch latch) {
        for (int i = 1; i <= messageCount; i++) {
            String message = "Emergency Alert #" + i + " - " + System.currentTimeMillis();
            rabbitTemplate.convertAndSend(exchange, routingKey, message);
            System.out.println("RabbitMQ: Sent alert -> " + message);
        }

        // Wait until all messages are consumed (processed by RabbitMqConsumer)
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}