package com.czq.sports.controller;

import com.czq.sports.pojo.User;
import com.czq.sports.service.UserService;
import com.czq.sports.utils.BaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public BaseResult login(String account, String password) {
        BaseResult result = new BaseResult();
        User user = userService.getUserByAccount(account);
        if (user == null) {
            logger.error("用户不存在");
            result.setMessage("用户不存在");
        } else {

        }

        return result;
    }
}
