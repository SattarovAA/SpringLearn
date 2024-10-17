package com.rest.hotelbooking.web.controller.statistic;

import com.rest.hotelbooking.service.statistic.StatisticService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("api/statistic")
@RestController
public class StatisticController {
    private final StatisticService statisticService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/csv/register_event")
    public void getCsvRegisterEvents(HttpServletResponse response) throws IOException {
        statisticService.generateRegistrationEventCsv(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/csv/reservation_event")
    public void getCsvReservationEvents(HttpServletResponse response) throws IOException {
        statisticService.generateReservationEventCsv(response);
    }
}
