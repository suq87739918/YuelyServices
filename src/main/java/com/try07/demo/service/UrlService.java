package com.try07.demo.service;

import com.try07.demo.entity.Url;
import com.try07.demo.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.try07.demo.logic.Encoder.getShortUrl;
import static com.try07.demo.logic.ValidationCheck.isValid;

@Service
public class UrlService {
    @Autowired
    private UrlRepository urlRepository;
    public String getLong_Url(String id) {
        return urlRepository.findByShortUrl(id);//按照ShortUrl查询
    }
    @Transactional
    public Url getShort_Url(String url) {
        if(! isValid(url)){
            System.out.println("please check the input url");
            return null;
        }
        Url urlObject = new Url();
        urlObject.setLong_url(url);
        urlObject.setShorturl(getShortUrl(url));
        return urlRepository.save(urlObject);
    }
}
