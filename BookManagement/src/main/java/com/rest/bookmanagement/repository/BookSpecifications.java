package com.rest.bookmanagement.repository;

import com.rest.bookmanagement.model.Book;
import com.rest.bookmanagement.model.Category;
import com.rest.bookmanagement.web.model.BookFilter;
import org.springframework.data.jpa.domain.Specification;

public interface BookSpecifications {

    static Specification<Book> withFilter(BookFilter bookFilter) {
        return Specification.where(byAuthor(bookFilter.getAuthor())).
                and(byCategoryName(bookFilter.getCategoryName()))
                .and(byTitle(bookFilter.getTitle()));
    }

    static Specification<Book> byCategoryName(String categoryName) {
        return ((root, query, criteriaBuilder) -> {
            if (categoryName == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get(Book.Fields.category).get(Category.Fields.name), categoryName);
        });
    }

    static Specification<Book> byAuthor(String author) {
        return ((root, query, criteriaBuilder) -> {
            if (author == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get(Book.Fields.author), author);
        });
    }
    static Specification<Book> byTitle(String title) {
        return ((root, query, criteriaBuilder) -> {
            if (title == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get(Book.Fields.title), title);
        });
    }
}
