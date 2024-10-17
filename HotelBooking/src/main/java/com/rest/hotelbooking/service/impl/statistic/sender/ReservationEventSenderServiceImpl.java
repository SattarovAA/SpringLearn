package com.rest.hotelbooking.service.impl.statistic.sender;

import com.rest.hotelbooking.model.Reservation;
import com.rest.hotelbooking.service.statistic.sender.ReservationEventSenderService;
import com.rest.hotelbooking.web.dto.statistic.ReservationEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReservationEventSenderServiceImpl implements ReservationEventSenderService {
    @Value("${app.kafka.kafkaMessageTopicReservation}")
    private String topicName;
    private final KafkaTemplate<String, ReservationEventDto> reservationEventKafkaTemplate;

    @Override
    public void send(Reservation model) {
        ReservationEventDto reservationEvent = ReservationEventDto.builder()
                .userId(model.getUser().getId())
                .checkIn(model.getCheckIn())
                .checkOut(model.getCheckOut())
                .build();
        reservationEventKafkaTemplate.send(topicName, reservationEvent);
    }
}
