package com.abdulrehman1793.rabbitmqaws.publisher;

import com.abdulrehman1793.rabbitmqaws.config.MessagingConfig;
import com.abdulrehman1793.rabbitmqaws.dto.Order;
import com.abdulrehman1793.rabbitmqaws.dto.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderPublisher {
    private final RabbitTemplate template;

    @PostMapping("/{restaurantName}")
    public String bookOrder(@RequestBody Order order, @PathVariable String restaurantName) {
        order.setOrderId(UUID.randomUUID().toString());
        OrderStatus orderStatus = new OrderStatus(order, "PROCESS", "order place successfully in " + restaurantName);

        template.convertAndSend(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY, orderStatus);
        return "success !";
    }
}
