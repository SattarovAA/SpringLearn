package com.rest.hotelbooking.service.impl.statistic.receiver;

import com.rest.hotelbooking.model.statistic.ReservationEvent;
import com.rest.hotelbooking.repository.staticstic.ReservationEventRepository;
import com.rest.hotelbooking.service.statistic.receiver.ReservationEventReceiverService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReservationEventReceiverServiceImpl implements ReservationEventReceiverService {
    private final ReservationEventRepository reservationEventRepository;

    @Override
    public List<ReservationEvent> findAll() {
        return reservationEventRepository.findAll();
    }

    @Override
    public ReservationEvent save(ReservationEvent model) {
        return reservationEventRepository.save(model);
    }
}
