package com.rest.hotelbooking.repository.specification;

import com.rest.hotelbooking.model.entity.Hotel;
import com.rest.hotelbooking.model.dto.hotel.HotelFilter;
import org.springframework.data.jpa.domain.Specification;

/**
 * Specifications for working with entity {@link Hotel}.
 */
public interface HotelSpecifications {
    /**
     * Default specifications for entity Hotel.
     */
    SimpleSpec<Hotel> SIMPLE_SPEC = new SimpleSpec<>();

    /**
     * Specification with filter.
     *
     * @param filter {@link HotelFilter} with the required parameters
     * @return Specification with the required parameters
     * @see SimpleSpec
     */
    static Specification<Hotel> withFilter(HotelFilter filter) {
        return Specification
                .where(SIMPLE_SPEC.byLongField(Hotel.Fields.id,
                                filter.id()
                        )
                )
                .and(SIMPLE_SPEC.byStringField(Hotel.Fields.name,
                                filter.name()
                        )
                )
                .and(SIMPLE_SPEC.byStringField(Hotel.Fields.adTitle,
                                filter.adTitle()
                        )
                )
                .and(SIMPLE_SPEC.byStringField(Hotel.Fields.city,
                                filter.city()
                        )
                )
                .and(SIMPLE_SPEC.byStringField(Hotel.Fields.address,
                                filter.address()
                        )
                )
                .and(SIMPLE_SPEC.byNumberField(Hotel.Fields.distanceFromCenter,
                                filter.minDistanceFromCenter(),
                                filter.maxDistanceFromCenter()
                        )
                )
                .and(SIMPLE_SPEC.byNumberField(Hotel.Fields.rating,
                                filter.minRating(),
                                filter.maxRating()
                        )
                )
                .and(SIMPLE_SPEC.byNumberField(Hotel.Fields.numberOfRatings,
                                filter.minNumberOfRatings(),
                                filter.maxNumberOfRatings()
                        )
                );
    }
}
