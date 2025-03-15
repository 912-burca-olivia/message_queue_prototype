package com.prototype.MessageQueues.config;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Bean
    public Queue queue() {
        return new Queue("alerts_queue", true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("alerts_exchange");
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("alerts_routing");
    }
}
