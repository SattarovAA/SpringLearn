package com.rest.hotelbooking.config.KafkaConfig;

import com.rest.hotelbooking.web.dto.statistic.RegistrationEventDto;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaRegistrationEventConfig extends KafkaSimpleConfig<RegistrationEventDto> {
}
