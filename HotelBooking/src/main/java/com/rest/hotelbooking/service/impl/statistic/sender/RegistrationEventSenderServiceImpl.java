package com.rest.hotelbooking.service.impl.statistic.sender;

import com.rest.hotelbooking.model.User;
import com.rest.hotelbooking.service.statistic.sender.RegistrationEventSenderService;
import com.rest.hotelbooking.web.dto.statistic.RegistrationEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegistrationEventSenderServiceImpl implements RegistrationEventSenderService {
    @Value("${app.kafka.kafkaMessageTopicRegistration}")
    private String topicName;
    private final KafkaTemplate<String, RegistrationEventDto> kafkaUserTemplate;
    @Override
    public void send(User model) {
        RegistrationEventDto registrationEvent =
                new RegistrationEventDto(model.getId());
        kafkaUserTemplate.send(topicName, registrationEvent);
    }
}
