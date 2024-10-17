package com.rest.hotelbooking.service;

import com.rest.hotelbooking.model.Room;
import com.rest.hotelbooking.web.dto.RoomFilter;

import java.util.List;

public interface RoomService extends CrudService<Room> {
    List<Room> filterBy(RoomFilter filter);
}
