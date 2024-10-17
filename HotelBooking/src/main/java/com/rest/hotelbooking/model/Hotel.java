package com.rest.hotelbooking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@FieldNameConstants
@Builder
@Entity
@Table(name = "hotels")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "ad_title")
    private String adTitle;
    @Column(name = "city")
    private String city;
    @Column(name = "address")
    private String address;
    @Column(name = "distance_from_center")
    private Long distanceFromCenter;
    @Column(name = "rating")
    @Builder.Default
    private BigDecimal rating = new BigDecimal("0");
    @Column(name = "number_of_ratings")
    @Builder.Default
    private Long numberOfRatings = 0L;
    @OneToMany(mappedBy = Room.Fields.hotel, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Room> rooms = Collections.emptyList();

}
