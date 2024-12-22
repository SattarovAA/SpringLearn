package com.rest.hotelbooking.service.impl;

import com.rest.hotelbooking.exception.DeleteEntityWithReferenceException;
import com.rest.hotelbooking.exception.EntityNotFoundException;
import com.rest.hotelbooking.model.entity.Reservation;
import com.rest.hotelbooking.model.entity.Room;
import com.rest.hotelbooking.repository.RoomRepository;
import com.rest.hotelbooking.repository.specification.RoomSpecifications;
import com.rest.hotelbooking.service.HotelService;
import com.rest.hotelbooking.service.RoomService;
import com.rest.hotelbooking.model.dto.room.RoomFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

/**
 * Service for working with entity {@link Room}.
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class RoomServiceImpl implements RoomService {
    /**
     * {@link Room} repository.
     */
    private final RoomRepository roomRepository;
    /**
     * Service for working with entity Hotel.
     * Needed to define the field hotel in {@link Room}.
     *
     * @see #enrich(Room)
     * @see #enrich(Room, Long)
     */
    private final HotelService hotelService;
    /**
     * Default page size.
     *
     * @see #findAll()
     */
    @Value("${app.service.room.defaultPageSize}")
    private int defaultPageSize;
    /**
     * Default page number.
     *
     * @see #findAll()
     */
    @Value("${app.service.room.defaultPageNumber}")
    private int defaultPageNumber;

    /**
     * {@link PageRequest} of type {@link Room} with
     * {@link #defaultPageNumber} and {@link #defaultPageSize} parameters.
     *
     * @return list of type {@link Room}.
     */
    @Override
    public List<Room> findAll() {
        log.info("Try to get all rooms without filter.");
        return roomRepository.findAll(
                PageRequest.of(defaultPageNumber, defaultPageSize)
        ).getContent();
    }

    /**
     * Find all objects of type {@link Room} with filter parameters.
     *
     * @param filter {@link RoomFilter} with the required parameters.
     * @return all objects of the type {@link Room} by filter.
     */
    @Override
    public List<Room> filterBy(RoomFilter filter) {
        log.info("Try to get all rooms with filter.");
        return roomRepository.findAll(RoomSpecifications.withFilter(filter),
                PageRequest.of(filter.pageNumber(), filter.pageSize())
        ).getContent();
    }

    /**
     * Get a {@link Room} object by specifying its id.
     *
     * @param id id searched {@link Room}.
     * @return object of type {@link Room} with searched id.
     */
    @Override
    public Room findById(Long id) {
        log.info("Find room with id {}.", id);
        return roomRepository.findById(id).orElseThrow(
                EntityNotFoundException.create(
                        MessageFormat.format(
                                "Room with id {0} not found!",
                                id
                        )
                )
        );
    }
    /**
     * Save object model of type {@link Room}.
     *
     * @param model object of type {@link Room} to save.
     * @return object of type {@link Room} that was saved.
     */
    @Override
    public Room save(Room model) {
        log.info("Try to create new room.");
        model = enrich(model);
        return roomRepository.save(model);
    }
    /**
     * Update object model of type {@link Room} with T.id equals id.
     *
     * @param id    id of the object to be updated.
     * @param model object of type {@link Room} to update.
     * @return object of type {@link Room} that was updated.
     */
    @Override
    public Room update(Long id, Room model) {
        log.warn("Try to update room with id {}", id);
        model = enrich(model, id);
        return roomRepository.save(model);
    }

    /**
     * Enrich model to full version.
     *
     * @param model {@link Room} to enrich.
     * @return {@link Room} with all fields.
     */
    private Room enrich(Room model) {
        return Room.builder()
                .id(model.getId())
                .capacity(model.getCapacity())
                .cost(model.getCost())
                .description(model.getDescription())
                .name(model.getName())
                .number(model.getNumber())
                .hotel(hotelService.findById(model.getHotel().getId()))
                .reservations(List.of())
                .build();
    }

    /**
     * Enrich model to full version.
     * If the model has no field values, then the values are taken
     * from a previously existing entity with the same id.
     *
     * @param model   {@link Room} with partially updated fields.
     * @param modelId room id to update {@link Room}.
     * @return Updated {@link Room}.
     */
    private Room enrich(Room model, Long modelId) {
        Room modelToUpdate = findById(modelId);
        return Room.builder()
                .id(modelId)
                .capacity(model.getCapacity() == null
                        ? modelToUpdate.getCapacity()
                        : model.getCapacity()
                )
                .cost(model.getCost() == null
                        ? modelToUpdate.getCost()
                        : model.getCost()
                )
                .description(model.getDescription() == null
                        ? modelToUpdate.getDescription()
                        : model.getDescription()
                )
                .name(model.getName() == null
                        ? modelToUpdate.getName()
                        : model.getName()
                )
                .number(model.getNumber() == null
                        ? modelToUpdate.getNumber()
                        : model.getNumber()
                )
                .reservations(modelToUpdate.getReservations())
                .hotel(modelToUpdate.getHotel())
                .build();
    }
    /**
     * Delete object with Room.id equals id from database.
     *
     * @param id id of the object to be deleted.
     */
    @Override
    public void deleteById(Long id) {
        log.warn("Try to delete room with id {}.", id);
        Room roomToDelete = findById(id);
        checkReservationReference(roomToDelete);
        roomRepository.deleteById(id);
    }

    /**
     * Check reservation reference.
     *
     * @param model {@link Room} to check.
     * @throws DeleteEntityWithReferenceException if {@link Room#getReservations()} not empty.
     */
    private void checkReservationReference(Room model) {
        if (!model.getReservations().isEmpty()) {
            throw new DeleteEntityWithReferenceException(
                    MessageFormat.format(
                            "Unable to delete room with id {0}. Room have {1} reservations.",
                            model.getId(),
                            model.getReservations().size()
                    )
            );
        }
    }
}
