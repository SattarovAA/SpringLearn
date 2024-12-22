package com.rest.hotelbooking.service.impl.statistic;

import com.opencsv.CSVWriter;
import com.rest.hotelbooking.model.entity.statistic.RegistrationEvent;
import com.rest.hotelbooking.model.entity.statistic.ReservationEvent;
import com.rest.hotelbooking.service.statistic.receiver.RegistrationEventReceiverService;
import com.rest.hotelbooking.service.statistic.receiver.ReservationEventReceiverService;
import com.rest.hotelbooking.service.statistic.StatisticService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class StatisticServiceImpl implements StatisticService {
    /**
     * Service interface for working with RegistrationEvent.
     *
     * @see #writeAllRegistrationEvent(CSVWriter)
     */
    private final RegistrationEventReceiverService registrationEventReceiverService;
    /**
     * Service interface for working with ReservationEvent.
     *
     * @see #writeAllReservationEvent(CSVWriter)
     */
    private final ReservationEventReceiverService reservationEventReceiverService;

    /**
     * generate reservation event csv in {@link HttpServletResponse} response.
     *
     * @param response HttpServletResponse for generate Csv.
     */
    @Override
    public void generateReservationEventCsv(HttpServletResponse response) {
        log.info("Try to generate reservations events csv.");
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition",
                "attachment; file=event.csv"
        );

        try (CSVWriter writer = new CSVWriter(response.getWriter())) {
            writeAllReservationEvent(writer);
        } catch (IOException e) {
            e.getLocalizedMessage();
        }
    }

    private void writeAllReservationEvent(CSVWriter writer) {
        List<ReservationEvent> eventList =
                reservationEventReceiverService.findAll();
        writer.writeNext(new String[]{"userId", "checkIn", "checkOut"});

        for (ReservationEvent event : eventList) {
            writer.writeNext(
                    new String[]{
                            event.getUserId().toString(),
                            event.getCheckIn().toString(),
                            event.getCheckOut().toString()
                    }
            );
        }
    }

    /**
     * generate registration event csv in {@link HttpServletResponse} response.
     *
     * @param response HttpServletResponse for generate Csv.
     */
    @Override
    public void generateRegistrationEventCsv(HttpServletResponse response) {
        log.info("Try to generate registrations events csv.");
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition",
                "attachment; file=event.csv"
        );

        try (CSVWriter writer = new CSVWriter(response.getWriter())) {
            writeAllRegistrationEvent(writer);
        } catch (IOException e) {
            e.getLocalizedMessage();
        }
    }

    private void writeAllRegistrationEvent(CSVWriter writer) {
        List<RegistrationEvent> eventList =
                registrationEventReceiverService.findAll();
        writer.writeNext(new String[]{"userId"});
        for (RegistrationEvent event : eventList) {
            writer.writeNext(
                    new String[]{
                            event.getUserId().toString()
                    }
            );
        }
    }
}
