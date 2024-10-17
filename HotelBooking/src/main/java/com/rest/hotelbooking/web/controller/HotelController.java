package com.rest.hotelbooking.web.controller;

import com.rest.hotelbooking.mapper.HotelSimpleMapper;
import com.rest.hotelbooking.web.dto.hotel.mark.HotelMark;
import com.rest.hotelbooking.service.HotelService;
import com.rest.hotelbooking.web.dto.HotelFilter;
import com.rest.hotelbooking.web.dto.hotel.HotelListResponse;
import com.rest.hotelbooking.web.dto.hotel.HotelRequest;
import com.rest.hotelbooking.web.dto.hotel.HotelResponse;
import com.rest.hotelbooking.web.dto.hotel.HotelResponseWith;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/hotel")
public class HotelController {
    private final HotelService hotelService;
    private final HotelSimpleMapper hotelMapper;

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping()
    public ResponseEntity<HotelListResponse> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(hotelMapper
                        .modelListToModelListResponse(hotelService.findAll())
                );
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<HotelListResponse> getAllFilterBy(@Valid HotelFilter filter) {
        return ResponseEntity.ok(
                hotelMapper.modelListToModelListResponse(
                        hotelService.filterBy(filter)
                )
        );
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping(path = "/{id}")
    public ResponseEntity<HotelResponseWith> getById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(hotelMapper.modelToResponseWith(
                        hotelService.findById(id)
                ));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<HotelResponse> create(@RequestBody @Valid HotelRequest modelRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(hotelMapper.modelToResponse(
                        hotelService.save(
                                hotelMapper.requestToModel(modelRequest)
                        )
                ));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/{id}")
    public ResponseEntity<HotelResponse> update(@PathVariable("id") Long id,
                                                @RequestBody @Valid HotelRequest modelRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(hotelMapper.modelToResponse(
                        hotelService.update(
                                id, hotelMapper.requestToModel(id, modelRequest)
                        )
                ));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        hotelService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping(path = "rating/{id}")
    public ResponseEntity<HotelResponse> addHotelNewMark(@PathVariable("id") Long hotelId,
                                                         @RequestBody @Valid HotelMark newMark) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(hotelMapper.modelToResponse(
                        hotelService.addNewMark(
                                hotelId, newMark)
                ));
    }
}
