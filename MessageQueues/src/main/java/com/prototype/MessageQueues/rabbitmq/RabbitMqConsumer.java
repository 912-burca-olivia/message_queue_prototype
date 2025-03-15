package com.prototype.MessageQueues.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqConsumer {

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receiveAlert(String message) {
        System.out.println("RabbitMQ: Received alert -> " + message);
    }
}

