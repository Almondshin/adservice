package org.example.adservice.persistance.redis;

import org.example.adservice.domain.coreservice.RedisLockPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisLockAdapter implements RedisLockPort {
    private static final Logger LOG = LoggerFactory.getLogger(RedisLockAdapter.class);

    private final StringRedisTemplate redisTemplate;

    public RedisLockAdapter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String acquireLock(String key, long expireTime) {
        String token = java.util.UUID.randomUUID().toString();  // 랜덤 토큰
        Boolean success = redisTemplate.opsForValue()
                .setIfAbsent(key, token, expireTime, TimeUnit.MILLISECONDS);

        if (Boolean.TRUE.equals(success)) {
            LOG.info("Lock 획득 성공 - key: {}, token: {}", key, token);
            return token;
        } else {
            LOG.debug("Lock 획득 실패 - key: {}", key);
            return null;
        }
    }

    @Override
    public void releaseLock(String key, String lockValue)  {
        if (lockValue == null) {
            LOG.debug("releaseLock 호출: lockValue가 null입니다. key: {}", key);
            return;
        }

        String redisValue = redisTemplate.opsForValue().get(key);

        if (lockValue.equals(redisValue)) {
            redisTemplate.delete(key);
            LOG.info("Lock 해제 성공 - key: {}, token: {}", key, lockValue);
        } else {
            LOG.debug("Lock 해제 실패(소유권 다름) - key: {}, lockValue: {}, redisValue: {}",
                    key, lockValue, redisValue);
        }
    }
}
