package com.rest.hotelbooking.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Entity Reservation.
 * Entity for working with JpaRepository.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@FieldNameConstants
@Builder
@Entity
@Table(name = "reservations",
        indexes = {
                @Index(columnList = "check_in"),
                @Index(columnList = "check_out")
        })
public class Reservation {
    /**
     * Long Reservation @Id for JpaRepository.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Day from which the booking starts.
     */
    @Column(name = "check_in")
    private LocalDate checkIn;
    /**
     * Day from which the booking ends.
     */
    @Column(name = "check_out")
    private LocalDate checkOut;
    /**
     * The room that was booked.
     */
    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;
    /**
     * The User who owns the booking.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id)
                && Objects.equals(checkIn, that.checkIn)
                && Objects.equals(checkOut, that.checkOut)
                && Objects.equals(room.getId(), that.room.getId())
                && Objects.equals(user.getId(), that.user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, checkIn, checkOut, room.getId(), user.getId());
    }
}
