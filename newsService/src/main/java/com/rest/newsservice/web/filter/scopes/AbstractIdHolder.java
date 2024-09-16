package com.rest.newsservice.web.filter.scopes;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public abstract class AbstractIdHolder implements IdHolder {
    private final UUID uuid;

    protected AbstractIdHolder() {
        uuid = UUID.randomUUID();
    }

    @Override
    public UUID logId() {
        log.info("{} is: {}", holderType(), uuid);
        return uuid;
    }

    abstract String holderType();
}
