package com.wzx.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class WebSocketController {
    private final SimpMessagingTemplate template;

    @Inject
    WebSocketController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/send/message")
    public void onReceivedMessage(String message) {
        this.template.convertAndSend("/chat", new SimpleDateFormat("HH:mm:ss").format(new Date()) + "_ " + message);
    }
}
