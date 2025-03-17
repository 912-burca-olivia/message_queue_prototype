package com.prototype.MessageQueues.controller;


import com.prototype.MessageQueues.kafka.KafkaConsumer;
import com.prototype.MessageQueues.kafka.KafkaProducer;
import com.prototype.MessageQueues.rabbitmq.RabbitMqConsumer;
import com.prototype.MessageQueues.rabbitmq.RabbitMqProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.CountDownLatch;

@RestController
@RequestMapping("/alerts")
public class AlertController {
    private final KafkaProducer kafkaProducer;
    private final RabbitMqProducer rabbitMqProducer;
    private final KafkaConsumer kafkaConsumer;
    private final RabbitMqConsumer rabbitMqConsumer;

    @Autowired
    public AlertController(KafkaProducer kafkaProducer, RabbitMqProducer rabbitMqProducer, KafkaConsumer kafkaConsumer, RabbitMqConsumer rabbitMqConsumer) {
        this.kafkaProducer = kafkaProducer;
        this.rabbitMqProducer = rabbitMqProducer;
        this.kafkaConsumer = kafkaConsumer;
        this.rabbitMqConsumer = rabbitMqConsumer;
    }

    @PostMapping("/send/kafka")
    public String sendKafkaAlert(@RequestBody String message) {
        kafkaProducer.sendAlert(message);
        return "Sent alert via Kafka!";
    }

    @PostMapping("/send/rabbitmq")
    public String sendRabbitAlert(@RequestBody String message) {
        rabbitMqProducer.sendAlert(message);
        return "Sent alert via RabbitMQ!";
    }

    @PostMapping("/send/kafka/{count}")
    public String sendKafkaAlerts(@PathVariable int count) {
        // Create a CountDownLatch with the number of messages to be sent
        CountDownLatch latch = new CountDownLatch(count);
        kafkaConsumer.setLatch(latch);

        kafkaProducer.sendMultipleAlerts(count, latch);

        // Wait for all messages to be processed
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "Error while waiting for messages to be processed!";
        }

        return "Sent " + count + " alerts via Kafka!";
    }

    @PostMapping("/send/rabbitmq/{count}")
    public String sendRabbitAlerts(@PathVariable int count) {
        // Create a CountDownLatch with the number of messages to be sent
        CountDownLatch latch = new CountDownLatch(count);
        rabbitMqConsumer.setLatch(latch);

        rabbitMqProducer.sendMultipleAlerts(count, latch);

        // Wait for all messages to be processed
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "Error while waiting for messages to be processed!";
        }

        return "Sent " + count + " alerts via RabbitMQ!";
    }
}