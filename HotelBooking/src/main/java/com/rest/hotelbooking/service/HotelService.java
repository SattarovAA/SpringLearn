package com.rest.hotelbooking.service;

import com.rest.hotelbooking.model.entity.Hotel;
import com.rest.hotelbooking.model.dto.hotel.mark.HotelMark;
import com.rest.hotelbooking.model.dto.hotel.HotelFilter;

import java.util.List;

/**
 * Default interface service for working with entity {@link Hotel}.
 */
public interface HotelService extends CrudService<Hotel> {
    /**
     * Find all Objects of type {@link Hotel} with filter parameters.
     *
     * @param filter {@link HotelFilter} with the required parameters.
     * @return all objects of type {@link Hotel} by filter.
     */
    List<Hotel> filterBy(HotelFilter filter);

    /**
     * Add new Mark to the hotel and update
     * hotel rating and hotel numberOfRating.
     *
     * @param hotelId hotel id to update rating.
     * @param newMark new mark.
     * @return {@link Hotel} with updated mark.
     */
    Hotel addNewMark(Long hotelId, HotelMark newMark);
}
