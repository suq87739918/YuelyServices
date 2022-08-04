package com.try07.demo.service;

public interface RedisUtilsInterface {
    void expire(String key, long time);
    Long getExpire(String key);
    Object get(String key);
    void set(String key, Object value);
    void set(String key, Object value, long time);
    void setLTS(String long_url, String short_url, long time);
    void setSTL(String short_url, String long_url, long time);
}
