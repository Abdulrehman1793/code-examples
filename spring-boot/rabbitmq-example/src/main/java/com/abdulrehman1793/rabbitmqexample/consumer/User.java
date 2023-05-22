package com.abdulrehman1793.rabbitmqexample.consumer;

import com.abdulrehman1793.rabbitmqexample.config.MessagingConfig;
import com.abdulrehman1793.rabbitmqexample.dto.OrderStatus;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class User {
    @RabbitListener(queues = MessagingConfig.QUEUE)
    public void consumeMessageFromQueue(OrderStatus orderStatus) {
        System.out.println("Message received from queue : " + orderStatus);
    }
}
