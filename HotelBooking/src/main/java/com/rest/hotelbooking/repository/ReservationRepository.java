package com.rest.hotelbooking.repository;

import com.rest.hotelbooking.model.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

/**
 * JpaRepository for working with {@link Reservation} entity.
 */
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    /**
     * Search {@link Reservation} with conflicting date
     * in range startDate and endDate
     * and room with id roomId.
     *
     * @param startDate date which the booking starts.
     * @param endDate   date which the booking ends.
     * @param roomId    room id that was booked.
     * @return true if exists conflicting reservations.
     */
    @Query("SELECT case " +
            "WHEN count(r)> 0 " +
            "THEN true " +
            "ELSE false " +
            "END " +
            "FROM Reservation r " +
            "WHERE (r.checkIn " +
            "BETWEEN :startDate AND :endDate " +
            "OR r.checkOut " +
            "BETWEEN :startDate AND :endDate) " +
            "AND r.room.id = :roomId")
    boolean existsConflictingReservations(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("roomId") Long roomId
    );

    /**
     * Search {@link Reservation} with conflicting date
     * in range startDate and endDate
     * and room with id roomId
     * exclude {@link Reservation} where room id equals currentId.
     *
     * @param startDate date which the booking starts.
     * @param endDate   date which the booking ends.
     * @param roomId    room id that was booked.
     * @param currentId room id that was booked before changes.
     * @return true if exists conflicting reservations.
     */
    @Query("SELECT case " +
            "WHEN count(r)> 0 " +
            "THEN true " +
            "ELSE false " +
            "END " +
            "FROM Reservation r " +
            "WHERE (r.checkIn " +
            "BETWEEN :startDate AND :endDate " +
            "OR r.checkOut " +
            "BETWEEN :startDate AND :endDate) " +
            "AND r.room.id = :roomId " +
            "AND r.id <> :currentId")
    boolean existsConflictingReservations(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("roomId") Long roomId,
            @Param("currentId") Long currentId
    );
}
