package com.wzx.middleware;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Sender implements RabbitTemplate.ConfirmCallback {
    private final RabbitTemplate rabbitTemplate;
    private final DirectExchange directExchange;

    @Autowired
    public Sender(RabbitTemplate rabbitTemplate, DirectExchange directExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setConfirmCallback(this);
        this.directExchange = directExchange;
    }

    public void send(String msg) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        System.out.println("开始发送消息 : " + msg.toLowerCase());
        String response = rabbitTemplate.convertSendAndReceive(directExchange.getName(), "rank.routingKey", msg, correlationData).toString();
        System.out.println("消费者响应 : " + response + " 消息处理完成");
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("回调id: " + correlationData.getId());
        if (ack) {
            System.out.println("消息成功消费");
        } else {
            System.out.println("消息消费失败:" + cause);
        }
    }
}
