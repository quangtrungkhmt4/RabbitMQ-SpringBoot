package com.benjamin.gateway.controller;

import com.benjamin.gateway.service.MessageSender;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HandlerController {

    @Autowired
    private MessageSender messageSender;

    @GetMapping(value = "/send")
    public String sendMessage(@RequestParam("msg") String message){
        messageSender.send(message);
        return "Send success";
    }

    @RabbitListener(queues = "${benjamin.rabbitmq.response.queue}")
    public void recievedMessage(String message){
        System.out.println("Message from chat: " + message);
    }
}
