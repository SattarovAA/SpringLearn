package com.rest.hotelbooking.repository;

import com.rest.hotelbooking.model.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * JpaRepository for working with {@link Hotel} entity.
 * Uses JpaSpecificationExecutor.
 */
@Repository
public interface HotelRepository
        extends JpaRepository<Hotel, Long>, JpaSpecificationExecutor<Hotel> {
}
