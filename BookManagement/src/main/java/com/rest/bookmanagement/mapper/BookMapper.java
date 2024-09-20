package com.rest.bookmanagement.mapper;

import com.rest.bookmanagement.model.Book;
import com.rest.bookmanagement.web.model.book.BookListResponse;
import com.rest.bookmanagement.web.model.book.BookRequest;
import com.rest.bookmanagement.web.model.book.BookResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {
    @Mapping(source = "bookRequest.categoryName", target = "category.name")
    Book requestToBook(BookRequest bookRequest);
    @Mapping(source = "bookRequest.categoryName", target = "category.name")
    @Mapping(source = "bookId", target = "id")
    Book requestToBook(Long bookId, BookRequest bookRequest);

    @Mapping(source = "category.name", target = "categoryName")
    BookResponse bookToResponse(Book book);

    List<BookResponse> bookListToBookResponseList(List<Book> books);

    default BookListResponse bookListToBookListResponse(List<Book> books) {
        BookListResponse response = new BookListResponse();
        response.setBooks(bookListToBookResponseList(books));
        return response;
    }
}
