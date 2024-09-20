package com.rest.bookmanagement.web.controller;

import com.rest.bookmanagement.mapper.BookMapper;
import com.rest.bookmanagement.service.BookService;
import com.rest.bookmanagement.web.model.BookFilter;
import com.rest.bookmanagement.web.model.book.BookListResponse;
import com.rest.bookmanagement.web.model.book.BookRequest;
import com.rest.bookmanagement.web.model.book.BookResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/book")
public class BookController {
    private final BookService bookService;
    private final BookMapper bookMapper;

    @GetMapping()
    public ResponseEntity<BookListResponse> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookMapper
                        .bookListToBookListResponse(bookService.findAll())
                );
    }

    @PostMapping()
    public ResponseEntity<BookResponse> create(@RequestBody @Valid BookRequest newsRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookMapper.bookToResponse(
                        bookService.save(
                                bookMapper.requestToBook(newsRequest)
                        )
                ));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<BookResponse> update(@PathVariable("id") Long id,
                                               @RequestBody @Valid BookRequest newsRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookMapper.bookToResponse(
                        bookService.update(
                                id, bookMapper.requestToBook(id, newsRequest)
                        )
                ));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        bookService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<BookListResponse> getAllByCategory(@PathVariable("categoryName") String categoryName) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookMapper.bookListToBookListResponse(
                                bookService.getAllByCategoryName(categoryName)
                        )
                );
    }

    @GetMapping("/filter/one")
    public ResponseEntity<BookResponse> getOneFilterBy(BookFilter filter) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookMapper.bookToResponse(
                                bookService.getOneByFilter(filter)
                        )
                );
    }
}
