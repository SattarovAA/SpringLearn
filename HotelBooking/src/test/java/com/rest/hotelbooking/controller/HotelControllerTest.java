package com.rest.hotelbooking.controller;

import com.rest.hotelbooking.mapper.HotelMapper;
import com.rest.hotelbooking.model.dto.hotel.HotelFilter;
import com.rest.hotelbooking.model.dto.hotel.HotelListResponse;
import com.rest.hotelbooking.model.dto.hotel.HotelRequest;
import com.rest.hotelbooking.model.dto.hotel.HotelResponse;
import com.rest.hotelbooking.model.dto.hotel.HotelResponseWith;
import com.rest.hotelbooking.model.dto.hotel.mark.HotelMark;
import com.rest.hotelbooking.model.entity.Hotel;
import com.rest.hotelbooking.service.HotelService;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("HotelControllerTest tests")
public class HotelControllerTest {
    private final static String urlTemplate = "/api/hotel";
    @MockitoBean
    private HotelService hotelService;
    @MockitoBean
    private HotelMapper hotelMapper;
    @Autowired
    private MockMvc mockMvc;
    private final Hotel defaultHotel = new Hotel(
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
    private final HotelResponse hotelResponse = new HotelResponse(
            1L,
            "name",
            "adTitle",
            "city",
            "address",
            10L,
            new BigDecimal("4"),
            10L
    );


    @Test
    @WithAnonymousUser
    @DisplayName("getAll test: get all hotels data from anonymous user.")
    void givenAnonymousUserWhenGetAllUrlThenStatusUnauthorized()
            throws Exception {
        String getAllUrl = urlTemplate;

        mockMvc.perform(get(getAllUrl))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser()
    @DisplayName("getAll test: get all hotels data.")
    void givenAdminUserWhenGetAllUrlThenHotelListResponse()
            throws Exception {
        String getAllUrl = urlTemplate;
        HotelResponse hotelResponse = new HotelResponse(
                1L,
                "title",
                "adTitle",
                "city",
                "address",
                10L,
                new BigDecimal("4"),
                10L
        );
        HotelListResponse hotelListResponse = new HotelListResponse(
                new ArrayList<>(List.of(hotelResponse))
        );
        List<Hotel> hotelList = Collections.singletonList(
                new Hotel()
        );

        when(hotelService.findAll())
                .thenReturn(hotelList);
        when(hotelMapper.modelListToModelListResponse(hotelList))
                .thenReturn(hotelListResponse);

        mockMvc.perform(get(getAllUrl))
                .andExpect(jsonPath("$.hotels").isArray())
                .andExpect(status().isOk());

        verify(hotelMapper, times(1))
                .modelListToModelListResponse(any());
        verify(hotelService, times(1))
                .findAll();
    }

    @Test
    @WithAnonymousUser
    @DisplayName("getAllByFilter test: get all hotels data from anonymous user.")
    void givenAnonymousUserWhenGetAllByFilterUrlThenStatusUnauthorized()
            throws Exception {
        String getAllByFilterUrl = urlTemplate + "/filter";

        mockMvc.perform(get(getAllByFilterUrl))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser()
    @DisplayName("getAllByFilter test: get all hotels data " +
            "without filter parameters.")
    void givenZeroFilterParamWhenGetAllByFilterUrlThenStatusBadRequest()
            throws Exception {
        String getAllByFilterUrl = urlTemplate + "/filter";

        mockMvc.perform(get(getAllByFilterUrl))
                .andExpect(jsonPath("$.errorMessage").isString())
                .andExpect(status().isBadRequest());

        verify(hotelMapper, times(0))
                .modelListToModelListResponse(any());
        verify(hotelService, times(0))
                .filterBy(any());
    }

    @Test
    @WithMockUser()
    @DisplayName("getAllByFilter test: get all hotels data " +
            "with 2 filter parameters.")
    void givenTwoFilterParamWhenGetAllByFilterUrlThenHotelListResponse()
            throws Exception {
        String getAllByFilterUrl = urlTemplate + "/filter";
        HotelResponse hotelResponse = new HotelResponse(
                1L,
                "name",
                "adTitle",
                "city",
                "address",
                10L,
                new BigDecimal("4"),
                10L
        );
        HotelListResponse taskListResponse = new HotelListResponse(
                Collections.singletonList(hotelResponse)
        );
        List<Hotel> hotelList = Collections.singletonList(
                new Hotel()
        );
        HotelFilter expectedFilter = new HotelFilter(
                10, 0, null,
                null, null, null,
                null, null, null,
                null, null, null,
                null
        );

        when(hotelService.filterBy(expectedFilter))
                .thenReturn(hotelList);
        when(hotelMapper.modelListToModelListResponse(hotelList))
                .thenReturn(taskListResponse);

        mockMvc.perform(get(getAllByFilterUrl)
                        .param("pageSize", "10")
                        .param("pageNumber", "0")
                )
                .andExpect(jsonPath("$.hotels").isArray())
                .andExpect(status().isOk());

        verify(hotelMapper, times(1))
                .modelListToModelListResponse(any());
        verify(hotelService, times(1))
                .filterBy(any());
    }

    /**
     * TODO add @CsvSource and @ParameterizedTest maybe.
     */
    @Test
    @WithMockUser()
    @DisplayName("getAllByFilter test: get all hotels data " +
            "with 3 filter parameters.")
    void givenThreeFilterParamWhenGetAllByFilterUrlThenHotelListResponse()
            throws Exception {
        String getAllByFilterUrl = urlTemplate + "/filter";
        HotelResponse hotelResponse = new HotelResponse(
                1L,
                "name",
                "adTitle",
                "city",
                "address",
                10L,
                new BigDecimal("4"),
                10L
        );
        HotelListResponse taskListResponse = new HotelListResponse(
                Collections.singletonList(hotelResponse)
        );
        List<Hotel> hotelList = Collections.singletonList(
                new Hotel()
        );
        HotelFilter expectedFilter = new HotelFilter(
                10, 0, null,
                "name", null, null,
                null, null, null,
                null, null, null,
                null
        );

        when(hotelService.filterBy(expectedFilter))
                .thenReturn(hotelList);
        when(hotelMapper.modelListToModelListResponse(hotelList))
                .thenReturn(taskListResponse);

        mockMvc.perform(get(getAllByFilterUrl)
                        .param("pageSize", "10")
                        .param("pageNumber", "0")
                        .param("name", "name")
                )
                .andExpect(jsonPath("$.hotels").isArray())
                .andExpect(status().isOk());

        verify(hotelMapper, times(1))
                .modelListToModelListResponse(any());
        verify(hotelService, times(1))
                .filterBy(any());
    }

    @Test
    @WithAnonymousUser
    @DisplayName("getById test: get hotel data by id from anonymous user.")
    void givenAnonymousUserWhenGetByIdUrlThenStatusUnauthorized()
            throws Exception {
        String getByIdUrl = urlTemplate + "/1";

        mockMvc.perform(get(getByIdUrl))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser()
    @DisplayName("getById test: get hotel data by hotel id.")
    void givenHotelIdWhenGetByIdUrlThenHotelResponseWith()
            throws Exception {
        String getByIdUrl = urlTemplate + "/1";
        HotelResponseWith hotelResponse = new HotelResponseWith(
                1L,
                "title",
                "adTitle",
                "city",
                "address",
                10L,
                new BigDecimal("4"),
                10L,
                Collections.emptyList()
        );
        when(hotelService.findById(1L))
                .thenReturn(defaultHotel);
        when(hotelMapper.modelToResponseWith(defaultHotel))
                .thenReturn(hotelResponse);

        mockMvc.perform(get(getByIdUrl))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").isString())
                .andExpect(jsonPath("$.adTitle").isString())
                .andExpect(jsonPath("$.city").isString())
                .andExpect(jsonPath("$.address").isString())
                .andExpect(jsonPath("$.distanceFromCenter").isNumber())
                .andExpect(jsonPath("$.rating").isNumber())
                .andExpect(jsonPath("$.numberOfRatings").isNumber())
                .andExpect(jsonPath("$.rooms").isArray())
                .andExpect(status().isOk());

        verify(hotelMapper, times(1))
                .modelToResponseWith(any());
        verify(hotelService, times(1))
                .findById(1L);
    }

    @Test
    @WithAnonymousUser
    @DisplayName("create test: create new hotel from anonymous user.")
    void givenAnonymousUserWhenCreateUrlThenStatusUnauthorized()
            throws Exception {
        String createUrl = urlTemplate;
        mockMvc.perform(post(createUrl))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser()
    @DisplayName("create test: create new hotel from simple user.")
    void givenUserPrincipalWhenCreateUrlThenForbidden()
            throws Exception {
        String createUrl = urlTemplate;
        String requestHotelJson = """
                {
                   "name": "name",
                   "adTitle": "adTitle",
                   "adTitle" : "adTitle",
                   "city" : "city",
                   "address" : "address",
                   "distanceFromCenter": 10
                }""";
        mockMvc.perform(post(createUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestHotelJson)
                )
                .andExpect(jsonPath("$.message").isString())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("create test: create new hotel from admin user.")
    void givenAdminPrincipalWhenCreateUrlThenHotelResponse()
            throws Exception {
        String createUrl = urlTemplate;
        String requestHotelJson = """
                {
                   "name": "name",
                   "adTitle": "adTitle",
                   "adTitle" : "adTitle",
                   "city" : "city",
                   "address" : "address",
                   "distanceFromCenter": 10
                }""";
        HotelRequest correctRequest = new HotelRequest(
                "name",
                "adTitle",
                "city",
                "address",
                10L
        );
        when(hotelMapper.requestToModel(correctRequest))
                .thenReturn(defaultHotel);
        when(hotelService.save(defaultHotel))
                .thenReturn(defaultHotel);
        when(hotelMapper.modelToResponse(defaultHotel))
                .thenReturn(hotelResponse);

        mockMvc.perform(post(createUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestHotelJson)
                )
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").isString())
                .andExpect(jsonPath("$.adTitle").isString())
                .andExpect(jsonPath("$.city").isString())
                .andExpect(jsonPath("$.address").isString())
                .andExpect(jsonPath("$.distanceFromCenter").isNumber())
                .andExpect(jsonPath("$.rating").isNumber())
                .andExpect(jsonPath("$.numberOfRatings").isNumber())
                .andExpect(status().isCreated());

        verify(hotelMapper, times(1))
                .requestToModel(any());
        verify(hotelService, times(1))
                .save(any());
        verify(hotelMapper, times(1))
                .modelToResponse(any());
    }

    @Test
    @WithAnonymousUser
    @DisplayName("update test: update hotel data by id from anonymous user.")
    void givenAnonymousUserWhenUpdateUrlThenStatusUnauthorized()
            throws Exception {
        String updateUrl = urlTemplate + "/1";
        mockMvc.perform(put(updateUrl))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser()
    @DisplayName("update test: update hotel data by id from simple user.")
    void givenUserPrincipalWhenUpdateUrlThenForbidden()
            throws Exception {
        String updateUrl = urlTemplate + "/1";
        String requestHotelJson = """
                {
                   "name": "name",
                   "adTitle": "adTitle",
                   "adTitle" : "adTitle",
                   "city" : "city",
                   "address" : "address",
                   "distanceFromCenter": 10
                }""";
        mockMvc.perform(put(updateUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestHotelJson)
                )
                .andExpect(jsonPath("$.message").isString())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("update test: update hotel data by id from admin user.")
    void givenAdminPrincipalWhenUpdateUrlThenHotelResponse()
            throws Exception {
        String updateUrl = urlTemplate + "/1";
        String requestHotelJson = """
                {
                   "name": "name",
                   "adTitle": "adTitle",
                   "adTitle" : "adTitle",
                   "city" : "city",
                   "address" : "address",
                   "distanceFromCenter": 10
                }""";
        HotelRequest correctRequest = new HotelRequest(
                "name",
                "adTitle",
                "city",
                "address",
                10L
        );
        when(hotelMapper.requestToModel(correctRequest))
                .thenReturn(defaultHotel);
        when(hotelService.update(1L, defaultHotel))
                .thenReturn(defaultHotel);
        when(hotelMapper.modelToResponse(defaultHotel))
                .thenReturn(hotelResponse);

        mockMvc.perform(put(updateUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestHotelJson)
                )
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").isString())
                .andExpect(jsonPath("$.adTitle").isString())
                .andExpect(jsonPath("$.city").isString())
                .andExpect(jsonPath("$.address").isString())
                .andExpect(jsonPath("$.distanceFromCenter").isNumber())
                .andExpect(jsonPath("$.rating").isNumber())
                .andExpect(jsonPath("$.numberOfRatings").isNumber())
                .andExpect(status().isOk());

        verify(hotelMapper, times(1))
                .requestToModel(any());
        verify(hotelService, times(1))
                .update(any(), any());
        verify(hotelMapper, times(1))
                .modelToResponse(any());
    }

    @Test
    @WithAnonymousUser
    @DisplayName("delete test: delete hotel data by id from anonymous user.")
    void givenAnonymousUserWhenDeleteUrlThenStatusUnauthorized()
            throws Exception {
        String deleteUrl = urlTemplate + "/1";

        mockMvc.perform(delete(deleteUrl))
                .andExpect(status().isUnauthorized());
        verify(hotelService, times(0))
                .deleteById(any());
    }

    @Test
    @WithMockUser
    @DisplayName("delete test: delete hotel data by id from simple user.")
    void givenUserPrincipalWhenDeleteUrlThenStatusForbidden()
            throws Exception {
        String deleteUrl = urlTemplate + "/1";

        mockMvc.perform(delete(deleteUrl))
                .andExpect(status().isForbidden());
        verify(hotelService, times(0))
                .deleteById(any());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("delete test: delete hotel data by id from admin user.")
    void givenAdminPrincipalWhenDeleteUrlThenStatusNoContent()
            throws Exception {
        String deleteUrl = urlTemplate + "/1";

        mockMvc.perform(delete(deleteUrl))
                .andExpect(status().isNoContent());
        verify(hotelService, times(1))
                .deleteById(any());
    }

    @Test
    @WithAnonymousUser
    @DisplayName("addHotelNewMark test: add hotel new mark by id" +
            " from anonymous user.")
    void givenAnonymousUserWhenAddNewMarkUrlThenStatusUnauthorized()
            throws Exception {
        String addNewMarkUrl = urlTemplate + "/rating/1";
        mockMvc.perform(put(addNewMarkUrl))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("addHotelNewMark test: add hotel new mark by id" +
            " from admin user.")
    void givenAdminPrincipalWhenAddNewMarkUrlThenHotelResponse()
            throws Exception {
        String addNewMarkUrl = urlTemplate + "/rating/1";
        String requestMarkJson = """
                {
                   "value": 4
                }""";
        HotelMark correctRequest = new HotelMark(4L);

        when(hotelService.addNewMark(1L, correctRequest))
                .thenReturn(defaultHotel);
        when(hotelMapper.modelToResponse(defaultHotel))
                .thenReturn(hotelResponse);

        mockMvc.perform(put(addNewMarkUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestMarkJson)
                )
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").isString())
                .andExpect(jsonPath("$.adTitle").isString())
                .andExpect(jsonPath("$.city").isString())
                .andExpect(jsonPath("$.address").isString())
                .andExpect(jsonPath("$.distanceFromCenter").isNumber())
                .andExpect(jsonPath("$.rating").isNumber())
                .andExpect(jsonPath("$.numberOfRatings").isNumber())
                .andExpect(status().isOk());

        verify(hotelService, times(1))
                .addNewMark(any(), any());
        verify(hotelMapper, times(1))
                .modelToResponse(any());
    }
}
