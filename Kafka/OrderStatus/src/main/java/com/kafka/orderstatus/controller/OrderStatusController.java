package com.kafka.orderstatus.controller;

import com.kafka.orderstatus.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;

import java.time.Instant;

@RequiredArgsConstructor
@Slf4j
@Controller
public class OrderStatusController {
    @Value("${app.kafka.KafkaMessageTopicProducer}")
    private String topicName;
    private final KafkaTemplate<String, OrderStatus> kafkaOrderTemplate;

    public String sendOrderStatus() {
        OrderStatus orderStatus = new OrderStatus("CREATED", Instant.now());
        kafkaOrderTemplate.send(topicName, orderStatus);
        return "Message send to Kafka";
    }
}
