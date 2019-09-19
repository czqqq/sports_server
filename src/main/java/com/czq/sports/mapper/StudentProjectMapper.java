package com.czq.sports.mapper;

import com.czq.sports.pojo.StudentProject;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

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

    @Select({"SELECT count(1) `count`, GROUP_CONCAT(s.name) `athletes`,p.name `project`,s.sex, g.name `group` \n" +
            "FROM `student_project` sp \n" +
            "LEFT JOIN `student` s ON (sp.sname = s.`name` AND sp.cid = s.cid) \n" +
            "LEFT JOIN `project` p ON sp.pid = p.id \n" +
            "LEFT JOIN `classes` c ON sp.cid = c.id \n" +
            "LEFT JOIN `group` g ON c.gid = g.id \n" +
            "GROUP BY sp.pid, s.sex"})
    List<Map<String,Object>> selectStatistics();
}
