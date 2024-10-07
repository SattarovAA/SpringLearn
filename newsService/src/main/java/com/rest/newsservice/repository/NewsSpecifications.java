package com.rest.newsservice.repository;

import com.rest.newsservice.model.Category;
import com.rest.newsservice.model.News;
import com.rest.newsservice.model.User;
import com.rest.newsservice.web.model.NewsFilter;
import org.springframework.data.jpa.domain.Specification;

public interface NewsSpecifications {
    static Specification<News> withFilter(NewsFilter newsFilter) {
        return Specification.where(byUserName(newsFilter.getUserName())).
                and(byCategoryName(newsFilter.getCategoryName()));
    }

    static Specification<News> byCategoryName(String categoryName) {
        return ((root, query, criteriaBuilder) -> {
            if (categoryName == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get(News.Fields.category).get(Category.Fields.name), categoryName);
        });
    }

    static Specification<News> byUserName(String userName) {
        return ((root, query, criteriaBuilder) -> {
            if (userName == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get(News.Fields.user).get(User.Fields.username), userName);
        });
    }
}
