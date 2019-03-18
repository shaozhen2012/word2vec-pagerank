package com.rongpingkeji.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    private static final String LOCK = "_LOCK";

    @Autowired
    private RedisTemplate redisTemplate;

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void remove(String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    public void removePattern(String pattern) {
        Set<Serializable> keys = this.redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            this.redisTemplate.delete(keys);
        }
    }

    public void remove(String key) {
        if (exists(key)) {
            this.redisTemplate.delete(key);
        }
    }

    public void hashremove(String key, Object keyItem) {
        if (exists(key)) {
            this.redisTemplate.opsForHash().delete(key, keyItem);
        }
    }


    public boolean exists(String key) {
        return this.redisTemplate.hasKey(key).booleanValue();
    }

    public <T> T get(String key) {
        T result = null;
        ValueOperations<Serializable, T> operations = this.redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    public <T> boolean set(String key, T value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, T> operations = this.redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //读取hash值

    public <T> T hashget(String key, Object keyItem) {
        String hashKey = keyItem.toString();
        T result = null;
        result = (T) this.redisTemplate.opsForHash().get(key, hashKey);
        return result;
    }


    public <T> boolean hashset(String key, Object keyItem, T value) {
        String hashKey = keyItem.toString();
        boolean result = false;
        try {
            this.redisTemplate.opsForHash().put(key, hashKey, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public <T> boolean hashset(String key, Object keyItem, T value, Long expireTime) {
        boolean result = false;
        String hashKey = keyItem.toString();
        try {
            this.redisTemplate.opsForHash().put(key, hashKey, value);
            this.redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    //保存hash值
    public <T> boolean hashset(String key, Map value) {
        boolean result = false;
        try {
            this.redisTemplate.opsForHash().putAll(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //保存hash值  设置过期时间
    public <T> boolean hashset(String key, Map value, Long expireTime) {
        boolean result = false;
        try {
            this.redisTemplate.opsForHash().putAll(key, value);
            this.redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public <T> boolean set(String key, T value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, T> operations = this.redisTemplate.opsForValue();
            operations.set(key, value);
            this.redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void expire(String key, Long expireTime) {
        this.redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
    }

    /**
     * 锁住资源，如果资源已经被锁住了，返回false，如果成功锁住资源，返回true，60秒钟后自动解锁
     *
     * @param key
     * @return
     */
    public boolean lock(String key) {
        String newKey = key + LOCK;
        if (exists(newKey)) {
            return false;
        }

        return set(newKey, "1", 60L);
    }

    /**
     * 解锁资源
     *
     * @param key
     * @return
     */
    public boolean unlock(String key) {
        String newKey = key + LOCK;
        remove(newKey);
        return true;
    }
}

