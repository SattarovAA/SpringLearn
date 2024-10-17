package com.rest.hotelbooking.repository;

import com.rest.hotelbooking.model.Hotel;
import com.rest.hotelbooking.web.dto.HotelFilter;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public interface HotelSpecifications {
    static Specification<Hotel> withFilter(HotelFilter filter) {
        return Specification
                .where(byLongField(Hotel.Fields.id, filter.id()))
                .and(byStringField(Hotel.Fields.name, filter.name()))
                .and(byStringField(Hotel.Fields.adTitle, filter.adTitle()))
                .and(byStringField(Hotel.Fields.city, filter.city()))
                .and(byStringField(Hotel.Fields.address, filter.address()))
                .and(byLongField(Hotel.Fields.distanceFromCenter, filter.minDistanceFromCenter(), filter.maxDistanceFromCenter()))
                .and(byBigDecimalField(Hotel.Fields.rating, filter.minRating(), filter.maxRating()))
                .and(byLongField(Hotel.Fields.numberOfRatings, filter.minNumberOfRatings(), filter.maxNumberOfRatings()))
                ;
    }

    static Specification<Hotel> byStringField(String field, String criterion) {
        return ((root, query, criteriaBuilder) -> {
            if (criterion == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get(field), criterion);
        });
    }

    static Specification<Hotel> byLongField(String field, Long criterion) {
        return ((root, query, criteriaBuilder) -> {
            if (criterion == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get(field), criterion);
        });
    }

    static Specification<Hotel> byLongField(String field, Long min, Long max) {
        return ((root, query, criteriaBuilder) -> {
            if (max == null && min == null) {
                return null;
            }
            if (min == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get(field), max);
            }
            if (max == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get(field), min);
            }
            return criteriaBuilder.between(root.get(field), min, max);
        });
    }

    static Specification<Hotel> byBigDecimalField(String field, BigDecimal min, BigDecimal max) {
        return ((root, query, criteriaBuilder) -> {
            if (max == null && min == null) {
                return null;
            }
            if (min == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get(field), max);
            }
            if (max == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get(field), min);
            }
            return criteriaBuilder.between(root.get(field), min, max);
        });
    }
}
