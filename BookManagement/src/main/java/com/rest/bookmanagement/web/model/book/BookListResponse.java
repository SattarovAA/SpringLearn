package com.rest.bookmanagement.web.model.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookListResponse {
    List<BookResponse> books = new ArrayList<>();
}
