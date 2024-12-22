package com.rest.hotelbooking.controller.statistic;

import com.rest.hotelbooking.service.statistic.StatisticService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("AppControllerTest tests")
public class StatisticControllerTest {
    private final static String urlTemplate = "/api/statistic";
    @MockitoBean
    private StatisticService statisticService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithAnonymousUser
    @DisplayName("getCsvReservationEvents test: get csv reservation events " +
            "from anonymous user.")
    void givenAnonymousWhenGetCsvReservationEventsUrlThenStatusUnauthorized()
            throws Exception {
        String reservationEventUrl = urlTemplate + "/csv/reservation_event";
        mockMvc.perform(get(reservationEventUrl))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser()
    @DisplayName("getCsvReservationEvents test: get csv reservation events " +
            "from simple user.")
    void givenUserPrincipalWhenGetCsvReservationEventsUrlThenForbidden()
            throws Exception {
        String reservationEventUrl = urlTemplate + "/csv/reservation_event";
        mockMvc.perform(get(reservationEventUrl))
                .andExpect(jsonPath("$.message").isString())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("getCsvReservationEvents test: get csv reservation events " +
            "from admin user.")
    void givenAdminPrincipalWhenGetCsvReservationEventsUrlThenCSVResponse()
            throws Exception {
        String reservationEventUrl = urlTemplate + "/csv/reservation_event";

        mockMvc.perform(get(reservationEventUrl))
                .andExpect(status().isOk());

        verify(statisticService, times(1))
                .generateReservationEventCsv(any());
    }
    @Test
    @WithAnonymousUser
    @DisplayName("getCsvRegistrationEvents test: get csv registration events " +
            "from anonymous user.")
    void givenAnonymousWhenGetCsvRegistrationEventsUrlThenStatusUnauthorized()
            throws Exception {
        String registrationEventUrl = urlTemplate + "/csv/register_event";
        mockMvc.perform(get(registrationEventUrl))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser()
    @DisplayName("getCsvRegistrationEvents test: get csv registration events " +
            "from simple user.")
    void givenUserPrincipalWhenGetCsvRegistrationEventsUrlThenForbidden()
            throws Exception {
        String registrationEventUrl = urlTemplate + "/csv/register_event";
        mockMvc.perform(get(registrationEventUrl))
                .andExpect(jsonPath("$.message").isString())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("getCsvRegistrationEvents test: get csv registration events " +
            "from admin user.")
    void givenAdminPrincipalWhenGetCsvRegistrationEventsUrlThenCSVResponse()
            throws Exception {
        String registrationEventUrl = urlTemplate + "/csv/register_event";

        mockMvc.perform(get(registrationEventUrl))
                .andExpect(status().isOk());

        verify(statisticService, times(1))
                .generateRegistrationEventCsv(any());
    }
}
