package com.czq.sports.pojo;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String account;
    private String password;
    private String name;
}
