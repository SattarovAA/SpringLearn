package com.rest.newsservice.mapper;

import com.rest.newsservice.model.Category;
import com.rest.newsservice.web.model.category.CategoryListResponse;
import com.rest.newsservice.web.model.category.CategoryRequest;
import com.rest.newsservice.web.model.category.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
    Category requestToCategory(CategoryRequest categoryRequest);

    @Mapping(source = "categoryId", target = "id")
    Category requestToCategory(Long categoryId, CategoryRequest categoryRequest);

    CategoryResponse categoryToResponse(Category category);

    List<CategoryResponse> categoryListToCategoryResponseList(List<Category> categories);

    default CategoryListResponse categoryListToCategoryListResponse(List<Category> categories) {
        CategoryListResponse response = new CategoryListResponse();
        response.setCategories(categoryListToCategoryResponseList(categories));
        return response;
    }
}
