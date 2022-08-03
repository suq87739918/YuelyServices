package com.try07.demo.repository;
import com.try07.demo.logic.Encoder;
import com.try07.demo.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url,Integer> {
    //JpaRepository对继承父接口中方法返回值类型做了适配
    //@Query(value = "select long_url from Url where short_url like %?1%", nativeQuery = true)
    //三元运算符，动态拼接
    //nativeQuery = true: 可以执行原生sql语句，所谓原生sql，也就是说这段sql拷贝到数据库中，然后把参数值给一下就能运行
    //String findByShortUrl(String id); //从Url处继承自定义接口
    Url findByLong_url(String long_url);
    Url findByShort_url(String short_url);
}
