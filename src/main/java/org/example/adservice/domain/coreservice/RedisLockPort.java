package org.example.adservice.domain.coreservice;

public interface RedisLockPort {
    String acquireLock(String key, long expireTime);
    void releaseLock(String key, String lockValue);
}

