package com.rest.hotelbooking.service.impl;

import com.rest.hotelbooking.exception.DeleteEntityWithReferenceException;
import com.rest.hotelbooking.exception.EntityNotFoundException;
import com.rest.hotelbooking.model.Hotel;
import com.rest.hotelbooking.web.dto.hotel.mark.HotelMark;
import com.rest.hotelbooking.repository.HotelRepository;
import com.rest.hotelbooking.repository.HotelSpecifications;
import com.rest.hotelbooking.service.HotelService;
import com.rest.hotelbooking.web.dto.HotelFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.List;


@RequiredArgsConstructor
@Service
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    @Override
    public List<Hotel> findAll() {
        return hotelRepository.findAll();
    }

    @Override
    public List<Hotel> filterBy(HotelFilter filter) {
        return hotelRepository.findAll(HotelSpecifications.withFilter(filter),
                PageRequest.of(filter.pageNumber(), filter.pageSize())
        ).getContent();
    }

    @Override
    public Hotel findById(Long id) {
        return hotelRepository.findById(id).orElseThrow(
                EntityNotFoundException.create(
                        MessageFormat.format("Отель с id {0} не найдена!", id)
                )
        );
    }

    @Override
    public Hotel save(Hotel model) {
        return hotelRepository.save(model);
    }

    @Override
    public Hotel update(Long id, Hotel model) {
        model = enrich(model, id);
        return hotelRepository.save(model);
    }

    private Hotel enrich(Hotel model, Long modelId) {
        Hotel modelToUpdate = findById(modelId);

        return Hotel.builder()
                .id(modelId)
                .address(model.getAddress() == null
                        ? modelToUpdate.getAddress()
                        : model.getAddress()
                )
                .adTitle(model.getAdTitle() == null
                        ? modelToUpdate.getAdTitle()
                        : model.getAdTitle()
                )
                .city(model.getCity() == null
                        ? modelToUpdate.getCity()
                        : model.getCity()
                )
                .distanceFromCenter(model.getDistanceFromCenter() == null
                        ? modelToUpdate.getDistanceFromCenter()
                        : model.getDistanceFromCenter()
                )
                .name(model.getName() == null
                        ? modelToUpdate.getName()
                        : model.getName()
                )
                .numberOfRatings(model.getNumberOfRatings() == null
                        ? modelToUpdate.getNumberOfRatings()
                        : model.getNumberOfRatings()
                )
                .rating(model.getRating() == null
                        ? modelToUpdate.getRating()
                        : model.getRating()
                )
                .rooms(modelToUpdate.getRooms())
                .build();
    }

    @Override
    public void deleteById(Long id) {
        Hotel hotelToDelete = findById(id);
        checkRoomsReference(hotelToDelete);
        hotelRepository.deleteById(id);
    }

    private void checkRoomsReference(Hotel model) {
        if (!model.getRooms().isEmpty()) {
            throw new DeleteEntityWithReferenceException(
                    MessageFormat.format(
                            "Unable to delete hotel with id {0}. Hotel have {1} rooms",
                            model.getId(), model.getRooms().size())
            );
        }
    }

    public Hotel addNewMark(Long hotelId, HotelMark newMark) {
        final int SCALE = 4;

        Hotel hotel = findById(hotelId);
        BigDecimal oldRating = hotel.getRating();
        Long numberOfRating = hotel.getNumberOfRatings();

        BigDecimal totalRating = oldRating.multiply(BigDecimal.valueOf(numberOfRating));
        totalRating = totalRating.add(BigDecimal.valueOf(newMark.value()));
        numberOfRating++;

        totalRating = totalRating
                .divide(BigDecimal.valueOf(numberOfRating), SCALE, RoundingMode.CEILING);

        return update(hotelId, Hotel.builder()
                .rating(totalRating)
                .numberOfRatings(numberOfRating)
                .build());
    }
}
