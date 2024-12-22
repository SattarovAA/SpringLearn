package com.rest.hotelbooking.controller;

import com.rest.hotelbooking.mapper.RoomMapper;
import com.rest.hotelbooking.model.dto.room.RoomFilter;
import com.rest.hotelbooking.model.dto.room.RoomListResponse;
import com.rest.hotelbooking.model.dto.room.RoomRequest;
import com.rest.hotelbooking.model.dto.room.RoomResponse;
import com.rest.hotelbooking.model.entity.Hotel;
import com.rest.hotelbooking.model.entity.Room;
import com.rest.hotelbooking.service.RoomService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("RoomControllerTest tests")
public class RoomControllerTest {
    private final static String urlTemplate = "/api/room";
    @MockitoBean
    private RoomService roomService;
    @MockitoBean
    private RoomMapper roomMapper;
    @Autowired
    private MockMvc mockMvc;
    private final Room defaultRoom = new Room(
            1L,
            "name",
            "description",
            "number",
            1L,
            1L,
            Collections.emptyList(),
            new Hotel()
    );
    private final RoomResponse roomResponse = new RoomResponse(
            1L,
            "name",
            "description",
            "number",
            1L,
            1L,
            1L,
            Collections.emptyList()
    );

    @Test
    @WithAnonymousUser
    @DisplayName("getAllByFilter test: get all rooms data from anonymous user.")
    void givenAnonymousUserWhenGetAllByFilterUrlThenStatusUnauthorized()
            throws Exception {
        String getAllByFilterUrl = urlTemplate + "/filter";

        mockMvc.perform(get(getAllByFilterUrl))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser()
    @DisplayName("getAllByFilter test: get all rooms data " +
            "without filter parameters.")
    void givenZeroFilterParamWhenGetAllByFilterUrlThenStatusBadRequest()
            throws Exception {
        String getAllByFilterUrl = urlTemplate + "/filter";

        mockMvc.perform(get(getAllByFilterUrl))
                .andExpect(jsonPath("$.errorMessage").isString())
                .andExpect(status().isBadRequest());

        verify(roomMapper, times(0))
                .modelListToModelListResponse(any());
        verify(roomService, times(0))
                .filterBy(any());
    }

    @Test
    @WithMockUser()
    @DisplayName("getAllByFilter test: get all rooms data " +
            "with 2 filter parameters.")
    void givenTwoFilterParamWhenGetAllByFilterUrlThenRoomListResponse()
            throws Exception {
        String getAllByFilterUrl = urlTemplate + "/filter";
        RoomListResponse roomListResponse = new RoomListResponse(
                Collections.singletonList(roomResponse)
        );
        List<Room> roomList = Collections.singletonList(
                new Room()
        );
        RoomFilter expectedFilter = new RoomFilter(
                10, 0, null,
                null, null, null,
                null, null, null,
                null, null, null,
                null
        );

        when(roomService.filterBy(expectedFilter))
                .thenReturn(roomList);
        when(roomMapper.modelListToModelListResponse(roomList))
                .thenReturn(roomListResponse);

        mockMvc.perform(get(getAllByFilterUrl)
                        .param("pageSize", "10")
                        .param("pageNumber", "0")
                )
                .andExpect(jsonPath("$.rooms").isArray())
                .andExpect(status().isOk());

        verify(roomMapper, times(1))
                .modelListToModelListResponse(any());
        verify(roomService, times(1))
                .filterBy(any());
    }

    @Test
    @WithMockUser()
    @DisplayName("getAllByFilter test: get all rooms data " +
            "with 3 filter parameters.")
    void givenThreeFilterParamWhenGetAllByFilterUrlThenRoomListResponse()
            throws Exception {
        String getAllByFilterUrl = urlTemplate + "/filter";
        RoomListResponse taskListResponse = new RoomListResponse(
                Collections.singletonList(roomResponse)
        );
        List<Room> roomList = Collections.singletonList(
                new Room()
        );
        RoomFilter expectedFilter = new RoomFilter(
                10, 0, null,
                "name", null, null,
                null, null, null,
                null, null, null,
                null
        );

        when(roomService.filterBy(expectedFilter))
                .thenReturn(roomList);
        when(roomMapper.modelListToModelListResponse(roomList))
                .thenReturn(taskListResponse);

        mockMvc.perform(get(getAllByFilterUrl)
                        .param("pageSize", "10")
                        .param("pageNumber", "0")
                        .param("name", "name")
                )
                .andExpect(jsonPath("$.rooms").isArray())
                .andExpect(status().isOk());

        verify(roomMapper, times(1))
                .modelListToModelListResponse(any());
        verify(roomService, times(1))
                .filterBy(any());
    }

    @Test
    @WithAnonymousUser
    @DisplayName("getById test: get room data by id from anonymous user.")
    void givenAnonymousUserWhenGetByIdUrlThenStatusUnauthorized()
            throws Exception {
        String getByIdUrl = urlTemplate + "/1";

        mockMvc.perform(get(getByIdUrl))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser()
    @DisplayName("getById test: get room data by room id.")
    void givenRoomIdWhenGetByIdUrlThenRoomResponse()
            throws Exception {
        String getByIdUrl = urlTemplate + "/1";
        when(roomService.findById(1L))
                .thenReturn(defaultRoom);
        when(roomMapper.modelToResponse(defaultRoom))
                .thenReturn(roomResponse);

        mockMvc.perform(get(getByIdUrl))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").isString())
                .andExpect(jsonPath("$.description").isString())
                .andExpect(jsonPath("$.number").isString())
                .andExpect(jsonPath("$.cost").isNumber())
                .andExpect(jsonPath("$.capacity").isNumber())
                .andExpect(jsonPath("$.hotelId").isNumber())
                .andExpect(jsonPath("$.reservations").isArray())
                .andExpect(status().isOk());

        verify(roomMapper, times(1))
                .modelToResponse(any());
        verify(roomService, times(1))
                .findById(1L);
    }

    @Test
    @WithAnonymousUser
    @DisplayName("create test: create new room from anonymous user.")
    void givenAnonymousUserWhenUpdateUrlThenStatusUnauthorized()
            throws Exception {
        String createUrl = urlTemplate;
        mockMvc.perform(post(createUrl))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser()
    @DisplayName("create test: create new room from simple user.")
    void givenUserPrincipalWhenCreateUrlThenForbidden()
            throws Exception {
        String createUrl = urlTemplate;
        String requestRoomJson = """
                {
                   "name": "name",
                   "description": "description",
                   "number" : "number",
                   "cost" : 1,
                   "capacity" : 1,
                   "hotelId": 1
                }""";
        mockMvc.perform(post(createUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestRoomJson)
                )
                .andExpect(jsonPath("$.message").isString())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("create test: create new room from admin user.")
    void givenAdminPrincipalWhenCreateUrlThenHotelResponse()
            throws Exception {
        String createUrl = urlTemplate;
        String requestRoomJson = """
                {
                   "name": "name",
                   "description": "description",
                   "number" : "number",
                   "cost" : 1,
                   "capacity" : 1,
                   "hotelId": 1
                }""";
        RoomRequest correctRequest = new RoomRequest(
                "name",
                "description",
                "number",
                1L,
                1L,
                1L
        );
        when(roomMapper.requestToModel(correctRequest))
                .thenReturn(defaultRoom);
        when(roomService.save(defaultRoom))
                .thenReturn(defaultRoom);
        when(roomMapper.modelToResponse(defaultRoom))
                .thenReturn(roomResponse);

        mockMvc.perform(post(createUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestRoomJson)
                )
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").isString())
                .andExpect(jsonPath("$.description").isString())
                .andExpect(jsonPath("$.number").isString())
                .andExpect(jsonPath("$.cost").isNumber())
                .andExpect(jsonPath("$.capacity").isNumber())
                .andExpect(jsonPath("$.hotelId").isNumber())
                .andExpect(jsonPath("$.reservations").isArray())
                .andExpect(status().isCreated());

        verify(roomMapper, times(1))
                .requestToModel(any());
        verify(roomService, times(1))
                .save(any());
        verify(roomMapper, times(1))
                .modelToResponse(any());
    }
    @Test
    @WithAnonymousUser
    @DisplayName("update test: update room data by id from anonymous user.")
    void givenAnonymousUserWhenCreateUrlThenStatusUnauthorized()
            throws Exception {
        String updateUrl = urlTemplate + "/1";
        mockMvc.perform(put(updateUrl))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser()
    @DisplayName("update test: update room data by id from simple user.")
    void givenUserPrincipalWhenUpdateUrlThenForbidden()
            throws Exception {
        String updateUrl = urlTemplate + "/1";
        String requestRoomJson = """
                {
                   "name": "name",
                   "description": "description",
                   "number" : "number",
                   "cost" : 1,
                   "capacity" : 1,
                   "hotelId": 1
                }""";
        mockMvc.perform(put(updateUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestRoomJson)
                )
                .andExpect(jsonPath("$.message").isString())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("update test: update room data by id from admin user.")
    void givenAdminPrincipalWhenUpdateUrlThenRoomResponse()
            throws Exception {
        String updateUrl = urlTemplate + "/1";
        String requestRoomJson = """
                {
                   "name": "name",
                   "description": "description",
                   "number" : "number",
                   "cost" : 1,
                   "capacity" : 1,
                   "hotelId": 1
                }""";
        RoomRequest correctRequest = new RoomRequest(
                "name",
                "description",
                "number",
                1L,
                1L,
                1L
        );
        when(roomMapper.requestToModel(correctRequest))
                .thenReturn(defaultRoom);
        when(roomService.update(1L, defaultRoom))
                .thenReturn(defaultRoom);
        when(roomMapper.modelToResponse(defaultRoom))
                .thenReturn(roomResponse);

        mockMvc.perform(put(updateUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestRoomJson)
                )
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").isString())
                .andExpect(jsonPath("$.description").isString())
                .andExpect(jsonPath("$.number").isString())
                .andExpect(jsonPath("$.cost").isNumber())
                .andExpect(jsonPath("$.capacity").isNumber())
                .andExpect(jsonPath("$.hotelId").isNumber())
                .andExpect(jsonPath("$.reservations").isArray())
                .andExpect(status().isOk());

        verify(roomMapper, times(1))
                .requestToModel(any());
        verify(roomService, times(1))
                .update(any(), any());
        verify(roomMapper, times(1))
                .modelToResponse(any());
    }
    @Test
    @WithAnonymousUser
    @DisplayName("delete test: delete room data by id from anonymous user.")
    void givenAnonymousUserWhenDeleteUrlThenStatusUnauthorized()
            throws Exception {
        String deleteUrl = urlTemplate + "/1";

        mockMvc.perform(delete(deleteUrl))
                .andExpect(status().isUnauthorized());
        verify(roomService, times(0))
                .deleteById(any());
    }

    @Test
    @WithMockUser
    @DisplayName("delete test: delete room data by id from simple user.")
    void givenUserPrincipalWhenDeleteUrlThenStatusForbidden()
            throws Exception {
        String deleteUrl = urlTemplate + "/1";

        mockMvc.perform(delete(deleteUrl))
                .andExpect(status().isForbidden());
        verify(roomService, times(0))
                .deleteById(any());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("delete test: delete room data by id from admin user.")
    void givenAdminPrincipalWhenDeleteUrlThenStatusNoContent()
            throws Exception {
        String deleteUrl = urlTemplate + "/1";

        mockMvc.perform(delete(deleteUrl))
                .andExpect(status().isNoContent());
        verify(roomService, times(1))
                .deleteById(any());
    }
}
