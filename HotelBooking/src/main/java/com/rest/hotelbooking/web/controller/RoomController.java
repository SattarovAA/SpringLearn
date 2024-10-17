package com.rest.hotelbooking.web.controller;

import com.rest.hotelbooking.mapper.RoomSimpleMapper;
import com.rest.hotelbooking.service.RoomService;
import com.rest.hotelbooking.web.dto.RoomFilter;
import com.rest.hotelbooking.web.dto.room.RoomListResponse;
import com.rest.hotelbooking.web.dto.room.RoomRequest;
import com.rest.hotelbooking.web.dto.room.RoomResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/room")
public class RoomController {
    private final RoomService roomService;
    private final RoomSimpleMapper roomMapper;


    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping(path = "/{id}")
    public ResponseEntity<RoomResponse> getById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(roomMapper.modelToResponse(
                        roomService.findById(id)
                ));
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<RoomListResponse> getAllFilterBy(@Valid RoomFilter filter) {
        return ResponseEntity.ok(
                roomMapper.modelListToModelListResponse(
                        roomService.filterBy(filter)
                )
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<RoomResponse> create(@RequestBody @Valid RoomRequest modelRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(roomMapper.modelToResponse(
                        roomService.save(
                                roomMapper.requestToModel(modelRequest)
                        )
                ));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/{id}")
    public ResponseEntity<RoomResponse> update(@PathVariable("id") Long id,
                                               @RequestBody @Valid RoomRequest modelRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(roomMapper.modelToResponse(
                        roomService.update(
                                id, roomMapper.requestToModel(id, modelRequest)
                        )
                ));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        roomService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
