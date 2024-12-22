package com.rest.hotelbooking.service.impl.statistic.sender;

import com.rest.hotelbooking.mapper.statistic.ReservationEventMapper;
import com.rest.hotelbooking.model.entity.Reservation;
import com.rest.hotelbooking.service.statistic.sender.ReservationEventSenderService;
import com.rest.hotelbooking.model.dto.statistic.ReservationEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Service for sending reservation event.
 *
 * @see Reservation
 * @see ReservationEventDto
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class ReservationEventSenderServiceImpl
        implements ReservationEventSenderService {
    /**
     * Kafka topic to send updated reservation events.
     */
    @Value("${app.kafka.kafkaMessageTopicReservation}")
    private String topicName;
    /**
     * {@link KafkaTemplate} to send information
     * about creating a new {@link Reservation}.
     */
    private final KafkaTemplate<String, ReservationEventDto> kafkaTemplate;
    /**
     * mapper for mapping {@link Reservation} to {@link ReservationEventDto}.
     */
    private final ReservationEventMapper reservationEventMapper;

    /**
     * Send information about creating a new {@link Reservation}.
     *
     * @param model {@link Reservation} for creation new event.
     */
    @Override
    public void send(Reservation model) {
        log.info("Try to send reservation event for user with id {}", model.getId());
        ReservationEventDto reservationEvent =
                reservationEventMapper.reservationToEventDto(model);
        kafkaTemplate.send(topicName, reservationEvent);
    }
}
