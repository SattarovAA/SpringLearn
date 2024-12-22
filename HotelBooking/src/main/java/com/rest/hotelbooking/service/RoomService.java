package com.rest.hotelbooking.service;

import com.rest.hotelbooking.model.entity.Room;
import com.rest.hotelbooking.model.dto.room.RoomFilter;

import java.util.List;

/**
 * Default interface service for working with entity {@link Room}.
 */
public interface RoomService extends CrudService<Room> {
    /**
     * Find all objects of type {@link Room} with filter parameters.
     *
     * @param filter {@link RoomFilter} with the required parameters.
     * @return all objects of the type {@link Room} by filter.
     */
    List<Room> filterBy(RoomFilter filter);
}
