package com.rest.hotelbooking.repository.specification;

import com.rest.hotelbooking.model.entity.Hotel;
import com.rest.hotelbooking.model.entity.Reservation;
import com.rest.hotelbooking.model.entity.Room;
import com.rest.hotelbooking.model.dto.room.RoomFilter;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

/**
 * Specifications for working with entity {@link Room}.
 */
public interface RoomSpecifications {
    /**
     * Default specifications for entity {@link Room}.
     */
    SimpleSpec<Room> SIMPLE_SPEC = new SimpleSpec<>();

    /**
     * Specification with filter.
     *
     * @param filter {@link RoomFilter} with the required parameters
     * @return Specification with the required parameters
     * @see SimpleSpec
     */
    static Specification<Room> withFilter(RoomFilter filter) {
        return Specification
                .where(SIMPLE_SPEC.byLongField(Room.Fields.id,
                                filter.id()
                        )
                )
                .and(SIMPLE_SPEC.byStringField(Room.Fields.name,
                                filter.name()
                        )
                )
                .and(SIMPLE_SPEC.byStringField(Room.Fields.description,
                                filter.description()
                        )
                )
                .and(SIMPLE_SPEC.byStringField(Room.Fields.number,
                                filter.number()
                        )
                )
                .and(SIMPLE_SPEC.byNumberField(Room.Fields.capacity,
                                filter.minCapacity(),
                                filter.maxCapacity()
                        )
                )
                .and(SIMPLE_SPEC.byNumberField(Room.Fields.cost,
                                filter.minCost(),
                                filter.maxCost()
                        )
                )
                .and(byHotelId(filter.hotelId()))
                .and(byReservationDate(filter.checkIn(),
                                filter.checkOut()
                        )
                );
    }

    /**
     * Rooms with reservations with non-conflict dates.
     *
     * @param startDate Day from which the booking starts
     * @param endDate   Day from which the booking ends
     * @return Specification with the required parameters
     * @see Reservation
     */
    static Specification<Room> byReservationDate(
            LocalDate startDate,
            LocalDate endDate
    ) {
        return (root, query, criteriaBuilder) -> {
            if (startDate == null || endDate == null) {
                return null;
            }
            Subquery<Reservation> subquery = query.subquery(Reservation.class);
            Root<Reservation> reservationRoot = subquery.from(Reservation.class);

            Predicate aroundCheckIn = criteriaBuilder.between(
                    reservationRoot.get(Reservation.Fields.checkIn),
                    startDate, endDate
            );
            Predicate aroundCheckOut = criteriaBuilder.between(
                    reservationRoot.get(Reservation.Fields.checkOut),
                    startDate, endDate
            );
            Predicate currentRoom = criteriaBuilder.equal(
                    reservationRoot.get(Reservation.Fields.room),
                    root
            );

            subquery.select(reservationRoot)
                    .where(criteriaBuilder.and(
                                    currentRoom,
                                    criteriaBuilder.or(
                                            aroundCheckIn,
                                            aroundCheckOut
                                    )
                            )
                    );
            Predicate withDateConflict = criteriaBuilder.exists(subquery);
            return criteriaBuilder.not(withDateConflict);
        };
    }

    /**
     * Reservations where Room.hotel.id field equals hotelId.
     *
     * @param hotelId id Room hotel
     * @return Specification with the required parameters
     * @see Hotel
     */
    static Specification<Room> byHotelId(Long hotelId) {
        return ((root, query, criteriaBuilder) -> {
            if (hotelId == null) {
                return null;
            }
            return criteriaBuilder.equal(
                    root.get(Room.Fields.hotel).get(Hotel.Fields.id),
                    hotelId
            );
        });
    }
}
