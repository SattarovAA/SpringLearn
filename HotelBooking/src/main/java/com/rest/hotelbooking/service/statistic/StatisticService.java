package com.rest.hotelbooking.service.statistic;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface StatisticService {

    void generateReservationEventCsv(HttpServletResponse response) throws IOException;

    void generateRegistrationEventCsv(HttpServletResponse response) throws IOException;
}
