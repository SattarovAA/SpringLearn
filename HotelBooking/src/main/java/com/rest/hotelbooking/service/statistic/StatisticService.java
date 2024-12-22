package com.rest.hotelbooking.service.statistic;

import jakarta.servlet.http.HttpServletResponse;

/**
 * General service interface for working with statistics.
 */
public interface StatisticService {
    /**
     * Generate ReservationEvent csv.
     * TODO rework?!
     *
     * @param response HttpServletResponse for generate Csv.
     */
    void generateReservationEventCsv(HttpServletResponse response);

    /**
     * Generate RegistrationEvent csv.
     *
     * @param response HttpServletResponse for generate Csv.
     */
    void generateRegistrationEventCsv(HttpServletResponse response);
}
