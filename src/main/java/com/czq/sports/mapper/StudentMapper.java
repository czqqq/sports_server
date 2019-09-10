package com.czq.sports.mapper;

import com.czq.sports.pojo.Student;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentMapper {

    @Delete("delete from student where cid = #{cid}")
    int deleteByCid(@Param(value = "cid") Integer cid);

    @Insert({
            "<script>",
            "insert into student(no, name, ct, cid, gid, sex) values ",
            "<foreach collection='list' item='item' index='index' separator=','>",
            "(#{item.no}, #{item.name}, #{item.ct, jdbcType=TIMESTAMP}, #{item.cid}, #{item.gid}, #{item.sex})",
            "</foreach>",
            "</script>"
    })
    int insertBatch(@Param(value="list") List<Student> list);

}
