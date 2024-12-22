package com.rest.hotelbooking.service.impl;

import com.rest.hotelbooking.exception.DeleteEntityWithReferenceException;
import com.rest.hotelbooking.exception.EntityNotFoundException;
import com.rest.hotelbooking.model.dto.hotel.HotelFilter;
import com.rest.hotelbooking.model.dto.hotel.mark.HotelMark;
import com.rest.hotelbooking.model.entity.Hotel;
import com.rest.hotelbooking.repository.HotelRepository;
import com.rest.hotelbooking.repository.specification.HotelSpecifications;
import com.rest.hotelbooking.service.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.List;

/**
 * Default service for working with entity {@link Hotel}.
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class HotelServiceImpl implements HotelService {
    /**
     * {@link Hotel}  repository for working with entity {@link Hotel}.
     */
    private final HotelRepository hotelRepository;
    /**
     * Default page size.
     *
     * @see #findAll()
     */
    @Value("${app.service.hotel.defaultPageSize}")
    private int defaultPageSize;
    /**
     * Default page number.
     *
     * @see #findAll()
     */
    @Value("${app.service.hotel.defaultPageNumber}")
    private int defaultPageNumber;

    /**
     * {@link PageRequest} of type {@link Hotel} with
     * {@link #defaultPageNumber} and {@link #defaultPageSize} parameters.
     *
     * @return list of type {@link Hotel}.
     */
    @Override
    public List<Hotel> findAll() {
        log.info("Try to get all hotels without filter.");
        return hotelRepository.findAll(
                PageRequest.of(defaultPageNumber, defaultPageSize)
        ).getContent();
    }

    /**
     * Find all Objects of type {@link Hotel} with filter parameters.
     *
     * @param filter {@link HotelFilter} with the required parameters.
     * @return all objects of type {@link Hotel} by filter.
     */
    @Override
    public List<Hotel> filterBy(HotelFilter filter) {
        log.info("Try to get all hotels with filter.");
        return hotelRepository.findAll(HotelSpecifications.withFilter(filter),
                        PageRequest.of(filter.pageNumber(), filter.pageSize())
                )
                .getContent();
    }

    /**
     * Get a {@link Hotel} object by specifying its id.
     *
     * @param id id searched {@link Hotel}.
     * @return object of type {@link Hotel} with searched id.
     */
    @Override
    public Hotel findById(Long id) {
        log.info("Find hotel with id {}.", id);
        return hotelRepository.findById(id).orElseThrow(
                EntityNotFoundException.create(
                        MessageFormat.format(
                                "Hotel with id {0} not found!",
                                id
                        )
                )
        );
    }

    /**
     * Save object model of type {@link Hotel}.
     *
     * @param model object of type {@link Hotel} to save.
     * @return object of type {@link Hotel} that was saved.
     */
    @Override
    public Hotel save(Hotel model) {
        log.warn("Try to create new hotel.");
        return hotelRepository.save(model);
    }

    /**
     * Update object model of type {@link Hotel} with T.id equals id.
     *
     * @param id    id of the object to be updated.
     * @param model object of type {@link Hotel} to update.
     * @return object of type {@link Hotel} that was updated.
     */
    @Override
    public Hotel update(Long id, Hotel model) {
        log.warn("Try to update hotel with id: " + id);
        model = enrich(model, id);
        return hotelRepository.save(model);
    }

    /**
     * Enrich model to full version.
     * If the model has no field values, then the values are taken
     * from a previously existing entity with the same id.
     *
     * @param model   {@link Hotel}  with partially updated fields.
     * @param modelId hotel id to update {@link Hotel} .
     * @return Updated {@link Hotel}.
     */
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

    /**
     * Delete object with Hotel.id equals id from database.
     *
     * @param id id of the object to be deleted.
     */
    @Override
    public void deleteById(Long id) {
        log.warn("Try to delete hotel with id {}.", id);
        Hotel hotelToDelete = findById(id);
        checkRoomsReference(hotelToDelete);
        hotelRepository.deleteById(id);
    }

    /**
     * Check Room Reference for {@link Hotel}.
     *
     * @param model {@link Hotel} to check.
     * @throws DeleteEntityWithReferenceException if {@link Hotel#getRooms()} not empty.
     */
    private void checkRoomsReference(Hotel model) {
        if (!model.getRooms().isEmpty()) {
            throw new DeleteEntityWithReferenceException(
                    MessageFormat.format(
                            "Unable to delete hotel with id {0}. Hotel have {1} rooms.",
                            model.getId(),
                            model.getRooms().size()
                    )
            );
        }
    }

    /**
     * Add new Mark to the hotel and update
     * hotel rating and hotel numberOfRating.<br>
     * Default scale mode = {@link RoundingMode}.CEILING.
     * Default scale = 4.
     * <br>
     * TODO @Async?
     *
     * @param hotelId hotel id to update rating.
     * @param newMark new mark.
     * @return {@link Hotel} with updated mark.
     */
    @Override
    public Hotel addNewMark(Long hotelId, HotelMark newMark) {
        log.info("add mark value {} to the hotel with id {}.",
                newMark.value(),
                hotelId
        );
        //scale of the BigDecimal quotient to be returned.
        final int scale = 4;
        Hotel hotel = findById(hotelId);

        BigDecimal oldRating = hotel.getRating();
        BigDecimal amount = BigDecimal.valueOf(hotel.getNumberOfRatings());

        BigDecimal totalRating = oldRating.multiply(amount);

        BigDecimal markValue = BigDecimal.valueOf(newMark.value());

        totalRating = totalRating.add(markValue);
        amount = amount.add(BigDecimal.ONE);

        totalRating = totalRating.divide(amount, scale, RoundingMode.CEILING);

        return update(hotelId,
                Hotel.builder()
                        .rating(totalRating)
                        .numberOfRatings(amount.longValue())
                        .build()
        );
    }
}
