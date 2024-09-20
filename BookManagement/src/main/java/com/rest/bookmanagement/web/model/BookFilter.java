package com.rest.bookmanagement.web.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookFilter {
    private String categoryName;
    private String title;
    private String author;
}
