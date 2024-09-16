package com.rest.newsservice.aop;

import com.rest.newsservice.exception.AccessException;
import com.rest.newsservice.exception.InnerException;
import com.rest.newsservice.web.filter.scopes.SessionHolder;
import com.rest.newsservice.service.CommentService;
import com.rest.newsservice.service.NewsService;
import com.rest.newsservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;
import java.util.UUID;

@Aspect
@RequiredArgsConstructor
@Component
@Slf4j
public class CheckUserIdAspect {
    private final UserService userService;
    private final NewsService newsService;
    private final CommentService commentService;
    private final SessionHolder sessionHolder;

    @Before(value = "@annotation(param)")
    public void beforeCheckUserUuid(CheckUserIdPrivacy param) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request =
                ((ServletRequestAttributes) requestAttributes).getRequest();
        Map<String, String> pathVariable =
                (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Long ownerId = Long.valueOf(pathVariable.get("id"));
        UUID ownerUuid;
        switch (param.entityType()) {
            case USER -> ownerUuid = userService.findById(ownerId).getUuid();
            case NEWS -> ownerUuid = newsService.findById(ownerId).getUser().getUuid();
            case COMMENT -> ownerUuid = commentService.findById(ownerId).getUser().getUuid();
            case NOT_FOUND -> throw new InnerException("Внутренняя ошибка entityType:NOT_FOUND");
            default -> throw new InnerException("Внутренняя ошибка entityType:Необработанный тип");
        }
        UUID currentUserUuid = sessionHolder.logId();

        if (!currentUserUuid.equals(ownerUuid)) {
            throw new AccessException("Нет доступа к этому комментарию для текущего пользователя");
        }
    }
}
