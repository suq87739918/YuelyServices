package com.try07.demo.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "url")
public class Url {

    @Id//数据库主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //GenerationType.AUTO: hibernate will look for the default hibernate_sequence table
    private int id;
    //private String created_at;
    @Column(name = "short_url")
    private String short_url;
    @Column(name = "long_url")
    private String long_url;
}
