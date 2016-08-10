package com.yk.springboot.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Set;

/**
 * Created by yukui on 2016/8/10.
 */
public class RedisCache implements Cache {

    private RedisTemplate redisTemplate;

    private String keyPrefix = "shiro_redis_cache:";

    public RedisCache(RedisTemplate redisTemplate) {
        if (null == redisTemplate) {
            throw new IllegalArgumentException("cache  argument cannot be null ");
        }
        this.redisTemplate = redisTemplate;
    }

    /**
     * 获得byte[]型的key
     * @param key
     * @return
     */
    private byte[] getKeyWithCache(Object key) {
        String cacheKey = keyPrefix + key;
        return cacheKey.getBytes();
    }

    public RedisCache(RedisTemplate redisTemplate, String keyPrefix) {
        this(redisTemplate);
        this.keyPrefix = keyPrefix;
    }

    @Override
    public Object get(Object key) throws CacheException {
        Object values = redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.get(getKeyWithCache(key));
                return null;
            }
        });
        return values;
    }

    @Override
    public Object put(Object key, Object value) throws CacheException {
        Object values = redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(getKeyWithCache(key),redisTemplate.getDefaultSerializer().serialize(value));
                return null;
            }
        });
        return values;
    }

    @Override
    public Object remove(Object key) throws CacheException {
        Object values = redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.decr(getKeyWithCache(key));
                return null;
            }
        });
        return values;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set keys() {
        return null;
    }

    @Override
    public Collection values() {
        return null;
    }
}
