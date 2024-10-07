package com.rest.newsservice.event;

import com.rest.newsservice.model.security.RefreshToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisKeyExpiredEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RedisExpirationEvent {
    @EventListener
    public void handleRedisKeyExpirationEvent(RedisKeyExpiredEvent<RefreshToken> expiredEvent) {
        RefreshToken expiredRefreshToken = (RefreshToken) expiredEvent.getValue();
        if (expiredRefreshToken == null) {
            throw new RuntimeException("RefreshToken is null in handleRedisKeyExpirationEvent");
        }
        log.info("Refresh token with key={} has expired. Refresh token is {}",
                expiredRefreshToken.getId(),
                expiredRefreshToken.getToken());
    }
}
