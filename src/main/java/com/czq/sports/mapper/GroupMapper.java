package com.czq.sports.mapper;

import com.czq.sports.pojo.Group;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GroupMapper {
    @Select("SELECT * FROM `group` WHERE name = #{name}")
    Group findByName(@Param("name") String name);


    @Select("\n" +
            "SELECT g.* FROM `group` g RIGHT JOIN `classes` c ON g.id = c.gid WHERE g.`status` = 0 GROUP BY g.id")
    List<Group> selectAvailAble();
}
