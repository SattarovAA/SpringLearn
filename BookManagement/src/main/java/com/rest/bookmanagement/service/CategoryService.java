package com.rest.bookmanagement.service;

import com.rest.bookmanagement.model.Category;

import java.util.Optional;

public interface CategoryService extends CrudService<Category> {
    Optional<Category> findByName(String name);
}
