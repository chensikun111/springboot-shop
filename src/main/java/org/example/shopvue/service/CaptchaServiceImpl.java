package org.example.shopvue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CaptchaServiceImpl  implements CaptchaService{
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void storeCaptchaTextWithExpiration(String uuid, String captchaText, long expirationInSeconds) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(uuid, captchaText, expirationInSeconds, TimeUnit.SECONDS);
    }
}
