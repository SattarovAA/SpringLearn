package com.rest.hotelbooking.service.impl.statistic.receiver;

import com.rest.hotelbooking.model.entity.statistic.RegistrationEvent;
import com.rest.hotelbooking.repository.staticstic.RegistrationEventRepository;
import com.rest.hotelbooking.service.statistic.receiver.RegistrationEventReceiverService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for working with {@link RegistrationEvent}.
 */
@RequiredArgsConstructor
@Service
public class RegistrationEventReceiverServiceImpl
        implements RegistrationEventReceiverService {
    /**
     * {@link RegistrationEvent} repository.
     */
    private final RegistrationEventRepository registrationEventRepository;
    /**
     * Default page size.
     *
     * @see #findAll()
     */
    @Value("${app.service.registrationEvent.defaultPageSize}")
    private int defaultPageSize;
    /**
     * Default page number.
     *
     * @see #findAll()
     */
    @Value("${app.service.registrationEvent.defaultPageNumber}")
    private int defaultPageNumber;


    /**
     * {@link PageRequest} of type {@link RegistrationEvent} with
     * {@link #defaultPageNumber} and {@link #defaultPageSize} parameters.
     *
     * @return list of type {@link RegistrationEvent}.
     */
    @Override
    public List<RegistrationEvent> findAll() {
        return registrationEventRepository.findAll(
                PageRequest.of(defaultPageNumber, defaultPageSize)
        ).getContent();
    }

    @Override
    public RegistrationEvent save(RegistrationEvent model) {
        return registrationEventRepository.save(model);
    }
}
