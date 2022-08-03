package com.try07.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService implements RedisUtilsInterface{

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Override
    public void setLTS(String long_url, String short_url, long time) {
        redisTemplate.opsForValue().set(long_url, short_url, time, TimeUnit.MINUTES);
    }
    @Override
    public void setSTL(String short_url, String long_url, long time){
        redisTemplate.opsForValue().set(short_url, long_url,  time, TimeUnit.MINUTES);
    }
    /**
     * 指定缓存失效时间 @param key 键 @param time 时间(秒) @return
     */
    @Override
    public void expire(String key, long time) {
        try {
            if (time > 0) redisTemplate.expire(key, time, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return
     */
    @Override
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.MINUTES);
    }
    /**
     * 普通缓存获取 @param key 键 @return 值
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }
    /**
     * 普通缓存放入 @param key 键 @param value 值 @return true成功 false失败
     */
    public void set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 普通缓存放入并设置时间 @param key 键 @param value 值 @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期 @return true成功 false 失败
     */
    public void set(String key, Object value, long time) {
        try {
            if (time > 0) redisTemplate.opsForValue().set(key, value, time, TimeUnit.MINUTES);
            else {
                set(key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
