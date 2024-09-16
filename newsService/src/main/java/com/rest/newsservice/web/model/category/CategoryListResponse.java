package com.rest.newsservice.web.model.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryListResponse {
    List<CategoryResponse> categories = new ArrayList<>();
}
