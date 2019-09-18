package com.czq.sports.mapper;

import com.czq.sports.pojo.Project;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ProjectMapper {
    @Select("SELECT * FROM `project` WHERE name = #{name}")
    Project selectByName(@Param(value = "name") String name);
}
