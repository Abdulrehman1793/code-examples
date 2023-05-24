package com.abdulrehman1793.awslambdareabbit.functions;

import com.abdulrehman1793.awslambdareabbit.config.MessagingConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.Supplier;

@Configuration
@RequiredArgsConstructor
public class Publisher {
    private final RabbitTemplate template;

    @Bean
    public Supplier<List<String>> hello() {
        template.convertAndSend(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY, "Publisher::hello");
        return () -> List.of("Hello", "world!");
    }

    @Bean
    public Supplier<List<String>> findAll() {
        template.convertAndSend(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY, "findAll");
        return () -> List.of("find", "all");
    }
}
