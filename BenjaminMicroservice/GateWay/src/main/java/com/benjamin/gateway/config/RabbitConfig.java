package com.benjamin.gateway.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${benjamin.rabbitmq.queue}")
    String queueName;

    @Value("${benjamin.rabbitmq.exchange}")
    String exchange;

    @Value("${benjamin.rabbitmq.routingkey}")
    private String routingkey;

    @Value("${benjamin.rabbitmq.response.queue}")
    String responseQueueName;

    @Value("${benjamin.rabbitmq.response.routingkey}")
    private String responseRoutingkey;

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    Queue responseQueue() {
        return new Queue(responseQueueName, false);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    Binding binding(Queue queue, Queue responseQueue, DirectExchange exchange) {
        BindingBuilder.bind(responseQueue).to(exchange).with(responseRoutingkey);
        return BindingBuilder.bind(queue).to(exchange).with(routingkey);
    }


    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public AmqpTemplate rabbitmqTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}
