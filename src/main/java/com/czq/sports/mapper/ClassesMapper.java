package com.czq.sports.mapper;

import com.czq.sports.pojo.Classes;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ClassesMapper {
    @Select("SELECT * FROM classes WHERE name = #{name}")
    Classes findByName(@Param("name") String name);

    @Insert({ "insert into classes(name, ct, coach, leader, gid, tel) values(#{name}, #{ct, jdbcType=TIMESTAMP}, #{coach}, #{leader}, #{gid}, #{tel})" })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertClasses(Classes classes);
}
