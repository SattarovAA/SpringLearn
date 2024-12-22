package com.rest.hotelbooking.controller;

import com.rest.hotelbooking.mapper.HotelMapper;
import com.rest.hotelbooking.model.dto.hotel.HotelFilter;
import com.rest.hotelbooking.model.dto.hotel.HotelListResponse;
import com.rest.hotelbooking.model.dto.hotel.HotelRequest;
import com.rest.hotelbooking.model.dto.hotel.HotelResponse;
import com.rest.hotelbooking.model.dto.hotel.HotelResponseWith;
import com.rest.hotelbooking.model.dto.hotel.mark.HotelMark;
import com.rest.hotelbooking.service.HotelService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for working with entity hotel.
 *
 * @see HotelRequest
 * @see HotelResponse
 * @see HotelResponseWith
 * @see HotelListResponse
 */
@Tag(name = "HotelController",
        description = "Controller for working with hotels.")
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/hotel")
@Slf4j
public class HotelController {
    /**
     * Service for working with entity hotel.
     */
    private final HotelService hotelService;
    /**
     * Mapper for working with hotel entity.
     */
    private final HotelMapper hotelMapper;

    /**
     * Get all hotels.
     *
     * @return {@link ResponseEntity} with {@link HttpStatus#OK}
     * and {@link HotelListResponse}.
     */
    @Operation(
            summary = "Get all hotels.",
            tags = {"hotel", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = HotelListResponse.class))
            }),
            @ApiResponse(responseCode = "401")
    })
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping()
    public ResponseEntity<HotelListResponse> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(hotelMapper.modelListToModelListResponse(
                                hotelService.findAll()
                        )
                );
    }

    /**
     * Get all hotels with filter.
     *
     * @param filter {@link HotelFilter} with the required parameters.
     * @return {@link ResponseEntity} with {@link HttpStatus#OK}
     * and {@link HotelListResponse}.
     */
    @Operation(
            summary = "Get hotels by filter.",
            tags = {"hotel", "get", "filter"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = HotelListResponse.class))
            }),
            @ApiResponse(responseCode = "401")
    })
    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<HotelListResponse> getAllFilterBy(
            @Valid HotelFilter filter) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(hotelMapper.modelListToModelListResponse(
                                hotelService.filterBy(filter)
                        )
                );
    }

    /**
     * Get hotel with id.
     *
     * @param id the id of the hotel to retrieve.
     * @return {@link ResponseEntity} with {@link HttpStatus#OK}
     * and {@link HotelResponseWith} with searched id.
     */
    @Operation(
            summary = "Get hotel by id.",
            description = "Get a Hotel object by specifying its id. " +
                    "The response is Hotel object with " +
                    "id, name, adTitle, city, address, list rooms" +
                    "distanceFromCenter, rating, numberOfRatings.",
            tags = {"hotel", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = HotelResponseWith.class))
            }),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "401")
    })
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping(path = "/{id}")
    public ResponseEntity<HotelResponseWith> getById(
            @PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(hotelMapper.modelToResponseWith(
                                hotelService.findById(id)
                        )
                );
    }

    /**
     * Create new hotel by {@link HotelRequest}.
     *
     * @param modelRequest {@link HotelRequest} to create new hotel.
     * @return {@link ResponseEntity} with {@link HttpStatus#CREATED}
     * and {@link HotelResponse} by saved hotel.
     */
    @Operation(
            summary = "Create new hotel.",
            description = "Only with admin access.",
            tags = {"hotel", "post"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = HotelResponse.class))
            }),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "401"),
            @ApiResponse(responseCode = "403"),
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<HotelResponse> create(
            @RequestBody @Valid HotelRequest modelRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(hotelMapper.modelToResponse(
                                hotelService.save(
                                        hotelMapper.requestToModel(modelRequest)
                                )
                        )
                );
    }

    /**
     * Update hotel with id by {@link HotelRequest}.
     *
     * @param id           hotel id to update hotel.
     * @param modelRequest {@link HotelRequest} to update hotel.
     * @return {@link ResponseEntity} with {@link HttpStatus#OK}
     * and {@link HotelResponse} by updated hotel.
     */
    @Operation(
            summary = "Update hotel by specifying its id.",
            description = "Only with admin access.",
            tags = {"hotel", "put"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = HotelResponse.class))
            }),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "401"),
            @ApiResponse(responseCode = "403")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/{id}")
    public ResponseEntity<HotelResponse> update(
            @PathVariable("id") Long id,
            @RequestBody @Valid HotelRequest modelRequest
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(hotelMapper.modelToResponse(
                                hotelService.update(
                                        id,
                                        hotelMapper.requestToModel(modelRequest)
                                )
                        )
                );
    }

    /**
     * Delete hotel by id.
     *
     * @param id id hotel to delete hotel.
     * @return {@link ResponseEntity} with {@link HttpStatus#NO_CONTENT}.
     */
    @Operation(
            summary = "Delete hotel by specifying its id.",
            description = "Only with admin access.",
            tags = {"hotel", "delete"})
    @ApiResponses({
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "401"),
            @ApiResponse(responseCode = "403")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        hotelService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Add hotel new mark.
     *
     * @param hotelId hotel to adding new mark.
     * @param newMark {@link HotelMark} to add to hotel rating.
     * @return {@link ResponseEntity} with {@link HttpStatus#OK}
     * and {@link HotelResponse} by updated hotel.
     */
    @Operation(
            summary = "add hotel new mark by specifying its id.",
            tags = {"hotel", "put", "mark"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = HotelResponse.class))
            }),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "401")
    })
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping(path = "rating/{id}")
    public ResponseEntity<HotelResponse> addHotelNewMark(
            @PathVariable("id") Long hotelId,
            @RequestBody @Valid HotelMark newMark
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(hotelMapper.modelToResponse(
                                hotelService.addNewMark(
                                        hotelId,
                                        newMark
                                )
                        )
                );
    }
}
