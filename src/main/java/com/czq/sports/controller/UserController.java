package com.czq.sports.controller;

import com.czq.sports.pojo.User;
import com.czq.sports.service.UserService;
import com.czq.sports.utils.BaseResult;
import com.czq.sports.utils.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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
            result.setMsg("用户不存在");
            result.setCode(ResultCode.NOTUSER);
        }
        return result;
    }

    @GetMapping("/get_info")
    public BaseResult getInfo() {
        BaseResult result = new BaseResult();
        /*
            name: 'super_admin',
            user_id: '1',
            access: ['super_admin', 'admin'],
            token: 'super_admin',
            avator: 'https://file.iviewui.com/dist/a0e88e83800f138b94d2414621bd9704.png'
        */
        Map<String, Object> data = new HashMap<>(5);
        data.put("name", "admin");
        data.put("user_id", 1);
        data.put("access", new String[]{"super_admin", "admin"});
        data.put("token", "super_admin");
        data.put("avator", "https://file.iviewui.com/dist/a0e88e83800f138b94d2414621bd9704.png");

        result.setData(data);
        return result;
    }
}
