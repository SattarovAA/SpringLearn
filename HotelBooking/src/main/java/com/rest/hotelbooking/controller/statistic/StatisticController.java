package com.rest.hotelbooking.controller.statistic;

import com.rest.hotelbooking.service.statistic.StatisticService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Statistic controller to get Csv register events and Csv reservation events.
 */
@Tag(name = "StatisticController",
        description = "Controller for working with statistics.")
@RequiredArgsConstructor
@RequestMapping("api/statistic")
@RestController
@Slf4j
public class StatisticController {
    /**
     * General service for working with statistics.
     */
    private final StatisticService statisticService;

    /**
     * Get register events in csv format.
     *
     * @param response HttpServletResponse.
     */
    @Operation(
            summary = "Get all register events.",
            description = "Only with admin access.",
            tags = {"statistic", "csv", "register-event"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/csv")
            }),
            @ApiResponse(responseCode = "401"),
            @ApiResponse(responseCode = "403"),
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/csv/register_event")
    public void getCsvRegisterEvents(HttpServletResponse response) {
        statisticService.generateRegistrationEventCsv(response);
    }

    /**
     * Get reservation events in csv format.
     *
     * @param response HttpServletResponse.
     */
    @Operation(
            summary = "Get all reservation events.",
            description = "Only with admin access.",
            tags = {"statistic", "csv", "reservation - event"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/csv")
            }),
            @ApiResponse(responseCode = "401"),
            @ApiResponse(responseCode = "403"),
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/csv/reservation_event")
    public void getCsvReservationEvents(HttpServletResponse response) {
        statisticService.generateReservationEventCsv(response);
    }
}
