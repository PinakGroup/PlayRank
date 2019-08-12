package com.wzx.middleware;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Receiver {
    @RabbitListener(queues = "rank.queue")
    public String process(String msg) throws InterruptedException {
        Thread.sleep(2000);
        System.out.println("Receiver: " + msg);
        return "your rank: " + new Date().toString();
    }
}
