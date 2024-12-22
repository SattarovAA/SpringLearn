package com.rest.hotelbooking.controller;

import com.rest.hotelbooking.mapper.RoomMapper;
import com.rest.hotelbooking.model.dto.room.RoomFilter;
import com.rest.hotelbooking.model.dto.room.RoomListResponse;
import com.rest.hotelbooking.model.dto.room.RoomRequest;
import com.rest.hotelbooking.model.dto.room.RoomResponse;
import com.rest.hotelbooking.service.RoomService;
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
 * Controller for working with entity room.
 *
 * @see RoomRequest
 * @see RoomResponse
 * @see RoomListResponse
 */
@Tag(name = "RoomController",
        description = "Controller for working with rooms.")
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/room")
@Slf4j
public class RoomController {
    /**
     * Service for working with entity room.
     */
    private final RoomService roomService;
    /**
     * Mapper for working with entity room.
     */
    private final RoomMapper roomMapper;

    /**
     * Get all rooms by filter.
     *
     * @param filter {@link RoomFilter} with the required parameters.
     * @return {@link ResponseEntity} with {@link HttpStatus#OK}
     * and {@link RoomListResponse}.
     */
    @Operation(
            summary = "Get all rooms by filter.",
            tags = {"room", "get", "filter"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = RoomListResponse.class))
            }),
            @ApiResponse(responseCode = "401")
    })
    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<RoomListResponse> getAllFilterBy(
            @Valid RoomFilter filter
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(roomMapper.modelListToModelListResponse(
                                roomService.filterBy(filter)
                        )
                );
    }

    /**
     * Get room with id.
     *
     * @param id the id of the room to retrieve.
     * @return {@link ResponseEntity} with {@link HttpStatus#OK}
     * and {@link RoomResponse} with searched id.
     */
    @Operation(
            summary = "Get room by id.",
            description = "Get a Room object by specifying its id. " +
                    "The response is Room object with " +
                    "id, name, description, number, cost," +
                    "capacity, hotelId, list reservations.",
            tags = {"room", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = RoomResponse.class))
            }),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "401")
    })
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping(path = "/{id}")
    public ResponseEntity<RoomResponse> getById(@PathVariable("id") Long id) {
        log.info("Try to get room with id: " + id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(roomMapper.modelToResponse(
                                roomService.findById(id)
                        )
                );
    }

    /**
     * Create new room by {@link RoomRequest}.
     *
     * @param modelRequest {@link RoomRequest} to create room.
     * @return {@link ResponseEntity} with {@link HttpStatus#CREATED}
     * and {@link RoomResponse} by saved room.
     */
    @Operation(
            summary = "Create new room.",
            description = "Only with admin access.",
            tags = {"room", "post"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = RoomResponse.class))
            }),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "401"),
            @ApiResponse(responseCode = "403"),
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<RoomResponse> create(
            @RequestBody @Valid RoomRequest modelRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(roomMapper.modelToResponse(
                                roomService.save(
                                        roomMapper.requestToModel(modelRequest)
                                )
                        )
                );
    }

    /**
     * Update room with id by {@link RoomRequest}.
     *
     * @param id           room id to update room.
     * @param modelRequest {@link RoomRequest} to update room.
     * @return {@link ResponseEntity} with {@link HttpStatus#OK}
     * and {@link RoomResponse} by updated room.
     */
    @Operation(
            summary = "Update room by specifying its id.",
            description = "Only with admin access.",
            tags = {"room", "put"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = RoomResponse.class))
            }),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "401"),
            @ApiResponse(responseCode = "403")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/{id}")
    public ResponseEntity<RoomResponse> update(
            @PathVariable("id") Long id,
            @RequestBody @Valid RoomRequest modelRequest
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(roomMapper.modelToResponse(
                                roomService.update(
                                        id,
                                        roomMapper.requestToModel(modelRequest)
                                )
                        )
                );
    }

    /**
     * Delete room by id.
     *
     * @param id id room to delete room.
     * @return {@link ResponseEntity} with {@link HttpStatus#NO_CONTENT}.
     */
    @Operation(
            summary = "Delete room by specifying its id.",
            description = "Only with admin access.",
            tags = {"room", "delete"})
    @ApiResponses({
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "401"),
            @ApiResponse(responseCode = "403")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        roomService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
