package com.wzx.middleware;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

@Component
public class Sender implements RabbitTemplate.ReturnCallback {
    private final RabbitTemplate rabbitTemplate;
    private final Random r = new Random();
    private final ObjectMapper mapper = new ObjectMapper();

    public Sender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("消息主体 message : " + message);
        System.out.println("消息主体 message : " + replyCode);
        System.out.println("描述：" + replyText);
        System.out.println("消息使用的交换器 exchange : " + exchange);
        System.out.println("消息使用的路由键 routing : " + routingKey);
    }

    public void send() {
        HashMap<String, String> map = new HashMap<>();
        map.put("time", new Date().toString());
        map.put("rank", String.valueOf(r.nextInt(100)));
        String content = "";
        try {
            content = mapper.writeValueAsString(map);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 通过实现 ReturnCallback 接口，启动消息失败返回，比如路由不到队列时触发回调
        rabbitTemplate.setReturnCallback(this);

        // 通过实现 ConfirmCallback 接口，消息发送到 Broker 后触发回调，
        // 确认消息是否到达 Broker 服务器，也就是只确认是否正确到达 Exchange 中
        rabbitTemplate.setConfirmCallback((CorrelationData correlationData, boolean ack, String cause) -> {
            if (ack) {
                System.out.println("Sender: 消息发送成功");
            } else {
                System.out.println("Sender: 消息发送失败，" + cause + correlationData.toString());
            }
        });

        rabbitTemplate.convertAndSend("rank.exchange", "rank.routingKey", content);
    }
}
