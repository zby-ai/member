package com.atguigu.atcrowdfunding.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * @author zbystart
 * @create 2021-02-27 14:49
 */
@SpringBootTest
public class RedisTest {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Test
    public void testRedisTemplate() {
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        opsForValue.set("heiehi","hello!");
    }
}
