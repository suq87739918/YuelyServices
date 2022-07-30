package com.try07.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.concurrent.TimeUnit;

public class example {
    conf:
    @Configuration
    @EnableCaching
    public class RedisConfig {
        @Bean
        public RedisConnectionFactory connectionFactory() {
            return new LettuceConnectionFactory();     }
        }
        @Bean
        public RedisTemplate<String, Object> template() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new JdkSerializationRedisSerializer());
        template.setValueSerializer(new JdkSerializationRedisSerializer());
        template.setEnableTransactionSupport(true);
        template.afterPropertiesSet();
        return template;
    }
}

    redis service:
    @Service
    public class RedisService {
        private final RedisTemplate<String, Object> template;

        @Autowired
        public RedisService(RedisTemplate<String, Object> template) {
            this.template = template;
        }
        public void setLTSandSTL(String longUrl, String shortUrl, long time) {
            template.opsForValue().set(longUrl, shortUrl, time, TimeUnit.MINUTES);
            template.opsForValue().set(shortUrl, longUrl, time, TimeUnit.MINUTES);
        }
        public void expire(String key, long time) {
            template.expire(key, time, TimeUnit.MINUTES);
        }
        public void set(String key, String value) {
            template.opsForValue().set(key, value);
        }
    }

    Cache:
    @Transactional
    public String generateShortenUrl(String longUrl) {
        if (!UrlUtils.isValidLongUrl(longUrl)) {
            LOGGER.error("Invalid long URL");
            return null;
        }
        String shortUrl = fetchShortUrlFromCache(longUrl);
        if (shortUrl != null) {
         LOGGER.info("get from cache");
          return shortUrl;
        }
        shortUrl = longToShortRepository.getByLongUrl(longUrl).getShortUrl();
        if (shortUrl != null) {
            LOGGER.info("get from DB");
            redisService.setLTSandSTL(longUrl, shortUrl, 20);
            LOGGER.info(("DB: save to cache"));
            return shortUrl;
        }
        shortUrl = urlShortener.generate();
        redisService.setLTSandSTL(longUrl, shortUrl, 20);
        LOGGER.info("save to cache");

        saveUrl(longUrl, shortUrl);
        return shortUrl;
    }

    private String fetchShortUrlFromCache(String longUrl) {
    String shortUrl = fetchValueByKey(longUrl);
    return shortUrl;
}

    private String fetchValueByKey(String key) {
    String value = (String)redisService.get(key);
    redisService.expire(key, 20);
    return value;
    }




