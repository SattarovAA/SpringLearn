package com.rest.hotelbooking.service.impl;

import com.rest.hotelbooking.exception.AlreadyExitsException;
import com.rest.hotelbooking.exception.EntityNotFoundException;
import com.rest.hotelbooking.model.Reservation;
import com.rest.hotelbooking.model.security.AppUserDetails;
import com.rest.hotelbooking.repository.ReservationRepository;
import com.rest.hotelbooking.service.ReservationService;
import com.rest.hotelbooking.service.RoomService;
import com.rest.hotelbooking.service.UserService;
import com.rest.hotelbooking.service.statistic.sender.ReservationEventSenderService;
import com.rest.hotelbooking.web.dto.statistic.ReservationEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserService userService;
    private final RoomService roomService;
    private final ReservationEventSenderService reservationEventSenderService;

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }


    @Override
    public Reservation findById(Long id) {
        return reservationRepository.findById(id).orElseThrow(
                EntityNotFoundException.create(
                        MessageFormat.format("Бронирование с id {0} не найдена!", id)
                )
        );
    }

    @Override
    public Reservation save(Reservation model) {
        model = enrich(model);
        checkReservationDateCollision(model);
        Reservation savedReservation = reservationRepository.save(model);
        reservationEventSenderService.send(savedReservation);
        return savedReservation;
    }


    @Override
    public Reservation update(Long id, Reservation model) {
        model = enrich(model, id);
        checkReservationDateCollision(model, id);
        Reservation savedReservation = reservationRepository.save(model);
        reservationEventSenderService.send(savedReservation);
        return savedReservation;
    }

    private Reservation enrich(Reservation model) {
        AppUserDetails userDetails = (AppUserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        return Reservation.builder()
                .id(model.getId())
                .checkIn(model.getCheckIn())
                .checkOut(model.getCheckOut())
                .room(roomService.findById(model.getRoom().getId()))
                .user(userService.findById(userDetails.getUserId()))
                .build();
    }

    private Reservation enrich(Reservation model, Long modelId) {
        Reservation modelToUpdate = findById(modelId);
        return Reservation.builder()
                .id(modelId)
                .checkIn(model.getCheckIn() == null
                        ? modelToUpdate.getCheckIn()
                        : model.getCheckIn()
                )
                .checkOut(model.getCheckOut() == null
                        ? modelToUpdate.getCheckOut()
                        : model.getCheckOut()
                )
                .room(model.getRoom().getId() == null
                        ? roomService.findById(modelToUpdate.getRoom().getId())
                        : roomService.findById(model.getRoom().getId())
                )
                .user(userService.findById(modelToUpdate.getUser().getId()))
                .build();
    }

    private void checkReservationDateCollision(Reservation model) {
        Optional<Reservation> reservationWithDateCollision = reservationRepository
                .findByCheckInBetweenOrCheckOutBetweenAndRoom_Id(
                        model.getCheckIn(), model.getCheckOut(),
                        model.getCheckIn(), model.getCheckOut(),
                        model.getRoom().getId()
                );

        if (reservationWithDateCollision.isPresent()) {
            throw new AlreadyExitsException("Reservation in this range already exist!");
        }
    }

    private void checkReservationDateCollision(Reservation model, Long currentReservationId) {
        Optional<Reservation> reservationWithDateCollision = reservationRepository
                .findByCheckInBetweenOrCheckOutBetweenAndRoom_IdAndIdNot(
                        model.getCheckIn(), model.getCheckOut(),
                        model.getCheckIn(), model.getCheckOut(),
                        model.getRoom().getId(), currentReservationId
                );

        if (reservationWithDateCollision.isPresent()) {
            throw new AlreadyExitsException("Reservation in this range already exist!");
        }
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        reservationRepository.deleteById(id);
    }
}
