package com.rest.hotelbooking.service.impl;

import com.rest.hotelbooking.exception.EntityNotFoundException;
import com.rest.hotelbooking.model.entity.Hotel;
import com.rest.hotelbooking.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("HotelServiceImplTest Tests")
public class HotelServiceImplTest {
    private HotelServiceImpl hotelService;
    @Mock
    private HotelRepository hotelRepository;
    private final int defaultPageSize = 10;

    @BeforeEach
    void setUp() {
        hotelService = new HotelServiceImpl(hotelRepository);
        try {
            Field PageSize = HotelServiceImpl.class
                    .getDeclaredField("defaultPageSize");
            PageSize.setAccessible(true);
            PageSize.setInt(hotelService, defaultPageSize);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("findAll test: get all hotel data.")
    void givenWhenGetAllThenListHotel() {
        List<Hotel> hotelList = new ArrayList<>(List.of(
                new Hotel(),
                new Hotel()
        ));
        PageRequest pageRequest = PageRequest.of(0, defaultPageSize);

        when(hotelRepository.findAll(pageRequest))
                .thenReturn(new PageImpl<>(hotelList));
        List<Hotel> actual = hotelService.findAll();

        assertEquals(hotelList.size(), actual.size());
        verify(hotelRepository, times(1))
                .findAll(pageRequest);
    }

    @Test
    @DisplayName("findById test: get hotel data by id.")
    void givenExistingIdWhenGetByIdThenHotel() {
        Long hotelId = 1L;
        Hotel defaultHotel = new Hotel(
                1L,
                "name",
                "adTitle",
                "city",
                "address",
                10L,
                new BigDecimal("4"),
                10L,
                Collections.emptyList()
        );

        when(hotelRepository.findById(hotelId))
                .thenReturn(Optional.of(defaultHotel));

        Hotel actual = hotelService.findById(hotelId);

        assertEquals(defaultHotel, actual);
        verify(hotelRepository, times(1))
                .findById(any());
    }

    @Test
    @DisplayName("findById test: try to get hotel data by not existing id.")
    void givenNotExistingIdWhenGetByIdThenThrow() {
        Long hotelId = 1L;

        when(hotelRepository.findById(hotelId))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> hotelService.findById(hotelId),
                " index is incorrect."
        );
        verify(hotelRepository, times(1))
                .findById(any());
    }

    @Test
    @DisplayName("save test: send hotel data to repository.")
    void givenHotelWhenSendHotelToDbThenSavedHotel() {
        String defaultPass = "pass";
        Hotel hotelToSave = new Hotel(
                1L,
                "name",
                "adTitle",
                "city",
                "address",
                10L,
                new BigDecimal("4"),
                10L,
                Collections.emptyList()
        );

        when(hotelRepository.save(hotelToSave))
                .thenReturn(hotelToSave);

        Hotel actual = hotelService.save(hotelToSave);

        assertEquals(hotelToSave, actual);
        verify(hotelRepository, times(1))
                .save(any());
    }

    @Test
    @DisplayName("update test: send hotel data to repository.")
    void givenHotelAndHotelIdWhenSendHotelToDbThenUpdatedHotel() {
        String defaultPass = "pass";
        Long hotelId = 1L;
        Hotel hotelToUpdate = new Hotel(
                1L,
                "name",
                "adTitle",
                "city",
                "address",
                10L,
                new BigDecimal("4"),
                10L,
                Collections.emptyList()
        );

        when(hotelRepository.findById(hotelId))
                .thenReturn(Optional.of(hotelToUpdate));
        when(hotelRepository.save(hotelToUpdate))
                .thenReturn(hotelToUpdate);

        Hotel actual = hotelService.update(hotelId, hotelToUpdate);

        assertEquals(hotelToUpdate, actual);
        verify(hotelRepository, times(1))
                .save(any());
        verify(hotelRepository, times(1))
                .findById(any());
    }

    @Test
    @DisplayName("update test: try update with not existed hotel id.")
    void givenHotelAndNotExistedHotelIdWhenSendHotelToDbThenUpdatedHotel() {
        String defaultPass = "pass";
        Long notExistedHotelId = 1L;
        Hotel hotelToUpdate = new Hotel(
                1L,
                "name",
                "adTitle",
                "city",
                "address",
                10L,
                new BigDecimal("4"),
                10L,
                Collections.emptyList()
        );

        when(hotelRepository.findById(notExistedHotelId))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> hotelService.update(notExistedHotelId, hotelToUpdate),
                "HotelId is incorrect."
        );
        verify(hotelRepository, times(0))
                .save(any());
        verify(hotelRepository, times(1))
                .findById(any());
    }

    @Test
    @DisplayName("delete test: delete hotel data message to repository.")
    void givenExistedHotelIdWhenSendHotelToDbThenUpdatedHotel() {
        Long existedHotelId = 1L;

        when(hotelRepository.findById(existedHotelId))
                .thenReturn(Optional.of(new Hotel()));
        hotelService.deleteById(existedHotelId);

        verify(hotelRepository, times(1))
                .findById(existedHotelId);
        verify(hotelRepository, times(1))
                .deleteById(existedHotelId);
    }

    @Test
    @DisplayName("delete test: delete hotel data message to repository.")
    void givenNotExistedHotelIdWhenSendHotelToDbThenUpdatedHotel() {
        Long notExistedHotelId = 1L;

        assertThrows(EntityNotFoundException.class,
                () -> hotelService.deleteById(notExistedHotelId),
                "HotelId is incorrect."
        );
        verify(hotelRepository, times(1))
                .findById(notExistedHotelId);
        verify(hotelRepository, times(0))
                .deleteById(notExistedHotelId);
    }
}
