package com.czq.sports.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Project {
    private Integer id;
    private String name;
    private Byte status;
    private Integer limit;
    private Long duration;
    private Date ct;
}
