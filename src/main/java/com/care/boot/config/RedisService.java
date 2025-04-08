package com.care.boot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void save(String key, String value) {
        redisTemplate.opsForValue().set(key, value, 30, TimeUnit.MINUTES);
    }
    
    public void saveLoginUser(String sessionId, String userId) {
        String key = "loginUser:" + sessionId;
        System.out.println("📦 저장할 키: " + key);
        System.out.println("📦 저장할 유저 ID: " + userId);
        redisTemplate.opsForValue().set(key, userId, 30, TimeUnit.MINUTES);
    }


    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
