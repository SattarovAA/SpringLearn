package com.rest.hotelbooking.listener.statistic;

import com.rest.hotelbooking.mapper.statistic.ReservationEventMapper;
import com.rest.hotelbooking.service.statistic.receiver.ReservationEventReceiverService;
import com.rest.hotelbooking.web.dto.statistic.ReservationEventDto;
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
public class KafkaReservationEventListener {
    private final ReservationEventReceiverService reservationEventReceiverService;
    private final ReservationEventMapper reservationEventMapper;

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
        log.info("Key: {}, Partition: {}, Timestamp: {}, Topic: {}", key, partition, timestamp, topic);
        reservationEventReceiverService.save(reservationEventMapper.dtoToModel(message));
    }
}
