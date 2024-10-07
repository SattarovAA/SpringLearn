package com.rest.newsservice.aop;

import com.rest.newsservice.model.security.RoleType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckUserIdPrivacy {
    EntityType entityType();
    RoleType[] alwaysAccessRoles();
}
