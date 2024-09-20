package com.rest.bookmanagement.service;

import java.util.List;

public interface CrudService<T> {
    List<T> findAll();

    T findById(Long id);

    T save(T model);

    T update(Long id, T model);

    void deleteById(Long id);
}
