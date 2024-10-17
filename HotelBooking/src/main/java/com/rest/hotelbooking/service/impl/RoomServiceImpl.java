package com.rest.hotelbooking.service.impl;

import com.rest.hotelbooking.exception.DeleteEntityWithReferenceException;
import com.rest.hotelbooking.exception.EntityNotFoundException;
import com.rest.hotelbooking.model.Room;
import com.rest.hotelbooking.repository.RoomRepository;
import com.rest.hotelbooking.repository.RoomSpecifications;
import com.rest.hotelbooking.service.HotelService;
import com.rest.hotelbooking.service.RoomService;
import com.rest.hotelbooking.web.dto.RoomFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final HotelService hotelService;

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public List<Room> filterBy(RoomFilter filter) {
        return roomRepository.findAll(RoomSpecifications.withFilter(filter),
                PageRequest.of(filter.pageNumber(), filter.pageSize())
        ).getContent();
    }

    @Override
    public Room findById(Long id) {
        return roomRepository.findById(id).orElseThrow(
                EntityNotFoundException.create(
                        MessageFormat.format("Комната с id {0} не найдена!", id)
                )
        );
    }

    @Override
    public Room save(Room model) {
        model = enrich(model);
        return roomRepository.save(model);
    }

    @Override
    public Room update(Long id, Room model) {
        model = enrich(model, id);
        return roomRepository.save(model);
    }

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

    @Override
    public void deleteById(Long id) {
        Room roomToDelete = findById(id);
        checkReservationReference(roomToDelete);
        roomRepository.deleteById(id);
    }

    private void checkReservationReference(Room model) {
        if (!model.getReservations().isEmpty()) {
            throw new DeleteEntityWithReferenceException(
                    MessageFormat.format(
                            "Unable to delete room with id {0}. Room have {1} reservations",
                            model.getId(), model.getReservations().size())
            );
        }
    }
}
