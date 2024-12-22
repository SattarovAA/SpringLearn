package com.rest.hotelbooking.service.impl.statistic.reciever;

import com.rest.hotelbooking.model.entity.User;
import com.rest.hotelbooking.model.entity.statistic.RegistrationEvent;
import com.rest.hotelbooking.repository.staticstic.RegistrationEventRepository;
import com.rest.hotelbooking.service.impl.UserServiceImpl;
import com.rest.hotelbooking.service.impl.statistic.receiver.RegistrationEventReceiverServiceImpl;
import com.rest.hotelbooking.service.impl.statistic.receiver.ReservationEventReceiverServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("RegistrationEventReceiverServiceImplTest Tests")
public class RegistrationEventReceiverServiceImplTest {
    RegistrationEventReceiverServiceImpl receiverService;
    @Mock
    private RegistrationEventRepository registrationEventRepository;
    private final int defaultPageSize = 10;

    @BeforeEach
    void setUp() {
        receiverService = new RegistrationEventReceiverServiceImpl(registrationEventRepository);
        try {
            Field PageSize = RegistrationEventReceiverServiceImpl.class
                    .getDeclaredField("defaultPageSize");
            PageSize.setAccessible(true);
            PageSize.setInt(receiverService, defaultPageSize);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("findAll test: get all registration event data.")
    void givenWhenGetAllThenListUser() {
        List<RegistrationEvent> userList = new ArrayList<>(List.of(
                new RegistrationEvent(),
                new RegistrationEvent()
        ));
        PageRequest pageRequest = PageRequest.of(0, 10);

        when(registrationEventRepository.findAll(pageRequest))
                .thenReturn(new PageImpl<>(userList));
        List<RegistrationEvent> actual = receiverService.findAll();

        assertEquals(userList.size(), actual.size());
        verify(registrationEventRepository, times(1))
                .findAll(pageRequest);
    }
}
