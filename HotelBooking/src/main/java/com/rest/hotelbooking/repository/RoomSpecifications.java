package com.rest.hotelbooking.repository;

import com.rest.hotelbooking.model.Hotel;
import com.rest.hotelbooking.model.Reservation;
import com.rest.hotelbooking.model.Room;
import com.rest.hotelbooking.web.dto.RoomFilter;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;


public interface RoomSpecifications {
    static Specification<Room> withFilter(RoomFilter filter) {
        return Specification
                .where(byLongField(Room.Fields.id, filter.id()))
                .and(byStringField(Room.Fields.name, filter.name()))
                .and(byStringField(Room.Fields.description, filter.description()))
                .and(byStringField(Room.Fields.number, filter.number()))
                .and(byLongField(Room.Fields.capacity, filter.minCapacity(), filter.maxCapacity()))
                .and(byLongField(Room.Fields.cost, filter.minCost(), filter.maxCost()))
                .and(byHotelId(filter.hotelId()))
                .and(byReservationDate(filter.checkIn(), filter.checkOut()))
                ;
    }

    static Specification<Room> byReservationDate(LocalDate startDate, LocalDate endDate) {
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
                    .where(
                            criteriaBuilder.and(
                                    currentRoom,
                                    criteriaBuilder.or(
                                            aroundCheckIn,
                                            aroundCheckOut
                                    )
                            )
                    );

            return criteriaBuilder.not(criteriaBuilder.exists(subquery));
        };
    }

    static Specification<Room> byHotelId(Long hotelId) {
        return ((root, query, criteriaBuilder) -> {
            if (hotelId == null) {
                return null;
            }
            return criteriaBuilder.equal(
                    root.get(Room.Fields.hotel).get(Hotel.Fields.id),
                    hotelId);
        });
    }

    static Specification<Room> byStringField(String field, String criterion) {
        return ((root, query, criteriaBuilder) -> {
            if (criterion == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get(field), criterion);
        });
    }

    static Specification<Room> byLongField(String field, Long criterion) {
        return ((root, query, criteriaBuilder) -> {
            if (criterion == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get(field), criterion);
        });
    }

    static Specification<Room> byLongField(String field, Long min, Long max) {
        return ((root, query, criteriaBuilder) -> {
            if (max == null && min == null) {
                return null;
            }
            if (min == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get(field), max);
            }
            if (max == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get(field), min);
            }
            return criteriaBuilder.between(root.get(field), min, max);
        });
    }
}
