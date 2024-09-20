package com.rest.bookmanagement.service.impl;

import com.rest.bookmanagement.exception.EntityNotFoundException;
import com.rest.bookmanagement.model.Category;
import com.rest.bookmanagement.repository.CategoryRepository;
import com.rest.bookmanagement.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@CacheConfig(cacheManager = "redisCacheManager")
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                EntityNotFoundException.create(
                        MessageFormat.format("Категория с id {0} не найдена!", id)
                )
        );
    }

    @Override
    public Category save(Category model) {
        return categoryRepository.save(model);
    }

    @Override
    public Category update(Long id, Category model) {
        findById(id);
        model.setId(id);
        return categoryRepository.save(model);
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        categoryRepository.deleteById(id);
    }

    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }
}
