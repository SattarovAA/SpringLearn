package com.rest.hotelbooking.listener.statistic;

import com.rest.hotelbooking.mapper.statistic.RegistrationEventMapper;
import com.rest.hotelbooking.service.impl.statistic.receiver.RegistrationEventReceiverServiceImpl;
import com.rest.hotelbooking.web.dto.statistic.RegistrationEventDto;
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
public class KafkaRegistrationEventListener {
    private final RegistrationEventReceiverServiceImpl registrationEventService;
    private final RegistrationEventMapper registrationEventMapper;

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
        log.info("Key: {}, Partition: {}, Timestamp: {}, Topic: {}", key, partition, timestamp, topic);
        registrationEventService.save(registrationEventMapper.dtoToModel(message));
    }
}
