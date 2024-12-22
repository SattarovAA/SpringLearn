package com.rest.hotelbooking.model.entity.statistic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

/**
 * Save new Reservation information.
 * Entity for working with MongoDb.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "reservation_event")
public class ReservationEvent {
    /**
     * String @Id for MongoDb.
     */
    @Id
    private String id;
    /**
     * id registered user.
     */
    private Long userId;
    /**
     * Day from which the booking starts.
     */
    private LocalDate checkIn;
    /**
     * Day from which the booking ends.
     */
    private LocalDate checkOut;
}
