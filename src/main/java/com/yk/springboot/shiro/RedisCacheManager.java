package com.yk.springboot.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by yukui on 2016/8/9.
 */
public class RedisCacheManager implements CacheManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisCacheManager.class);

    // fast lookup by name map
    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();

    private RedisTemplate redisTemplate;

    private String keyPrefix = "shiro_redis_cache:";

    public RedisCacheManager(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        LOGGER.debug("获取名称为: " + name + " 的RedisCache实例");
        Cache cache = caches.get(name);
        if (cache == null) {
            cache = new RedisCache(redisTemplate, keyPrefix);
            caches.put(name, cache);
        }
        return cache;
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }
}
