package com.benjamin.chatservice.controller;

import com.benjamin.chatservice.service.MessageSender;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class HandlerMessage {

    @Autowired
    private MessageSender messageSender;

    @RabbitListener(queues = "${benjamin.rabbitmq.queue}")
    public void recievedMessage(String message){
        System.out.println("Message recieved: " + message);
        Random random = new Random();
        int number = random.nextInt();
        System.out.println("Random number: " + number);
        messageSender.send(String.valueOf(number));
    }

}
