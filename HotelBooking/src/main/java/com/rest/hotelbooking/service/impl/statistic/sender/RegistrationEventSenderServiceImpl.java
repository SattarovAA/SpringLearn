package com.rest.hotelbooking.service.impl.statistic.sender;

import com.rest.hotelbooking.mapper.statistic.RegistrationEventMapper;
import com.rest.hotelbooking.model.dto.statistic.RegistrationEventDto;
import com.rest.hotelbooking.model.dto.statistic.ReservationEventDto;
import com.rest.hotelbooking.model.entity.Reservation;
import com.rest.hotelbooking.model.entity.User;
import com.rest.hotelbooking.service.statistic.sender.RegistrationEventSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Service for sending registration event.
 *
 * @see User
 * @see RegistrationEventDto
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class RegistrationEventSenderServiceImpl
        implements RegistrationEventSenderService {
    /**
     * Kafka topic to send updated registration events.
     */
    @Value("${app.kafka.kafkaMessageTopicRegistration}")
    private String topicName;
    /**
     * {@link KafkaTemplate} to send information
     * about creating a new {@link User}.
     */
    private final KafkaTemplate<String, RegistrationEventDto> kafkaUserTemplate;
    /**
     * mapper for mapping {@link Reservation} to {@link ReservationEventDto}.
     */
    private final RegistrationEventMapper registrationEventMapper;

    /**
     * Send information about creating a new {@link User}.
     *
     * @param model {@link User} for creation new event.
     */
    @Override
    public void send(User model) {
        log.info("Try to send registration event for user with id {}", model.getId());
        RegistrationEventDto registrationEvent =
                registrationEventMapper.userToEventDto(model);
        kafkaUserTemplate.send(topicName, registrationEvent);
    }
}
