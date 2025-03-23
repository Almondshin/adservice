package org.example.adservice.persistance.redis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.redis")
public class RedisProperties {
    private long lockExpireTime;

    public long getLockExpireTime() {
        return lockExpireTime;
    }
    public void setLockExpireTime(long lockExpireTime) {
        this.lockExpireTime = lockExpireTime;
    }
}
