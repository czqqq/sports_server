package com.czq.sports.service.impl;

import com.czq.sports.mapper.ClassesMapper;
import com.czq.sports.pojo.Classes;
import com.czq.sports.service.ClassesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ClassesServiceImpl implements ClassesService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ClassesMapper classesMapper;

    @Override
    public Classes getClassesByName(String name) {
        if (!StringUtils.hasText(name)) {
            logger.error("查找班级时，name为空");
            return null;
        }
        return classesMapper.findByName(name);
    }
}
