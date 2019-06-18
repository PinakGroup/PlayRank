package com.wzx.middleware;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Sender {
    private final AmqpTemplate rabbitTemplate;
    private final Queue dateQueue;

    @Autowired
    public Sender(AmqpTemplate rabbitTemplate, Queue dateQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.dateQueue = dateQueue;
    }


    public void send() {
        String context = "Date ".concat(new SimpleDateFormat("yyyy-MM-dd HH:mm:dd").format(new Date()));
        System.out.println("Sender : " + context);
        rabbitTemplate.convertAndSend(dateQueue.getName(), context);
    }

}
