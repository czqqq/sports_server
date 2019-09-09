package com.czq.sports.mapper;

import com.czq.sports.pojo.Group;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface GroupMapper {
    @Select("SELECT * FROM group WHERE name = #{name}")
    Group findByName(@Param("name") String name);

}
