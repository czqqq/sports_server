package com.czq.sports.service.impl;

import com.czq.sports.mapper.UserMapper;
import com.czq.sports.pojo.User;
import com.czq.sports.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserMapper userMapper;


    @Override
    public User getUserByAccount(String account) {
        if (account == null || "".equals(account)) {
            logger.error("用户名不能为空");
            return null;
        }else{
            return userMapper.findByAccount(account);
        }
    }
}
