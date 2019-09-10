package com.czq.sports.service;

import com.czq.sports.pojo.Student;

import java.util.List;

public interface StudentService {

    int deleteByCid(Integer cid);

    int insertBatch(List<Student> students);
}
