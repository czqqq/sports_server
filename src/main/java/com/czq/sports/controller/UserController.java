package com.czq.sports.controller;

import com.czq.sports.pojo.User;
import com.czq.sports.service.UserService;
import com.czq.sports.utils.BaseResult;
import com.czq.sports.utils.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public BaseResult login(String userName, String password) {
        BaseResult result = new BaseResult();
        User user = userService.getUserByAccount(userName);
        if (user == null) {
            logger.error("用户不存在");
            result.setCode(ResultCode.NOTUSER);
            result.setMsg("用户不存在");
        } else {

        }

        return result;
    }
}
