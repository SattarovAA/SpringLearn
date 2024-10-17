package com.rest.hotelbooking.repository;

import com.rest.hotelbooking.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Boolean existsByCheckInBetweenOrCheckOutBetweenAndRoom_Id(
            LocalDate checkIn, LocalDate checkIn2,
            LocalDate checkOut, LocalDate checkOut2,
            Long room_id);

    Optional<Reservation> findByCheckInBetweenOrCheckOutBetweenAndRoom_Id(
            LocalDate checkIn, LocalDate checkIn2,
            LocalDate checkOut, LocalDate checkOut2,
            Long room_id);

    Boolean existsByCheckInBetweenOrCheckOutBetweenAndRoom_IdAndIdNot(
            LocalDate checkIn, LocalDate checkIn2,
            LocalDate checkOut, LocalDate checkOut2,
            Long room_id, Long id);

    Optional<Reservation> findByCheckInBetweenOrCheckOutBetweenAndRoom_IdAndIdNot(
            LocalDate checkIn, LocalDate checkIn2,
            LocalDate checkOut, LocalDate checkOut2,
            Long room_id, Long id);
}
