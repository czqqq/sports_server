package com.czq.sports.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Group {
    private Integer id;
    private String name;
    private Byte status;
    private Date ct;
}
