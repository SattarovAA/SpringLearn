package com.rest.hotelbooking.repository;

import com.rest.hotelbooking.model.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * JpaRepository for working with {@link Room} entity.
 * Uses JpaSpecificationExecutor.
 */
public interface RoomRepository
        extends JpaRepository<Room, Long>, JpaSpecificationExecutor<Room> {
}
