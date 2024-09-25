package com.kafka.orderservice.web.controller;

import com.kafka.orderservice.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/kafka-service/order")
public class OrderController {
    @Value("${app.kafka.KafkaMessageTopic}")
    private String topicName;
    private final KafkaTemplate<String, Order> kafkaOrderTemplate;

    @PostMapping("")
    public ResponseEntity<String> sendOrder(@RequestBody Order message) {
        kafkaOrderTemplate.send(topicName, message);
        return ResponseEntity.ok("message send to Kafka");
    }
}
