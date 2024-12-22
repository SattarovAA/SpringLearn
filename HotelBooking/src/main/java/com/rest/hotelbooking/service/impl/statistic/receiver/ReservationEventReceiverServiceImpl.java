package com.rest.hotelbooking.service.impl.statistic.receiver;

import com.rest.hotelbooking.model.entity.statistic.ReservationEvent;
import com.rest.hotelbooking.repository.staticstic.ReservationEventRepository;
import com.rest.hotelbooking.service.statistic.receiver.ReservationEventReceiverService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for working with {@link ReservationEvent}.
 */
@RequiredArgsConstructor
@Service
public class ReservationEventReceiverServiceImpl
        implements ReservationEventReceiverService {
    /**
     * {@link ReservationEvent} Repository.
     */
    private final ReservationEventRepository reservationEventRepository;
    /**
     * Default page size.
     *
     * @see #findAll()
     */
    @Value("${app.service.reservationEvent.defaultPageSize}")
    private int defaultPageSize;
    /**
     * Default page number.
     *
     * @see #findAll()
     */
    @Value("${app.service.reservationEvent.defaultPageNumber}")
    private int defaultPageNumber;

    /**
     * {@link PageRequest} of type {@link ReservationEvent} with
     * {@link #defaultPageNumber} and {@link #defaultPageSize} parameters.
     *
     * @return list of type {@link ReservationEvent}.
     */
    @Override
    public List<ReservationEvent> findAll() {
        return reservationEventRepository.findAll(
                PageRequest.of(defaultPageNumber, defaultPageSize)
        ).getContent();
    }

    @Override
    public ReservationEvent save(ReservationEvent model) {
        return reservationEventRepository.save(model);
    }
}
