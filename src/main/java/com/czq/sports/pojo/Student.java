package com.czq.sports.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Student {
    private Integer id;
    private String name;
    private String no;
    private Integer cid;
    private Date ct;
    private Byte sex;
}
