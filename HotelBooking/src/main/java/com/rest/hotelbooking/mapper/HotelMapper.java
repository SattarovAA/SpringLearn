package com.rest.hotelbooking.mapper;

import com.rest.hotelbooking.model.entity.Hotel;
import com.rest.hotelbooking.model.dto.hotel.HotelListResponse;
import com.rest.hotelbooking.model.dto.hotel.HotelRequest;
import com.rest.hotelbooking.model.dto.hotel.HotelResponse;
import com.rest.hotelbooking.model.dto.hotel.HotelResponseWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper for working with {@link Hotel} entity.
 * <br>
 * Uses {@link RoomMapper} for mapping inner Hotel.rooms field.
 *
 * @see HotelResponse
 * @see HotelRequest
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = RoomMapper.class)
public interface HotelMapper {
    /**
     * {@link HotelRequest} to {@link Hotel} mapping.
     *
     * @param request {@link HotelRequest} for mapping.
     * @return mapped {@link Hotel}.
     */
    Hotel requestToModel(HotelRequest request);

    /**
     * {@link Hotel} to {@link HotelResponse} mapping.
     *
     * @param model {@link Hotel} for mapping.
     * @return mapped {@link HotelResponse}.
     */
    HotelResponse modelToResponse(Hotel model);

    /**
     * {@link Hotel} to {@link HotelResponseWith} mapping.
     *
     * @param model {@link Hotel} for mapping.
     * @return mapped {@link HotelResponseWith}.
     */
    HotelResponseWith modelToResponseWith(Hotel model);

    /**
     * List of {@link Hotel} to List of {@link HotelResponse} mapping.
     *
     * @param modelList List of {@link Hotel} for mapping.
     * @return mapped List of {@link HotelResponse}.
     * @see #modelToResponse(Hotel).
     */
    List<HotelResponse> modelListToResponseList(List<Hotel> modelList);

    /**
     * List of {@link Hotel} to {@link HotelListResponse} mapping.
     *
     * @param modelList List of {@link Hotel} for mapping.
     * @return mapped {@link HotelListResponse}.
     * @see #modelListToResponseList(List)
     */
    default HotelListResponse modelListToModelListResponse(
            List<Hotel> modelList) {
        return new HotelListResponse(
                modelListToResponseList(modelList)
        );
    }
}
