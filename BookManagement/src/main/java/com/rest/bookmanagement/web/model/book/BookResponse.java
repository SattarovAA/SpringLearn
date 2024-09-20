package com.rest.bookmanagement.web.model.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookResponse {
    private String title;
    private String author;
    private String content;
    private String categoryName;
}
