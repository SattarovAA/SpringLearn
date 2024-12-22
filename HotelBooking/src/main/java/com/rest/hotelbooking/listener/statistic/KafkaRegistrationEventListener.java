package com.rest.hotelbooking.listener.statistic;

import com.rest.hotelbooking.mapper.statistic.RegistrationEventMapper;
import com.rest.hotelbooking.model.dto.statistic.RegistrationEventDto;
import com.rest.hotelbooking.service.statistic.receiver.RegistrationEventReceiverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Kafka listener for {@link RegistrationEventDto}.
 */
@Component
@ConditionalOnProperty(
        prefix = "app.kafka.listener",
        name = "registration-event",
        havingValue = "true")
@RequiredArgsConstructor
@Slf4j
public class KafkaRegistrationEventListener {
    /**
     * Service for working with registration events.
     */
    private final RegistrationEventReceiverService registrationEventService;
    /**
     * Mapper for working with registration events.
     */
    private final RegistrationEventMapper registrationEventMapper;

    /**
     * Kafka listener for {@link RegistrationEventDto}.<br>
     * Map entity to dto with {@link RegistrationEventMapper}.<br>
     * Save mapped entity with {@link RegistrationEventReceiverService}.
     *
     * @param message   {@link RegistrationEventDto}.
     * @param key       UUID.
     * @param partition Integer.
     * @param timestamp Long.
     * @param topic     String.
     */
    @KafkaListener(topics = "${app.kafka.kafkaMessageTopicRegistration}",
            groupId = "${app.kafka.kafkaMessageGroupId}",
            containerFactory = "kafkaListenerContainerFactory")
    void orderListener(@Payload RegistrationEventDto message,
                       @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) UUID key,
                       @Header(value = KafkaHeaders.RECEIVED_PARTITION, required = false) Integer partition,
                       @Header(value = KafkaHeaders.RECEIVED_TIMESTAMP, required = false) Long timestamp,
                       @Header(value = KafkaHeaders.RECEIVED_TOPIC, required = false) String topic) {
        log.info("Topic: kafkaMessageTopicRegistration");
        log.info("Received message: {}", message);
        log.info("Key: {}, Partition: {}, Timestamp: {}, Topic: {}",
                key, partition, timestamp, topic);
        registrationEventService.save(registrationEventMapper.dtoToModel(message));
    }
}
