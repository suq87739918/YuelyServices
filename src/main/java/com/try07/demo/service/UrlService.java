package com.try07.demo.service;

import com.try07.demo.entity.Url;
import com.try07.demo.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.try07.demo.logic.Encoder.getShortUrl;
import static com.try07.demo.logic.ValidationCheck.isValid;

@Service
public class UrlService implements UrlServiceInterface{
    @Autowired
    private UrlRepository urlRepository;
    private final RedisService redisService;

    public UrlService(RedisService redisService) {
        this.redisService = redisService;
    }

    //获取long和short的缓存过期时间
    private void showExpireTime(String long_url, String short_url){
        System.out.println("Expire Time:");
        System.out.println(redisService.getExpire(long_url));
        System.out.println(redisService.getExpire(short_url));
    }
    //设置long的缓存过期时间并获取short
    public String getShortFromCache(String long_url){
        String short_url = (String) redisService.get(long_url);
        if(short_url != null){
            redisService.expire(long_url,50);
        }
        return short_url;
    }
    //设置short的缓存过期时间并获取long
    public String getLongFromCache(String short_url){
        String long_url = (String) redisService.get(short_url);
        if(short_url != null){
            redisService.expire(short_url,50);
        }
        return long_url;
    }
    //保存在cache
    public void saveLongAndShort(String long_url, String short_url){
        int time = 50;
        redisService.setLTS(long_url,short_url,time);
        redisService.setSTL(short_url,long_url,time);
        System.out.println("saved to cache");
    }
    @Transactional
    @Override
    public String getShort_Url(String long_url) {
        if (!isValid(long_url)) {
            System.out.println("please check the input url");
            return null;
        }
        if (getShortFromCache(long_url) != null){
            String short_url = getShortFromCache(long_url);
            showExpireTime(long_url,short_url);
            System.out.println("short url is in the cache already");
            return short_url;
        }
        if (urlRepository.findByLongUrl(long_url) != null){
            Url longtoshortTemp = urlRepository.findByLongUrl(long_url);
            String short_url = longtoshortTemp.getShort_url();
            saveLongAndShort(long_url, short_url);
            return short_url;
        }
        //save to database
        Url getShort_Url = new Url();
        getShort_Url.setLong_url(long_url);
        String shortedurl = getShortUrl(long_url);
        getShort_Url.setShort_url(shortedurl);
        urlRepository.save(getShort_Url);
        return shortedurl;
    }

    @Override
    @Transactional
    public String getLong_Url(String short_url) {
        if (getLongFromCache(short_url) != null){
            String long_url = getLongFromCache(short_url);
            showExpireTime(long_url,short_url);
            System.out.println("long url is in the cache already");
            return long_url;
        }
        Url longtoshort = urlRepository.findByShortUrl(short_url);
        System.out.println("long url is in the database");
        String long_url = longtoshort.getLong_url();
        saveLongAndShort(long_url,short_url);
        return longtoshort.getLong_url();
    }
}
