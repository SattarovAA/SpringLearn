package com.rest.hotelbooking.model.entity.statistic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Save Registration User information.
 * Entity for working with MongoDb.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "registration_event")
public class RegistrationEvent {
    /**
     * String @Id for MongoDb.
     */
    @Id
    private String id;
    /**
     * id registered user.
     */
    private Long userId;
}
