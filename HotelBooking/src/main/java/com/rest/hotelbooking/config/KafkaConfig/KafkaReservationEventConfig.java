package com.rest.hotelbooking.config.KafkaConfig;

import com.rest.hotelbooking.web.dto.statistic.ReservationEventDto;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaReservationEventConfig extends KafkaSimpleConfig<ReservationEventDto> {
}
