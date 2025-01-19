package com.tsc.sfgjms.listener;

import com.tsc.sfgjms.config.JmsConfig;
import com.tsc.sfgjms.model.Message;
import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MessageListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(@Payload Message message, MessageHeaders headers, jakarta.jms.Message jmsMessage) {
        System.out.println("Message received: " + message);
    }

    @JmsListener(destination = JmsConfig.MY_SEND_RECEIVE_QUEUE)
    public void listenToSendReceiveQueue(@Payload Message message, MessageHeaders headers, jakarta.jms.Message jmsMessage) throws JMSException {

        /*Message helloMessage = Message
                .builder()
                .id(UUID.randomUUID())
                .message("Hello World")
                .build();*/

        jmsTemplate.convertAndSend(jmsMessage.getJMSReplyTo(), message);
        System.out.println("Message sent/received: " + message);
    }

}
