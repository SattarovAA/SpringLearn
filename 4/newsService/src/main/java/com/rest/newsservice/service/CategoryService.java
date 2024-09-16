package com.rest.newsservice.service;

import com.rest.newsservice.model.Category;

public interface CategoryService extends CrudService<Category> {
    Category findByName(String name);
}
