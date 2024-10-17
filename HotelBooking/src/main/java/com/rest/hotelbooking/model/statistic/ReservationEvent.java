package com.rest.hotelbooking.model.statistic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "reservation_event")
public class ReservationEvent {
    @Id
    private String id;
    private Long userId;
    private LocalDate checkIn;
    private LocalDate checkOut;
}
