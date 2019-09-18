package com.czq.sports.mapper;

import com.czq.sports.pojo.StudentProject;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentProjectMapper {
    @Insert({
            "<script>",
            "INSERT INTO `student_project` (`sid`, `pid`, `ct`, `cid`, `sname`)  values ",
            "<foreach collection='list' item='item' index='index' separator=','>",
            "(#{item.sid}, #{item.pid}, #{item.ct, jdbcType=TIMESTAMP}, #{item.cid}, #{item.sname})",
            "</foreach>",
            "</script>"
    })
    int insertBatch(@Param(value="list") List<StudentProject> list);

    @Delete("delete from student_project where cid = #{cid}")
    int deleteByCid(@Param(value = "cid") Integer cid);
}
