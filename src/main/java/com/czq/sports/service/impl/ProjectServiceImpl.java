package com.czq.sports.service.impl;

import com.czq.sports.mapper.ProjectMapper;
import com.czq.sports.pojo.Project;
import com.czq.sports.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ProjectMapper projectMapper;
    @Override
    public Project selectByName(String name) {
        Project project = null;
        if (StringUtils.hasText(name)) {
            project = projectMapper.selectByName(name);
        }
        return project;
    }

}
