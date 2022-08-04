package com.try07.demo.logic;
import java.util.Random;

public class Encoder {
    public static String getShortUrl(String url){
        char[] root = new char[]{
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd',
                'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm',
                'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A', 'S', 'D',
                'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M'}; //写在class下
        StringBuffer str = new StringBuffer();  //避免动态扩充次数，影响效率, 使用append
        for (int i = 0; i < 7; i++){  //生成随机值长度最长为7
            int pointer = Math.abs(new Random().nextInt(62));//获取一个0到62的随机数，不包含63，返回绝对值
            str.append(root[pointer]); //str后面添加一个7位数长度的随机组合
        }
        String url_shorted = str.toString();
        String shortUrl = "http://localhost:8080/shortener/" + url_shorted;
        return shortUrl;
    }
}
