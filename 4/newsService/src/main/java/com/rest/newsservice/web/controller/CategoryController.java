package com.rest.newsservice.web.controller;

import com.rest.newsservice.mapper.CategoryMapper;
import com.rest.newsservice.service.CategoryService;
import com.rest.newsservice.web.model.category.CategoryListResponse;
import com.rest.newsservice.web.model.category.CategoryRequest;
import com.rest.newsservice.web.model.category.CategoryResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;


    @GetMapping()
    public ResponseEntity<CategoryListResponse> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(categoryMapper.categoryListToCategoryListResponse(
                        categoryService.findAll()
                ));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CategoryResponse> getById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(categoryMapper
                        .categoryToResponse(categoryService.findById(id))
                );
    }

    @PostMapping()
    public ResponseEntity<CategoryResponse> create(@RequestBody @Valid CategoryRequest categoryRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryMapper.categoryToResponse(
                        categoryService.save(
                                categoryMapper.requestToCategory(categoryRequest)
                        ))
                );
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable("id") Long id,
                                                   @RequestBody @Valid CategoryRequest categoryRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(categoryMapper.categoryToResponse(
                        categoryService.update(
                                id,
                                categoryMapper.requestToCategory(id, categoryRequest)
                        ))
                );
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
