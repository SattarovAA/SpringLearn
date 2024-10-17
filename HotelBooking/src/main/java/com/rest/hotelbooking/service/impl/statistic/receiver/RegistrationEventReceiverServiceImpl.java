package com.rest.hotelbooking.service.impl.statistic.receiver;

import com.rest.hotelbooking.model.statistic.RegistrationEvent;
import com.rest.hotelbooking.repository.staticstic.RegistrationEventRepository;
import com.rest.hotelbooking.service.statistic.receiver.RegistrationEventReceiverService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RegistrationEventReceiverServiceImpl implements RegistrationEventReceiverService {
    private final RegistrationEventRepository registrationEventRepository;

    @Override
    public List<RegistrationEvent> findAll() {
        return registrationEventRepository.findAll();
    }

    @Override
    public RegistrationEvent save(RegistrationEvent model) {
        return registrationEventRepository.save(model);
    }
}
