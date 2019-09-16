package com.czq.sports.mapper;

import com.czq.sports.pojo.Student;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;
import java.util.Map;

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

    @Select("SELECT " +
            "c.name '班级'," +
            "MIN(s.no) '最小号码'," +
            "MAX(s.no) '最大号码'," +
            "SUM(s.sex=0 AND g.`name` = '高职') '男高'," +
            "SUM(s.sex=0 AND g.`name` = '中职') '男中'," +
            "SUM(s.sex=1 AND g.`name` = '高职') '女高'," +
            "SUM(s.sex=1 AND g.`name` = '中职') '女中'," +
            "SUM(1) '合计' " +
            "FROM `student` s " +
            "LEFT JOIN `classes` c ON s.cid = c.id " +
            "LEFT JOIN `group` g ON c.gid = g.id " +
            "GROUP BY c.id")
    List<Map<String, String>> numberStatistics();

    @Select("call p_update_no()")
    @Options(statementType = StatementType.CALLABLE)
    void updateNo();
}
