package com.rest.hotelbooking.service.impl;

import com.rest.hotelbooking.exception.EntityNotFoundException;
import com.rest.hotelbooking.model.entity.Hotel;
import com.rest.hotelbooking.model.entity.Room;
import com.rest.hotelbooking.repository.RoomRepository;
import com.rest.hotelbooking.service.HotelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.lang.reflect.Field;
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
@DisplayName("RoomServiceImplTest Tests")
public class RoomServiceImplTest {
    private RoomServiceImpl roomService;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private HotelService hotelService;
    private final int defaultPageSize = 10;

    @BeforeEach
    void setUp() {
        roomService = new RoomServiceImpl(roomRepository, hotelService);
        try {
            Field PageSize = RoomServiceImpl.class
                    .getDeclaredField("defaultPageSize");
            PageSize.setAccessible(true);
            PageSize.setInt(roomService, defaultPageSize);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("findAll test: get all room data.")
    void givenWhenGetAllThenListRoom() {
        List<Room> roomList = new ArrayList<>(List.of(
                new Room(),
                new Room()
        ));
        PageRequest pageRequest = PageRequest.of(0, defaultPageSize);

        when(roomRepository.findAll(pageRequest))
                .thenReturn(new PageImpl<>(roomList));
        List<Room> actual = roomService.findAll();

        assertEquals(roomList.size(), actual.size());
        verify(roomRepository, times(1))
                .findAll(pageRequest);
    }

    @Test
    @DisplayName("findById test: get room data by id.")
    void givenExistingIdWhenGetByIdThenRoom() {
        Long roomId = 1L;
        Room defaultRoom = new Room(
                1L,
                "name",
                "description",
                "number",
                1L,
                1L,
                Collections.emptyList(),
                new Hotel()
        );

        when(roomRepository.findById(roomId))
                .thenReturn(Optional.of(defaultRoom));

        Room actual = roomService.findById(roomId);

        assertEquals(defaultRoom, actual);
        verify(roomRepository, times(1))
                .findById(any());
    }

    @Test
    @DisplayName("findById test: try to get room data by not existing id.")
    void givenNotExistingIdWhenGetByIdThenThrow() {
        Long roomId = 1L;

        when(roomRepository.findById(roomId))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> roomService.findById(roomId),
                " index is incorrect."
        );
        verify(roomRepository, times(1))
                .findById(any());
    }

    @Test
    @DisplayName("save test: send room data to repository.")
    void givenRoomWhenSendRoomToDbThenSavedRoom() {
        Room roomToSave = new Room(
                null,
                "name",
                "description",
                "number",
                1L,
                1L,
                Collections.emptyList(),
                new Hotel()
        );

        when(roomRepository.save(roomToSave))
                .thenReturn(roomToSave);
        when(hotelService.findById(any()))
                .thenReturn(new Hotel());

        Room actual = roomService.save(roomToSave);

        assertEquals(roomToSave, actual);
        verify(roomRepository, times(1))
                .save(any());
        verify(hotelService, times(1))
                .findById(any());
    }

    @Test
    @DisplayName("update test: send room data to repository.")
    void givenRoomAndRoomIdWhenSendRoomToDbThenUpdatedRoom() {
        Long roomId = 1L;
        Room roomToUpdate = new Room(
                1L,
                "name",
                "description",
                "number",
                1L,
                1L,
                Collections.emptyList(),
                new Hotel()
        );

        when(roomRepository.findById(roomId))
                .thenReturn(Optional.of(roomToUpdate));
        when(roomRepository.save(roomToUpdate))
                .thenReturn(roomToUpdate);

        Room actual = roomService.update(roomId, roomToUpdate);

        assertEquals(roomToUpdate, actual);
        verify(roomRepository, times(1))
                .save(any());
        verify(roomRepository, times(1))
                .findById(any());
    }

    @Test
    @DisplayName("update test: try update with not existed room id.")
    void givenRoomAndNotExistedRoomIdWhenSendRoomToDbThenUpdatedRoom() {
        Long notExistedRoomId = 1L;
        Room roomToUpdate = new Room(
                1L,
                "name",
                "description",
                "number",
                1L,
                1L,
                Collections.emptyList(),
                new Hotel()
        );

        when(roomRepository.findById(notExistedRoomId))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> roomService.update(notExistedRoomId, roomToUpdate),
                "RoomId is incorrect."
        );
        verify(roomRepository, times(0))
                .save(any());
        verify(roomRepository, times(1))
                .findById(any());
    }

    @Test
    @DisplayName("delete test: delete room data message to repository.")
    void givenExistedRoomIdWhenSendRoomToDbThenUpdatedRoom() {
        Long existedRoomId = 1L;

        when(roomRepository.findById(existedRoomId))
                .thenReturn(Optional.of(new Room()));
        roomService.deleteById(existedRoomId);

        verify(roomRepository, times(1))
                .findById(existedRoomId);
        verify(roomRepository, times(1))
                .deleteById(existedRoomId);
    }

    @Test
    @DisplayName("delete test: delete room data message to repository.")
    void givenNotExistedRoomIdWhenSendRoomToDbThenUpdatedRoom() {
        Long notExistedRoomId = 1L;

        assertThrows(EntityNotFoundException.class,
                () -> roomService.deleteById(notExistedRoomId),
                "RoomId is incorrect."
        );
        verify(roomRepository, times(1))
                .findById(notExistedRoomId);
        verify(roomRepository, times(0))
                .deleteById(notExistedRoomId);
    }
}
