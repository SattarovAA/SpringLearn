package com.rest.hotelbooking.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Entity Room.
 * Entity for working with JpaRepository.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@FieldNameConstants
@Builder
@Entity
@Table(name = "rooms",
        indexes = {
                @Index(columnList = "hotel_id"),
                @Index(columnList = Room.Fields.cost)
        })
public class Room {
    /**
     * Long Room @Id for JpaRepository.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Room name.
     */
    @Column(name = "name")
    private String name;
    /**
     * Room description.
     */
    @Column(name = "description")
    private String description;
    /**
     * Room number in String format.
     */
    @Column(name = "number")
    private String number;
    /**
     * Room cost.
     */
    @Column(name = "cost")
    private Long cost;
    /**
     * Room capacity.
     */
    @Column(name = "capacity")
    private Long capacity;
    /**
     * Room reservations.
     *
     * @value new ArrayList<>()
     */
    @OneToMany(mappedBy = Reservation.Fields.room, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Reservation> reservations = new ArrayList<>();
    /**
     * Hotel where Room is located.
     */
    @ManyToOne
    @JoinColumn(name = "hotel_id", referencedColumnName = "id")
    private Hotel hotel;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Room room = (Room) o;
        return Objects.equals(id, room.id)
                && Objects.equals(name, room.name)
                && Objects.equals(description, room.description)
                && Objects.equals(number, room.number)
                && Objects.equals(cost, room.cost)
                && Objects.equals(capacity, room.capacity)
                && Objects.equals(reservations.size(), room.reservations.size())
                && Objects.equals(hotel.getId(), room.hotel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id, name, description, number,
                cost, capacity, reservations.size(), hotel.getId()
        );
    }
}
