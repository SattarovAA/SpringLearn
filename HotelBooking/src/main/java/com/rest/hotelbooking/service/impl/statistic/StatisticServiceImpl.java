package com.rest.hotelbooking.service.impl.statistic;

import com.opencsv.CSVWriter;
import com.rest.hotelbooking.model.statistic.RegistrationEvent;
import com.rest.hotelbooking.model.statistic.ReservationEvent;
import com.rest.hotelbooking.service.statistic.receiver.RegistrationEventReceiverService;
import com.rest.hotelbooking.service.statistic.receiver.ReservationEventReceiverService;
import com.rest.hotelbooking.service.statistic.StatisticService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StatisticServiceImpl implements StatisticService {
    private final RegistrationEventReceiverService registrationEventReceiverService;
    private final ReservationEventReceiverService reservationEventReceiverService;

    @Override
    public void generateReservationEventCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; file=event.csv");

        CSVWriter writer = new CSVWriter(response.getWriter());
        writeAllReservationEvent(writer);

        writer.close();
    }

    private void writeAllReservationEvent(CSVWriter writer) {
        List<ReservationEvent> eventList = reservationEventReceiverService.findAll();
        writer.writeNext(new String[]{"userId", "checkIn", "checkOut"});

        for (ReservationEvent event : eventList) {
            writer.writeNext(new String[]{
                    event.getUserId().toString(),
                    event.getCheckIn().toString(),
                    event.getCheckOut().toString()
            });
        }
    }

    @Override
    public void generateRegistrationEventCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; file=event.csv");

        CSVWriter writer = new CSVWriter(response.getWriter());
        writeAllRegistrationEvent(writer);

        writer.close();
    }

    private void writeAllRegistrationEvent(CSVWriter writer) {
        List<RegistrationEvent> eventList = registrationEventReceiverService.findAll();
        writer.writeNext(new String[]{"userId"});
        for (RegistrationEvent event : eventList) {
            writer.writeNext(new String[]{
                    event.getUserId().toString()
            });
        }
    }
}
