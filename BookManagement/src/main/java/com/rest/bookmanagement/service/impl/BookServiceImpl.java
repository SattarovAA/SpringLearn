package com.rest.bookmanagement.service.impl;

import com.rest.bookmanagement.config.properties.AppCacheProperties;
import com.rest.bookmanagement.exception.EntityNotFoundException;
import com.rest.bookmanagement.model.Book;
import com.rest.bookmanagement.model.Category;
import com.rest.bookmanagement.repository.BookRepository;
import com.rest.bookmanagement.repository.BookSpecifications;
import com.rest.bookmanagement.service.BookService;
import com.rest.bookmanagement.web.model.BookFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@CacheConfig(cacheManager = "redisCacheManager")
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final CategoryServiceImpl categoryService;

    @Override
    @Cacheable(AppCacheProperties.CacheNames.DB_BOOKS)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(
                EntityNotFoundException.create(
                        MessageFormat.format("Категория с id {0} не найдена!", id)
                )
        );
    }

    @Override
    @CacheEvict(value = AppCacheProperties.CacheNames.DB_BOOKS, allEntries = true)
    public Book save(Book model) {
        Optional<Category> bookCategory = categoryService.findByName(model.getCategory().getName());

        if (bookCategory.isPresent()) {
            model.setCategory(bookCategory.get());
        } else {
            model.setCategory(categoryService.save(model.getCategory()));
        }
        return bookRepository.save(model);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = AppCacheProperties.CacheNames.DB_BOOKS, allEntries = true),
            @CacheEvict(value = AppCacheProperties.CacheNames.BOOK_BY_CATEGORY_NAME, allEntries = true),
            @CacheEvict(value = AppCacheProperties.CacheNames.ONE_BY_FILTER_CACHE, allEntries = true)
    })
    public Book update(Long id, Book model) {
        findById(id);
        Optional<Category> bookCategory = categoryService.findByName(model.getCategory().getName());

        if (bookCategory.isEmpty()){
            throw new EntityNotFoundException(MessageFormat.format(
                                        "Категория с name {0} не найдена!",
                                        model.getCategory().getName()
                                ));
        }

        model.setCategory(bookCategory.get());
        model.setId(id);
        return bookRepository.save(model);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = AppCacheProperties.CacheNames.DB_BOOKS, allEntries = true),
            @CacheEvict(value = AppCacheProperties.CacheNames.BOOK_BY_CATEGORY_NAME, allEntries = true),
            @CacheEvict(value = AppCacheProperties.CacheNames.ONE_BY_FILTER_CACHE, allEntries = true)
    })
    public void deleteById(Long id) {
        findById(id);
        bookRepository.deleteById(id);
    }

    @Override
    @Cacheable(AppCacheProperties.CacheNames.ONE_BY_FILTER_CACHE)
    public Book getOneByFilter(BookFilter filter) {
        List<Book> books = getAllByFilter(filter);
        if (books.isEmpty()) {
            throw new EntityNotFoundException("Книга с заданнами параметрами не найдена!");
        }
        return books.get(0);
    }

    @Override
    public List<Book> getAllByFilter(BookFilter filter) {
        return bookRepository.findAll(BookSpecifications.withFilter(filter));
    }

    @Override
    @Cacheable(AppCacheProperties.CacheNames.BOOK_BY_CATEGORY_NAME)
    public List<Book> getAllByCategoryName(String categoryName) {
        BookFilter filter = new BookFilter();
        filter.setCategoryName(categoryName);
        getAllByFilter(filter);
        return bookRepository.getAllByCategory_Name(categoryName);
    }
}
