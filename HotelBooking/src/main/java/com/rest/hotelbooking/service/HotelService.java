package com.rest.hotelbooking.service;

import com.rest.hotelbooking.model.Hotel;
import com.rest.hotelbooking.web.dto.hotel.mark.HotelMark;
import com.rest.hotelbooking.web.dto.HotelFilter;

import java.util.List;

public interface HotelService extends CrudService<Hotel> {
    List<Hotel> filterBy(HotelFilter filter);

    Hotel addNewMark(Long hotelId, HotelMark newMark);
}
