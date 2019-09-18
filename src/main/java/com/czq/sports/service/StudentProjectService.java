package com.czq.sports.service;

import com.czq.sports.pojo.StudentProject;

import java.util.List;

public interface StudentProjectService {

    int deleteByCid(Integer cid);

    int insertBatch(List<StudentProject> studentProjects);
}
