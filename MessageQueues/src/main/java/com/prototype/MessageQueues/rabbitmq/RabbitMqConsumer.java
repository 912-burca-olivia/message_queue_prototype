package com.prototype.MessageQueues.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Service
public class RabbitMqConsumer {
    private CountDownLatch latch;

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receiveAlert(String message) {
        System.out.println("RabbitMQ: Received alert -> " + message);
        if (latch != null) {
            latch.countDown(); // Signal that a message has been processed
        }
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }
}

