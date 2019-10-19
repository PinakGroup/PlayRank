package com.wzx.middleware;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RabbitListener(queues = "rank.queue")
public class Receiver {
    @RabbitHandler
    public void process(String sendMsg, Channel channel, Message message) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("AckReceiver: 收到了一条消息<" + sendMsg + ">，收到此消息时间<"
                + new Date().toString() + ">");

        try {
            // 告诉服务器收到这条消息已经被当前消费者消费了，可以在队列安全删除，这样后面就不会再重发了，
            // 否则消息服务器以为这条消息没处理掉，后续还会再发
            // 第二个参数是消息的标识，false只确认当前一个消息收到，true确认所有consumer获得的消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println("AckReceiver: 已成功消费");
        } catch (Exception e) {
            System.out.println("AckReceiver: 消费失败");
            e.printStackTrace();
        }
    }
}
