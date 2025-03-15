package com.prototype.MessageQueues.controller;


import com.prototype.MessageQueues.kafka.KafkaProducer;
import com.prototype.MessageQueues.rabbitmq.RabbitMqProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alerts")
public class AlertController {
    private final KafkaProducer kafkaProducer;
    private final RabbitMqProducer rabbitMqProducer;

    @Autowired
    public AlertController(KafkaProducer kafkaProducer, RabbitMqProducer rabbitMqProducer) {
        this.kafkaProducer = kafkaProducer;
        this.rabbitMqProducer = rabbitMqProducer;
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
        kafkaProducer.sendMultipleAlerts(count);
        return "Sent " + count + " alerts via Kafka!";
    }

    @PostMapping("/send/rabbitmq/{count}")
    public String sendRabbitAlerts(@PathVariable int count) {
        rabbitMqProducer.sendMultipleAlerts(count);
        return "Sent " + count + " alerts via RabbitMQ!";
    }
}