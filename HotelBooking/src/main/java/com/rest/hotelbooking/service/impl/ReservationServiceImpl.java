package com.rest.hotelbooking.service.impl;

import com.rest.hotelbooking.exception.AlreadyExitsException;
import com.rest.hotelbooking.exception.EntityNotFoundException;
import com.rest.hotelbooking.model.entity.Reservation;
import com.rest.hotelbooking.model.security.AppUserDetails;
import com.rest.hotelbooking.repository.ReservationRepository;
import com.rest.hotelbooking.service.ReservationService;
import com.rest.hotelbooking.service.RoomService;
import com.rest.hotelbooking.service.UserService;
import com.rest.hotelbooking.service.statistic.sender.ReservationEventSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

/**
 * Service for working with entity {@link Reservation}.
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class ReservationServiceImpl implements ReservationService {
    /**
     * {@link Reservation} repository.
     */
    private final ReservationRepository reservationRepository;
    /**
     * Service for working with entity User.
     * Needed to define the field user in {@link Reservation}.
     *
     * @see #enrich(Reservation)
     * @see #enrich(Reservation, Long)
     */
    private final UserService userService;
    /**
     * Service for working with entity Room.
     * Needed to define the field room in {@link Reservation}.
     *
     * @see #enrich(Reservation)
     * @see #enrich(Reservation, Long)
     */
    private final RoomService roomService;

    /**
     * Service for sending information
     * about creating a new {@link Reservation}.
     */
    private final ReservationEventSenderService reservationEventSenderService;
    /**
     * Default page size.
     *
     * @see #findAll()
     */
    @Value("${app.service.reservation.defaultPageSize}")
    private int defaultPageSize;
    /**
     * Default page number.
     *
     * @see #findAll()
     */
    @Value("${app.service.reservation.defaultPageNumber}")
    private int defaultPageNumber;


    /**
     * {@link PageRequest} of type {@link Reservation} with
     * {@link #defaultPageNumber} and {@link #defaultPageSize} parameters.
     *
     * @return list of type {@link Reservation}.
     */
    @Override
    public List<Reservation> findAll() {
        log.info("Try to get all reservations.");
        return reservationRepository.findAll(
                PageRequest.of(defaultPageNumber, defaultPageSize)
        ).getContent();
    }

    /**
     * Get a {@link Reservation} object by specifying its id.
     *
     * @param id id searched {@link Reservation}.
     * @return object of type {@link Reservation} with searched id.
     */
    @Override
    public Reservation findById(Long id) {
        log.info("Find reservation with id {}.", id);
        return reservationRepository.findById(id).orElseThrow(
                EntityNotFoundException.create(
                        MessageFormat.format(
                                "Reservation with id {0} not found!",
                                id
                        )
                )
        );
    }
    /**
     * Save object model of type {@link Reservation}.
     *
     * @param model object of type {@link Reservation} to save.
     * @return object of type {@link Reservation} that was saved.
     */
    @Override
    public Reservation save(Reservation model) {
        log.warn("Try to create new reservation.");
        model = enrich(model);
        checkReservationDateCollision(model);
        Reservation savedReservation = reservationRepository.save(model);
        reservationEventSenderService.send(savedReservation);
        return savedReservation;
    }

    /**
     * Update object model of type {@link Reservation} with T.id equals id.
     *
     * @param id    id of the object to be updated.
     * @param model object of type {@link Reservation} to update.
     * @return object of type {@link Reservation} that was updated.
     */
    @Override
    public Reservation update(Long id, Reservation model) {
        log.warn("Try to update reservation with id {}.", id);
        model = enrich(model, id);
        checkReservationDateCollision(model, id);
        Reservation savedReservation = reservationRepository.save(model);
        reservationEventSenderService.send(savedReservation);
        return savedReservation;
    }

    /**
     * Enrich model to full version.
     * Field user get from {@link AppUserDetails}.
     *
     * @param model {@link Reservation} without user field.
     * @return {@link Reservation} with all fields.
     */
    private Reservation enrich(Reservation model) {
        AppUserDetails userDetails =
                (AppUserDetails) SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal();

        return Reservation.builder()
                .id(model.getId())
                .checkIn(model.getCheckIn())
                .checkOut(model.getCheckOut())
                .room(roomService.findById(model.getRoom().getId()))
                .user(userService.findById(userDetails.getUserId()))
                .build();
    }

    /**
     * Enrich model to full version.
     * If the model has no field values, then the values are taken
     * from a previously existing entity with the same id.
     *
     * @param model   {@link Reservation} with partially updated fields.
     * @param modelId reservation id to update {@link Reservation}.
     * @return Updated {@link Reservation}.
     */
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

    /**
     * Check reservation date collision.
     * For save new {@link Reservation}.
     *
     * @param model {@link Reservation} to check.
     * @throws AlreadyExitsException if {@link Reservation} in
     *                               this range already exist.
     */
    private void checkReservationDateCollision(Reservation model) {
        boolean collision =
                reservationRepository.existsConflictingReservations(
                        model.getCheckIn(),
                        model.getCheckOut(),
                        model.getRoom().getId()
                );
        if (collision) {
            throw new AlreadyExitsException(
                    MessageFormat.format(
                            "Reservation in this range ({0} to {1}) already exist!",
                            model.getCheckIn(),
                            model.getCheckOut()
                    )
            );
        }
    }

    /**
     * Check reservation date collision.
     * For update {@link Reservation}.
     *
     * @param model                {@link Reservation} to check.
     * @param currentReservationId current reservation id.
     * @throws AlreadyExitsException if {@link Reservation} in
     *                               this range already exist.
     */
    private void checkReservationDateCollision(Reservation model,
                                               Long currentReservationId) {
        boolean collision =
                reservationRepository.existsConflictingReservations(
                        model.getCheckIn(),
                        model.getCheckOut(),
                        model.getRoom().getId(),
                        currentReservationId
                );
        if (collision) {
            throw new AlreadyExitsException(
                    MessageFormat.format(
                            "Reservation in this range ({0} to {1}) already exist!",
                            model.getCheckIn(),
                            model.getCheckOut()
                    )
            );
        }
    }
    /**
     * Delete object with Reservation.id equals id from database.
     *
     * @param id id of the object to be deleted.
     */
    @Override
    public void deleteById(Long id) {
        log.warn("Try to delete reservation with id {}.", id);
        findById(id);
        reservationRepository.deleteById(id);
    }
}
