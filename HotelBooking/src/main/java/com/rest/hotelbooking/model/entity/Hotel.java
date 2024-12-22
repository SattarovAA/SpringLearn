package com.rest.hotelbooking.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Entity Hotel.
 * Entity for working with JpaRepository.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@FieldNameConstants
@Builder
@Entity
@Table(name = "hotels",
        indexes = {
                @Index(columnList = Hotel.Fields.rating),
                @Index(columnList = "distance_from_center")
        })
public class Hotel {
    /**
     * Long Hotel @Id for JpaRepository.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Hotel name.
     */
    @Column(name = "name")
    private String name;
    /**
     * Hotel ad title.
     */
    @Column(name = "ad_title")
    private String adTitle;
    /**
     * City location.
     */
    @Column(name = "city")
    private String city;
    /**
     * Hotel address.
     */
    @Column(name = "address")
    private String address;
    /**
     * Distance from the hotel to the center.
     */
    @Column(name = "distance_from_center")
    private Long distanceFromCenter;
    /**
     * Hotel rating.
     *
     * @value new BigDecimal("0")
     */
    @Column(name = "rating")
    @Builder.Default
    private BigDecimal rating = new BigDecimal("0");
    /**
     * Number of hotel ratings.
     *
     * @value 0L
     */
    @Column(name = "number_of_ratings")
    @Builder.Default
    private Long numberOfRatings = 0L;
    /**
     * Available rooms in Hotel.
     *
     * @value Collections.emptyList()
     */
    @OneToMany(mappedBy = Room.Fields.hotel, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Room> rooms = Collections.emptyList();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Hotel hotel = (Hotel) o;
        return Objects.equals(id, hotel.id)
                && Objects.equals(name, hotel.name)
                && Objects.equals(adTitle, hotel.adTitle)
                && Objects.equals(city, hotel.city)
                && Objects.equals(address, hotel.address)
                && Objects.equals(distanceFromCenter, hotel.distanceFromCenter)
                && Objects.equals(rating, hotel.rating)
                && Objects.equals(numberOfRatings, hotel.numberOfRatings)
                && Objects.equals(rooms.size(), hotel.rooms.size());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id, name, adTitle, city, address,
                distanceFromCenter, rating, numberOfRatings, rooms.size()
        );
    }
}
