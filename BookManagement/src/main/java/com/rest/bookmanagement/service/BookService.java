package com.rest.bookmanagement.service;

import com.rest.bookmanagement.model.Book;
import com.rest.bookmanagement.web.model.BookFilter;

import java.util.List;

public interface BookService extends CrudService<Book>{
    Book getOneByFilter(BookFilter filter);

    List<Book> getAllByFilter(BookFilter filter);
    List<Book> getAllByCategoryName(String categoryName);
}
