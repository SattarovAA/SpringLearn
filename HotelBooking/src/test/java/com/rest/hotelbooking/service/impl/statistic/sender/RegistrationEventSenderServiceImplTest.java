package com.rest.hotelbooking.service.impl.statistic.sender;

import com.rest.hotelbooking.mapper.statistic.RegistrationEventMapperImpl;
import com.rest.hotelbooking.model.dto.statistic.RegistrationEventDto;
import com.rest.hotelbooking.model.entity.User;
import com.rest.hotelbooking.model.security.RoleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("RegistrationEventSenderServiceImplTest Tests")
public class RegistrationEventSenderServiceImplTest {
    private RegistrationEventSenderServiceImpl senderService;
    @Mock
    private KafkaTemplate<String, RegistrationEventDto> kafkaTemplate;
    @Mock
    private RegistrationEventMapperImpl registrationEventMapper;
    @Value("${app.kafka.kafkaMessageTopicRegistration}")
    private String topicName;

    @BeforeEach
    void setUp() {
        senderService = new RegistrationEventSenderServiceImpl(
                kafkaTemplate,
                registrationEventMapper
        );
    }

    @Test
    @DisplayName("send test: send registration with kafkaTemplate.")
    void givenReservationWhenSendThenKafkaTemplateSend() {
        User reservationToSend = new User(
                1L,
                "user",
                "pass",
                "email",
                Collections.singleton(RoleType.ROLE_USER),
                Collections.emptyList()
        );
        RegistrationEventDto eventToSend = new RegistrationEventDto(1L);

        when(registrationEventMapper.userToEventDto(reservationToSend))
                .thenReturn(eventToSend);

        senderService.send(reservationToSend);

        verify(kafkaTemplate, times(1))
                .send(topicName, eventToSend);
        verify(registrationEventMapper, times(1))
                .userToEventDto(any());
    }
}
