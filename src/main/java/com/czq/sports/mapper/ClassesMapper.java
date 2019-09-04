package com.czq.sports.mapper;

import com.czq.sports.pojo.Classes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ClassesMapper {
    @Select("SELECT * FROM classes WHERE name = #{name}")
    Classes findByName(@Param("name") String name);
}
