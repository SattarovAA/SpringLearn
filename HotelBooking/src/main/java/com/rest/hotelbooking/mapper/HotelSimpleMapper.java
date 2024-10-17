package com.rest.hotelbooking.mapper;

import com.rest.hotelbooking.model.Hotel;
import com.rest.hotelbooking.web.dto.hotel.HotelListResponse;
import com.rest.hotelbooking.web.dto.hotel.HotelRequest;
import com.rest.hotelbooking.web.dto.hotel.HotelResponse;
import com.rest.hotelbooking.web.dto.hotel.HotelResponseWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = RoomSimpleMapper.class)
public interface HotelSimpleMapper extends SimpleRequestResponseMapper<Hotel, HotelRequest, HotelResponse> {
    HotelResponseWith modelToResponseWith(Hotel modelId);

    @Override
    List<HotelResponse> modelListToResponseList(List<Hotel> model);

    default HotelListResponse modelListToModelListResponse(List<Hotel> model) {
        return new HotelListResponse(
                modelListToResponseList(model)
        );
    }
}
