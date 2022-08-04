package com.try07.demo.logic;

import org.apache.commons.validator.routines.UrlValidator;

public class ValidationCheck {
    public static boolean isValid(String url){
        boolean validation = UrlValidator.getInstance().isValid(url);
        //Commons-validator包中的UrlValidator校验器，校验URL是否有效
        //getInstance()可以跨栈区域使用，或者远程跨区域使用。getInstance()通常是创建static静态实例方法的。类似new
        return validation;
    }
}
