package com.wzx.middleware;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @RabbitListener(queues = "rank.queue")
    public String process(String msg) {
        System.out.println("Receiver: " + msg);
        return "your rank";
    }
}
