package com.rest.hotelbooking.service.impl.statistic.sender;

import com.rest.hotelbooking.mapper.statistic.ReservationEventMapperImpl;
import com.rest.hotelbooking.model.dto.statistic.ReservationEventDto;
import com.rest.hotelbooking.model.entity.Reservation;
import com.rest.hotelbooking.model.entity.Room;
import com.rest.hotelbooking.model.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ReservationEventSenderServiceImplTest Tests")
public class ReservationEventSenderServiceImplTest {
    private ReservationEventSenderServiceImpl senderService;
    @Mock
    private KafkaTemplate<String, ReservationEventDto> kafkaTemplate;
    @Mock
    private ReservationEventMapperImpl reservationEventMapper;
    @Value("${app.kafka.kafkaMessageTopicReservation}")
    private String topicName;

    @BeforeEach
    void setUp() {
        senderService = new ReservationEventSenderServiceImpl(
                kafkaTemplate,
                reservationEventMapper
        );
    }

    @Test
    @DisplayName("send test: send reservation with kafkaTemplate.")
    void givenReservationWhenSendThenKafkaTemplateSend() {
        Reservation reservationToSend = new Reservation(
                1L,
                LocalDate.of(2000, 1, 1),
                LocalDate.of(2000, 1, 10),
                new Room(),
                new User()
        );
        ReservationEventDto eventToSend = new ReservationEventDto(
                1L,
                LocalDate.of(2000, 1, 1),
                LocalDate.of(2000, 1, 10)
        );

        when(reservationEventMapper.reservationToEventDto(reservationToSend))
                .thenReturn(eventToSend);

        senderService.send(reservationToSend);

        verify(kafkaTemplate, times(1))
                .send(topicName, eventToSend);
        verify(reservationEventMapper, times(1))
                .reservationToEventDto(any());
    }
}
