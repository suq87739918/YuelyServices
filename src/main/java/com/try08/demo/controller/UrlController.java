package com.try08.demo.controller;

import com.try08.demo.entity.Url;
import com.try08.demo.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shortener")
public class UrlController {
    @Autowired
    /*
    @Autowired:
        1.默认按照类型去容器中查找
        2.如果有多个相同类型组件，再将属性名作为组件id在容器中查找
            private urlService urlservice里 urlservice是属性名
     */
    private UrlService urlService;

    @GetMapping("/{id}")
    public String getLong_Url(@PathVariable String id){
        return urlService.getLong_Url(id);
    }

    @PostMapping
    public Url getShort_Url(@RequestBody String url){
        return urlService.getShort_Url(url);
    }
}
