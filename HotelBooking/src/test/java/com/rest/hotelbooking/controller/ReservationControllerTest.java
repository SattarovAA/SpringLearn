package com.rest.hotelbooking.controller;

import com.rest.hotelbooking.mapper.ReservationMapper;
import com.rest.hotelbooking.model.dto.reservation.ReservationListResponse;
import com.rest.hotelbooking.model.dto.reservation.ReservationRequest;
import com.rest.hotelbooking.model.dto.reservation.ReservationResponse;
import com.rest.hotelbooking.model.entity.Reservation;
import com.rest.hotelbooking.model.entity.Room;
import com.rest.hotelbooking.model.entity.User;
import com.rest.hotelbooking.model.security.AppUserDetails;
import com.rest.hotelbooking.model.security.RoleType;
import com.rest.hotelbooking.service.ReservationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("ReservationControllerTest tests")
public class ReservationControllerTest {
    private final static String urlTemplate = "/api/reservation";
    @MockitoBean
    private ReservationService reservationService;
    @MockitoBean
    private ReservationMapper reservationMapper;
    @Autowired
    private MockMvc mockMvc;

    private final ReservationResponse roomResponse = new ReservationResponse(
            1L,
            LocalDate.now(),
            LocalDate.now(),
            1L
    );

    @Test
    @WithAnonymousUser
    @DisplayName("getAll test: get all reservations data from anonymous user.")
    void givenAnonymousUserWhenGetAllUrlThenStatusUnauthorized()
            throws Exception {
        String getAllUrl = urlTemplate;

        mockMvc.perform(get(getAllUrl))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    @DisplayName("getAll test: get all reservations data from anonymous user.")
    void givenSimpleUserWhenGetAllUrlThenStatusForbidden()
            throws Exception {
        String getAllUrl = urlTemplate;

        mockMvc.perform(get(getAllUrl))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("getAll test: get all reservations data.")
    void givenAdminUserWhenGetAllUrlThenReservationListResponse()
            throws Exception {
        String getAllUrl = urlTemplate;
        ReservationListResponse taskListResponse = new ReservationListResponse(
                new ArrayList<>(List.of(roomResponse))
        );
        List<Reservation> reservationList = Collections.singletonList(
                new Reservation()
        );

        when(reservationService.findAll())
                .thenReturn(reservationList);
        when(reservationMapper.modelListToModelListResponse(reservationList))
                .thenReturn(taskListResponse);

        mockMvc.perform(get(getAllUrl))
                .andExpect(jsonPath("$.reservations").isArray())
                .andExpect(status().isOk());

        verify(reservationMapper, times(1))
                .modelListToModelListResponse(any());
        verify(reservationService, times(1))
                .findAll();
    }

    @Test
    @WithAnonymousUser
    @DisplayName("create test: create new reservation from anonymous user.")
    void givenAnonymousUserWhenUpdateUrlThenStatusUnauthorized()
            throws Exception {
        String createUrl = urlTemplate;
        mockMvc.perform(post(createUrl))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    @DisplayName("create test: create new reservation.")
    void givenReservationWhenCreateUrlThenReservationResponse()
            throws Exception {
        String createUrl = urlTemplate;
        Reservation defaultReservation = new Reservation(
                1L,
                LocalDate.of(2000, 1, 1),
                LocalDate.of(2000, 1, 10),
                new Room(),
                new User()
        );
        String requestReservationJson = """
                {
                   "checkIn": "2000-01-01",
                   "checkOut": "2000-01-10",
                   "roomId" : 1
                }""";
        ReservationRequest correctRequest = new ReservationRequest(
                LocalDate.of(2000, 1, 1),
                LocalDate.of(2000, 1, 10),
                1L
        );

        when(reservationMapper.requestToModel(correctRequest))
                .thenReturn(defaultReservation);
        when(reservationService.save(defaultReservation))
                .thenReturn(defaultReservation);
        when(reservationMapper.modelToResponse(defaultReservation))
                .thenReturn(roomResponse);

        mockMvc.perform(post(createUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestReservationJson)
                )
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.checkIn").isString())
                .andExpect(jsonPath("$.checkOut").isString())
                .andExpect(jsonPath("$.roomId").isNumber())
                .andExpect(status().isCreated());

        verify(reservationMapper, times(1))
                .requestToModel(any());
        verify(reservationService, times(1))
                .save(any());
        verify(reservationMapper, times(1))
                .modelToResponse(any());
    }
}
