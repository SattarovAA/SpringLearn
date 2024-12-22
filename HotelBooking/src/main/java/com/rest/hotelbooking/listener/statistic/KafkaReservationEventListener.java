package com.rest.hotelbooking.listener.statistic;

import com.rest.hotelbooking.mapper.statistic.ReservationEventMapper;
import com.rest.hotelbooking.service.statistic.receiver.ReservationEventReceiverService;
import com.rest.hotelbooking.model.dto.statistic.ReservationEventDto;
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
 * Kafka listener for {@link ReservationEventDto}.
 */
@Component
@ConditionalOnProperty(
        prefix = "app.kafka.listener",
        name = "reservation-event",
        havingValue = "true")
@RequiredArgsConstructor
@Slf4j
public class KafkaReservationEventListener {
    /**
     * Service for working with reservation events.
     */
    private final ReservationEventReceiverService reservationEventReceiverService;
    /**
     * Mapper for working with reservation events.
     */
    private final ReservationEventMapper reservationEventMapper;

    /**
     * Kafka listener for {@link ReservationEventDto}.<br>
     * Map entity to dto with {@link ReservationEventMapper}.<br>
     * Save mapped entity with {@link ReservationEventReceiverService}.
     *
     * @param message   {@link ReservationEventDto}.
     * @param key       UUID.
     * @param partition Integer.
     * @param timestamp Long.
     * @param topic     String.
     */
    @KafkaListener(topics = "${app.kafka.kafkaMessageTopicReservation}",
            groupId = "${app.kafka.kafkaMessageGroupId}",
            containerFactory = "kafkaListenerContainerFactory")
    void reservationsListener(@Payload ReservationEventDto message,
                              @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) UUID key,
                              @Header(value = KafkaHeaders.RECEIVED_PARTITION, required = false) Integer partition,
                              @Header(value = KafkaHeaders.RECEIVED_TIMESTAMP, required = false) Long timestamp,
                              @Header(value = KafkaHeaders.RECEIVED_TOPIC, required = false) String topic) {
        log.info("Topic: kafkaMessageTopicReservation");
        log.info("Received message: {}", message);
        log.info("Key: {}, Partition: {}, Timestamp: {}, Topic: {}",
                key, partition, timestamp, topic);
        reservationEventReceiverService.save(reservationEventMapper.dtoToModel(message));
    }
}
