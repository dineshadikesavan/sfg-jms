package com.tsc.sfgjms.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsc.sfgjms.config.JmsConfig;
import com.tsc.sfgjms.model.Message;
import jakarta.jms.JMSException;
import jakarta.jms.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class Sender {

    private final JmsTemplate jmsTemplate;

    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 5000)
    public void sendMessage() {
        System.out.println("Sending message");

        Message message = Message.builder()
                .id(UUID.randomUUID())
                .message("Hello World")
                .build();

        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, message);

        System.out.println("Sent message");
    }

    @Scheduled(fixedRate = 5000)
    public void sendReceiveMessage() {
        System.out.println("Sending/Receiving message");

        Message message = Message.builder()
                .id(UUID.randomUUID())
                .message("Hello World")
                .build();

        jmsTemplate.sendAndReceive(JmsConfig.MY_SEND_RECEIVE_QUEUE, new MessageCreator() {
            @Override
            public jakarta.jms.Message createMessage(Session session) throws JMSException {
                try {
                    jakarta.jms.Message outputMessage = session.createTextMessage(objectMapper.writeValueAsString(message));
                    outputMessage.setStringProperty("_type", "com.tsc.sfgjms.model.Message");
                    System.out.println(objectMapper.writeValueAsString(message));
                    System.out.println("Sent/Received message" + outputMessage.getBody(String.class));
                    return outputMessage;
                } catch (JsonProcessingException e) {
                    throw new JMSException(e.getMessage());
                }
            }
        });

    }
}
