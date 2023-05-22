package com.abdulrehman1793.rabbitmqexample.consumer;

import com.abdulrehman1793.rabbitmqexample.config.MessagingConfig;
import com.abdulrehman1793.rabbitmqexample.dto.OrderStatus;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class User {
    @RabbitListener(queues = MessagingConfig.QUEUE)
    public void consumeMessageFromQueue(OrderStatus orderStatus) {
        System.out.println("Message received from queue : " + orderStatus);
    }

    @RabbitListener(queues = MessagingConfig.QUEUE)
    public void handleMessage(Message message, Channel channel) throws IOException {
        try {
            byte[] payloadBytes = message.getBody();
            String payload = new String(payloadBytes, StandardCharsets.UTF_8);

            System.out.println("Message acknowledge :" + payload);
            // Process the payload (message) and store data to the database
            // If an error occurs, throw an exception

            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); // Acknowledge the message
        } catch (Exception e) {
            // Handle the error and decide whether to acknowledge or reject the message
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true); // Reject and requeue the message for retry
        }
    }
}
