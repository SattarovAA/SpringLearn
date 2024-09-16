package com.rest.newsservice.service.impl;

import com.rest.newsservice.exception.DuplicateKeyException;
import com.rest.newsservice.exception.EntityNotFoundException;
import com.rest.newsservice.model.Category;
import com.rest.newsservice.repository.CategoryRepository;
import com.rest.newsservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@RequiredArgsConstructor
@Service
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
        checkDuplicateName(model.getName());
        return categoryRepository.save(model);
    }

    @Override
    public Category update(Long id, Category model) {
        findById(id);
        checkDuplicateName(model.getName());

        model.setId(id);
        return categoryRepository.save(model);
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        categoryRepository.deleteById(id);
    }

    public Category findByName(String name) {
        return categoryRepository.findByName(name).orElseThrow(
                EntityNotFoundException.create(
                        MessageFormat.format(
                                "Категория с name {0} не найдена!",
                                name
                        )
                )
        );
    }

    private void checkDuplicateName(String name) {
        if (categoryRepository.findByName(name).isPresent()) {
            throw new DuplicateKeyException(MessageFormat.format(
                    "Категория с именем {0} уже существует", name
            ));
        }
    }
}
