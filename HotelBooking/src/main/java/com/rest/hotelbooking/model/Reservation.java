package com.rest.hotelbooking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@FieldNameConstants
@Builder
@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "check_in")
    private LocalDate checkIn;
    @Column(name = "check_out")
    private LocalDate checkOut;
    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
