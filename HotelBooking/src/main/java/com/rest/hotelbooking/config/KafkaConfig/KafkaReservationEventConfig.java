package com.rest.hotelbooking.config.KafkaConfig;

import com.rest.hotelbooking.model.dto.statistic.ReservationEventDto;
import org.springframework.context.annotation.Configuration;

/**
 * Kafka configuration for ReservationEventDto.
 *
 * @see KafkaSimpleConfig
 * @see ReservationEventDto
 */
@Configuration
public class KafkaReservationEventConfig
        extends KafkaSimpleConfig<ReservationEventDto> {
}
