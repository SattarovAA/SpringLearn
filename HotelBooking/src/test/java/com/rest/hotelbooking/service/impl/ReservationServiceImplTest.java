package com.rest.hotelbooking.service.impl;

import com.rest.hotelbooking.exception.EntityNotFoundException;
import com.rest.hotelbooking.model.entity.Reservation;
import com.rest.hotelbooking.model.entity.Room;
import com.rest.hotelbooking.model.entity.User;
import com.rest.hotelbooking.model.security.AppUserDetails;
import com.rest.hotelbooking.model.security.RoleType;
import com.rest.hotelbooking.repository.ReservationRepository;
import com.rest.hotelbooking.service.RoomService;
import com.rest.hotelbooking.service.UserService;
import com.rest.hotelbooking.service.statistic.sender.ReservationEventSenderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("RoomServiceImplTest Tests")
public class ReservationServiceImplTest {
    private ReservationServiceImpl reservationService;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private UserService userService;
    @Mock
    private RoomService roomService;
    @Mock
    private ReservationEventSenderService reservationEventSenderService;
    private final int defaultPageSize = 10;

    @BeforeEach
    void setUp() {
        reservationService = new ReservationServiceImpl(
                reservationRepository,
                userService,
                roomService,
                reservationEventSenderService);
        try {
            Field PageSize = ReservationServiceImpl.class
                    .getDeclaredField("defaultPageSize");
            PageSize.setAccessible(true);
            PageSize.setInt(reservationService, defaultPageSize);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("findAll test: get all reservations data.")
    void givenWhenGetAllThenListReservation() {
        List<Reservation> reservationList = new ArrayList<>(List.of(
                new Reservation(),
                new Reservation()
        ));
        PageRequest pageRequest = PageRequest.of(0, defaultPageSize);

        when(reservationRepository.findAll(pageRequest))
                .thenReturn(new PageImpl<>(reservationList));
        List<Reservation> actual = reservationService.findAll();

        assertEquals(reservationList.size(), actual.size());
        verify(reservationRepository, times(1))
                .findAll(pageRequest);
    }

    @Test
    @DisplayName("findById test: get reservation data by id.")
    void givenExistingIdWhenGetByIdThenReservation() {
        Long reservationId = 1L;
        Reservation defaultReservation = new Reservation(
                1L,
                LocalDate.of(2000, 1, 1),
                LocalDate.of(2000, 1, 10),
                new Room(),
                new User()
        );

        when(reservationRepository.findById(reservationId))
                .thenReturn(Optional.of(defaultReservation));

        Reservation actual = reservationService.findById(reservationId);

        assertEquals(defaultReservation, actual);
        verify(reservationRepository, times(1))
                .findById(any());
    }

    @Test
    @DisplayName("findById test: try to get reservation data by not existing id.")
    void givenNotExistingIdWhenGetByIdThenThrow() {
        Long reservationId = 1L;

        when(reservationRepository.findById(reservationId))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> reservationService.findById(reservationId),
                " index is incorrect."
        );
        verify(reservationRepository, times(1))
                .findById(any());
    }

    @Test
    @DisplayName("save test: send reservation data to repository.")
    void givenReservationWhenSendReservationToDbThenSavedReservation() {
        Long userPrincipalId = 10L;
        User defaultUser = new User(
                userPrincipalId,
                "username",
                "pass",
                "email",
                Collections.singleton(RoleType.ROLE_USER),
                Collections.emptyList()
        );
        Reservation reservationToSave = new Reservation(
                null,
                LocalDate.of(2000, 1, 1),
                LocalDate.of(2000, 1, 10),
                new Room(),
                defaultUser
        );
        AppUserDetails principal = new AppUserDetails(defaultUser);
        Authentication auth =
                new UsernamePasswordAuthenticationToken(
                        principal, "password", principal.getAuthorities()
                );

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        when(reservationRepository.save(reservationToSave))
                .thenReturn(reservationToSave);
        when(userService.findById(userPrincipalId))
                .thenReturn(defaultUser);
        when(roomService.findById(any()))
                .thenReturn(new Room());

        Reservation actual = reservationService.save(reservationToSave);

        assertEquals(reservationToSave, actual);
        verify(reservationRepository, times(1))
                .save(any());
        verify(userService, times(1))
                .findById(any());
        verify(roomService, times(1))
                .findById(any());
        verify(reservationEventSenderService, times(1))
                .send(reservationToSave);
    }

    @Test
    @DisplayName("update test: send reservation data to repository.")
    void givenReservationAndExistedIdWhenSendReservationToDbThenUpdatedReservation() {
        Long reservationId = 1L;
        Reservation reservationToUpdate = new Reservation(
                1L,
                LocalDate.of(2000, 1, 1),
                LocalDate.of(2000, 1, 10),
                new Room(),
                new User()
        );

        when(reservationRepository.findById(reservationId))
                .thenReturn(Optional.of(reservationToUpdate));
        when(reservationRepository.save(reservationToUpdate))
                .thenReturn(reservationToUpdate);
        when(userService.findById(any()))
                .thenReturn(new User());
        when(roomService.findById(any()))
                .thenReturn(new Room());

        Reservation actual = reservationService.update(reservationId, reservationToUpdate);

        assertEquals(reservationToUpdate, actual);
        verify(reservationRepository, times(1))
                .save(any());
        verify(reservationRepository, times(1))
                .findById(any());
        verify(userService, times(1))
                .findById(any());
        verify(roomService, times(1))
                .findById(any());
        verify(reservationEventSenderService, times(1))
                .send(any());
    }

    @Test
    @DisplayName("update test: try update with not existed reservation id.")
    void givenReservationAndNotExistedIdWhenSendReservationToDbThenUpdatedReservation() {
        Long notExistedReservationId = 1L;
        Reservation reservationToUpdate = new Reservation(
                null,
                LocalDate.of(2000, 1, 1),
                LocalDate.of(2000, 1, 10),
                new Room(),
                new User()
        );

        when(reservationRepository.findById(notExistedReservationId))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> reservationService.update(notExistedReservationId, reservationToUpdate),
                "ReservationId is incorrect."
        );
        verify(reservationRepository, times(0))
                .save(any());
        verify(reservationRepository, times(1))
                .findById(any());
    }

    @Test
    @DisplayName("delete test: delete reservation data message to repository.")
    void givenExistedReservationIdWhenSendReservationToDbThenUpdatedReservation() {
        Long existedReservationId = 1L;

        when(reservationRepository.findById(existedReservationId))
                .thenReturn(Optional.of(new Reservation()));
        reservationService.deleteById(existedReservationId);

        verify(reservationRepository, times(1))
                .findById(existedReservationId);
        verify(reservationRepository, times(1))
                .deleteById(existedReservationId);
    }

    @Test
    @DisplayName("delete test: delete reservation data message to repository.")
    void givenNotExistedReservationIdWhenSendReservationToDbThenUpdatedReservation() {
        Long notExistedReservationId = 1L;

        assertThrows(EntityNotFoundException.class,
                () -> reservationService.deleteById(notExistedReservationId),
                "ReservationId is incorrect."
        );
        verify(reservationRepository, times(1))
                .findById(notExistedReservationId);
        verify(reservationRepository, times(0))
                .deleteById(notExistedReservationId);
    }
}
