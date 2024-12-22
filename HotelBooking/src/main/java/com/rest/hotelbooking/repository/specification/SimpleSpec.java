package com.rest.hotelbooking.repository.specification;

import org.springframework.data.jpa.domain.Specification;

/**
 * Default specification by fields for entity {@link T}.
 *
 * @param <T> entity for specifications.
 */
public class SimpleSpec<T> {
    /**
     * Entity with {@link Long} type field equals criterion.
     *
     * @param field     field name in entity {@link T}.
     * @param criterion searched value.
     * @return Specification with the required parameters.
     */
    public Specification<T> byLongField(String field, Long criterion) {
        return ((root, query, criteriaBuilder) -> {
            if (criterion == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get(field), criterion);
        });
    }

    /**
     * Entity with {@link N} type field in range min to max.
     *
     * @param field field name in entity {@link T}.
     * @param min   minimum value.
     * @param max   maximum value.
     * @param <N>   type of field.
     * @return Specification with the required parameters.
     */
    public <N extends Number & Comparable<? super N>> Specification<T> byNumberField(
            String field,
            N min,
            N max
    ) {
        return ((root, query, criteriaBuilder) -> {
            if (max == null && min == null) {
                return null;
            }
            if (min == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get(field), max);
            }
            if (max == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get(field), min);
            }
            return criteriaBuilder.between(root.get(field), min, max);
        });
    }

    /**
     * Entity with {@link String} type field equals criterion.
     *
     * @param field     field name in entity {@link T}.
     * @param criterion searched value.
     * @return Specification with the required parameters.
     */
    public Specification<T> byStringField(String field, String criterion) {
        return ((root, query, criteriaBuilder) -> {
            if (criterion == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get(field), criterion);
        });
    }
}
