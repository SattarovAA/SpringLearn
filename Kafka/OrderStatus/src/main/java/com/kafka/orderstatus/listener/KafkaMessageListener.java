package com.kafka.orderstatus.listener;

import com.kafka.orderstatus.controller.OrderStatusController;
import com.kafka.orderstatus.model.Order;
import com.kafka.orderstatus.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaMessageListener {
    private final OrderStatusController orderStatusController;

    @KafkaListener(topics = "${app.kafka.KafkaMessageTopicConsumer}",
            groupId = "${app.kafka.KafkaMessageGroupId}",
            containerFactory = "kafkaListenerContainerFactory")
    void orderListener(@Payload Order message,
                       @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) UUID key,
                       @Header(value = KafkaHeaders.RECEIVED_PARTITION, required = false) Integer partition,
                       @Header(value = KafkaHeaders.RECEIVED_TIMESTAMP, required = false) Long timestamp,
                       @Header(value = KafkaHeaders.RECEIVED_TOPIC, required = false) String topic) {
        log.info("Topic: order-topic");
        log.info("Received message: {}", message);
        log.info("Key: {}, Partition: {}, Timestamp: {}, Topic: {}", key, partition, timestamp, topic);
        orderStatusController.sendOrderStatus();
    }

    @KafkaListener(topics = "${app.kafka.KafkaMessageTopicProducer}",
            groupId = "${app.kafka.KafkaMessageGroupId}",
            containerFactory = "kafkaListenerContainerFactoryForStatus")
    void orderStatusListener(@Payload OrderStatus message,
                             @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) UUID key,
                             @Header(value = KafkaHeaders.RECEIVED_PARTITION, required = false) Integer partition,
                             @Header(value = KafkaHeaders.RECEIVED_TIMESTAMP, required = false) Long timestamp,
                             @Header(value = KafkaHeaders.RECEIVED_TOPIC, required = false) String topic) {
        log.info("Topic: order-status-service");
        log.info("Received message: {}", message);
        log.info("Key: {}, Partition: {}, Timestamp: {}, Topic: {}", key, partition, timestamp, topic);
    }
}
