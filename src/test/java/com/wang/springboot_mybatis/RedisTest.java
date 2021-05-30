package com.wang.springboot_mybatis;

import com.service.RedisService;
import com.service.RedisServicebyTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisTest {
    @Autowired
    RedisServicebyTemplate redisServicebyTemplate;

    @Autowired
    RedisService redisService;

    @Test
    void redis(){
        redisServicebyTemplate.set("name", "Mrz");
        assert(redisServicebyTemplate.get("name").equals("Mrz"));
    }

    @Test
    void jedis(){
        redisService.set("name","jj",-1);
        System.out.println(redisService.get("name"));
    }
}
