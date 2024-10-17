package com.rest.hotelbooking.web.controller;

import com.rest.hotelbooking.mapper.ReservationSimpleMapper;
import com.rest.hotelbooking.service.ReservationService;
import com.rest.hotelbooking.web.dto.reservation.ReservationListResponse;
import com.rest.hotelbooking.web.dto.reservation.ReservationRequest;
import com.rest.hotelbooking.web.dto.reservation.ReservationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/reservation")
public class ReservationController {
    private final ReservationService reservationService;
    private final ReservationSimpleMapper reservationMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<ReservationListResponse> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(reservationMapper
                        .modelListToModelListResponse(reservationService.findAll())
                );
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping()
    public ResponseEntity<ReservationResponse> create(@RequestBody @Valid ReservationRequest modelRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reservationMapper.modelToResponse(
                        reservationService.save(
                                reservationMapper.requestToModel(modelRequest)
                        )
                ));
    }
}
