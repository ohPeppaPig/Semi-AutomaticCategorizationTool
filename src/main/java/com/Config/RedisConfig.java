package com.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private int port;

    @Value("${redis.maxTotal}")
    private int maxTotal;

    @Value("${redis.maxIdle}")
    private int maxIdle;

    @Value("${redis.minIdle}")
    private int minIdle;

    @Value("${redis.timeout}")
    private int timeout;

    @Value("${redis.maxWait}")
    private long maxWait;

    @Value("${redis.password}")
    private String password;

    @Bean
    public JedisPool getJedisPool(){

        JedisPoolConfig jedisPoolConfig= new JedisPoolConfig();

        // 连接池阻塞最大等待时间
        jedisPoolConfig.setMaxWaitMillis(maxWait);

        // 连接池最大空闲连接数
        jedisPoolConfig.setMaxIdle(maxIdle);

        // 连接池最小空闲连接数
        jedisPoolConfig.setMinIdle(maxIdle);

        // 连接池最大链接数
        jedisPoolConfig.setMaxTotal(maxTotal);


        JedisPool jedisPool= new JedisPool(jedisPoolConfig,host,port,timeout,password);

        return jedisPool;
    }
}
