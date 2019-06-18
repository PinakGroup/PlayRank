package com.wzx.middleware;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @RabbitListener(queues = "date")
    public void processDate(String date) {
        System.out.println("Receiver : " + date);
    }
}
