package com.czq.sports.service.impl;

import com.czq.sports.mapper.StudentProjectMapper;
import com.czq.sports.pojo.StudentProject;
import com.czq.sports.service.StudentProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentProjectServiceImpl implements StudentProjectService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private StudentProjectMapper studentProjectMapper;
    @Override
    public int deleteByCid(Integer cid) {
        if (cid == null) {
            logger.error("删除失败，cid为空");
            return 0;
        }
        return studentProjectMapper.deleteByCid(cid);
    }

    @Override
    public int insertBatch(List<StudentProject> projects) {
        return studentProjectMapper.insertBatch(projects);
    }
}
