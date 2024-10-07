package com.rest.newsservice.aop;

import com.rest.newsservice.exception.AccessException;
import com.rest.newsservice.exception.InnerException;
import com.rest.newsservice.model.security.AppUserDetails;
import com.rest.newsservice.model.security.RoleType;
import com.rest.newsservice.service.CommentService;
import com.rest.newsservice.service.NewsService;
import com.rest.newsservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Arrays;
import java.util.Map;

@Aspect
@RequiredArgsConstructor
@Component
@Slf4j
public class CheckUserIdAspect {
    private final UserService userService;
    private final NewsService newsService;
    private final CommentService commentService;

    @Before(value = "@annotation(param)")
    public void beforeCheckUserIdPrivacy(CheckUserIdPrivacy param) {
        if (isAuthoritiesHasAnyRole(param.alwaysAccessRoles())) {
            return;
        }

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request =
                ((ServletRequestAttributes) requestAttributes).getRequest();
        Map<String, String> pathVariable =
                (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Long pathId = Long.valueOf(pathVariable.get("id"));
        Long ownerUserId;
        switch (param.entityType()) {
            case USER -> ownerUserId = userService.findById(pathId).getId();
            case NEWS -> ownerUserId = newsService.findById(pathId).getUser().getId();
            case COMMENT -> ownerUserId = commentService.findById(pathId).getUser().getId();
            case NOT_FOUND -> throw new InnerException("Внутренняя ошибка entityType:NOT_FOUND");
            default -> throw new InnerException("Внутренняя ошибка entityType:Необработанный тип");
        }
        Long currenUserId = getCurrentUserId();

        if (!currenUserId.equals(ownerUserId)) {
            throw new AccessException("Нет доступа к этому для текущего пользователя");
        }
    }

    private Long getCurrentUserId() {
        AppUserDetails userDetails =(AppUserDetails) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();
        return userDetails.getUserId();
    }

    private boolean isAuthoritiesHasAnyRole(RoleType[] roles) {
        var authorities =
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        return Arrays.stream(roles).anyMatch(e ->
                authorities.contains(new SimpleGrantedAuthority(e.toString()))
        );
    }
}
