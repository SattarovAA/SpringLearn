package com.rest.hotelbooking.service.impl.statistic.reciever;

import com.rest.hotelbooking.model.entity.statistic.RegistrationEvent;
import com.rest.hotelbooking.model.entity.statistic.ReservationEvent;
import com.rest.hotelbooking.repository.staticstic.ReservationEventRepository;
import com.rest.hotelbooking.service.impl.HotelServiceImpl;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ReservationEventReceiverServiceImplTest Tests")
public class ReservationEventReceiverServiceImplTest {
    ReservationEventReceiverServiceImpl receiverService;
    @Mock
    private ReservationEventRepository reservationEventRepository;
    private final int defaultPageSize = 10;

    @BeforeEach
    void setUp() {
        receiverService = new ReservationEventReceiverServiceImpl(reservationEventRepository);
        try {
            Field PageSize = ReservationEventReceiverServiceImpl.class
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
        List<ReservationEvent> userList = new ArrayList<>(List.of(
                new ReservationEvent(),
                new ReservationEvent()
        ));
        PageRequest pageRequest = PageRequest.of(0, 10);

        when(reservationEventRepository.findAll(pageRequest))
                .thenReturn(new PageImpl<>(userList));
        List<ReservationEvent> actual = receiverService.findAll();

        assertEquals(userList.size(), actual.size());
        verify(reservationEventRepository, times(1))
                .findAll(pageRequest);
    }
}
