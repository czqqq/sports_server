package com.czq.sports.service.impl;

import com.czq.sports.mapper.StudentMapper;
import com.czq.sports.pojo.Student;
import com.czq.sports.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public int deleteByCid(Integer cid) {
        if (cid == null) {
            logger.error("删除失败，cid为空");
            return 0;
        }
        return studentMapper.deleteByCid(cid);
    }

    @Override
    public int insertBatch(List<Student> students) {
        return studentMapper.insertBatch(students);
    }
}
