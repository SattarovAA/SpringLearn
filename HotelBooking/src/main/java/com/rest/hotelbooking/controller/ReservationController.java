package com.rest.hotelbooking.controller;

import com.rest.hotelbooking.mapper.ReservationMapper;
import com.rest.hotelbooking.model.dto.reservation.ReservationListResponse;
import com.rest.hotelbooking.model.dto.reservation.ReservationRequest;
import com.rest.hotelbooking.model.dto.reservation.ReservationResponse;
import com.rest.hotelbooking.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for working with entity reservation.
 *
 * @see ReservationRequest
 * @see ReservationResponse
 * @see ReservationListResponse
 */
@Tag(name = "ReservationController",
        description = "Controller for working with reservations.")
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/reservation")
@Slf4j
public class ReservationController {
    /**
     * Service for working with entity reservation.
     */
    private final ReservationService reservationService;
    /**
     * Mapper for working with entity reservation.
     */
    private final ReservationMapper reservationMapper;

    /**
     * Get all reservations.
     *
     * @return {@link ResponseEntity} with {@link HttpStatus#OK}
     * and {@link ReservationListResponse}.
     */
    @Operation(
            summary = "Get all reservations.",
            description = "Only with admin access.",
            tags = {"reservation", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ReservationListResponse.class))
            }),
            @ApiResponse(responseCode = "401"),
            @ApiResponse(responseCode = "403")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<ReservationListResponse> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(reservationMapper.modelListToModelListResponse(
                                reservationService.findAll()
                        )
                );
    }

    /**
     * Create new reservation by {@link ReservationRequest}.
     *
     * @param modelRequest {@link ReservationRequest} to create reservation.
     * @return {@link ResponseEntity} with {@link HttpStatus#CREATED}
     * and {@link ReservationResponse} by saved reservation.
     */
    @Operation(
            summary = "Create new reservation.",
            tags = {"reservation", "post"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = ReservationResponse.class))
            }),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "401")
    })
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping()
    public ResponseEntity<ReservationResponse> create(
            @RequestBody @Valid ReservationRequest modelRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reservationMapper.modelToResponse(
                                reservationService.save(
                                        reservationMapper.requestToModel(modelRequest)
                                )
                        )
                );
    }
}
