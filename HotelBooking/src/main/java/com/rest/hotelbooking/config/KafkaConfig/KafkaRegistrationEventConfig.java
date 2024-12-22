package com.rest.hotelbooking.config.KafkaConfig;

import com.rest.hotelbooking.model.dto.statistic.RegistrationEventDto;
import org.springframework.context.annotation.Configuration;

/**
 * Kafka configuration for RegistrationEventDto.
 *
 * @see KafkaSimpleConfig
 * @see RegistrationEventDto
 */
@Configuration
public class KafkaRegistrationEventConfig
        extends KafkaSimpleConfig<RegistrationEventDto> {
}
