package com.czq.sports.service.impl;

import com.czq.sports.mapper.GroupMapper;
import com.czq.sports.pojo.Group;
import com.czq.sports.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class GroupServiceImpl implements GroupService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private GroupMapper groupMapper;

    @Override
    public Group getGroupByName(String name) {
        if (!StringUtils.hasText(name)) {
            logger.error("查找班级name为空");
            return null;
        }
        return groupMapper.findByName(name);
    }
}
