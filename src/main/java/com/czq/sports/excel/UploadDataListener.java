package com.czq.sports.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.czq.sports.pojo.Classes;
import com.czq.sports.pojo.Group;
import com.czq.sports.pojo.Student;
import com.czq.sports.service.ClassesService;
import com.czq.sports.service.GroupService;
import com.czq.sports.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 模板的读取类
 *
 * @author Jiaju Zhuang
 */
@Component
public class UploadDataListener extends AnalysisEventListener<UploadData> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadDataListener.class);

    @Autowired
    private GroupService groupService;
    @Autowired
    private ClassesService classesService;
    @Autowired
    private StudentService studentService;

    private Classes classes;
    private Map<String,Integer> signCount;

    private static final int BATCH_COUNT = 100;
    private List<Student> list = new ArrayList<Student>();

    @Override
    public void invoke(UploadData data, AnalysisContext context) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));

        if (signCount == null) {
            signCount = new HashMap<>();
        }
        int row = context.readRowHolder().getRowIndex();
        if (row > 31) {
            signCount.clear();
            return;
        }

        byte sex = data.getGroupName().indexOf("男子") > 0 ? (byte)0 : (byte)1;

        String athletes1 = data.getAthletes1();
        String athletes2 = data.getAthletes2();

        if (",18,19,30,31,".contains(","+row + ",")) {
            //团体项目 todo
        } else {
            //个人项目

            List<String> strings = new ArrayList<>();
            if (athletes1 != null) {
                strings.add(athletes1);
            }
            if (athletes2 != null) {
                if (strings.contains(athletes2)) {
                    LOGGER.error("导入报名表失败，单个项目不能报名两次（同一行的两个名字一样）");
                    return;
                }
                strings.add(athletes2);
            }

            strings.forEach(athletes -> {
                if (StringUtils.hasText(athletes)) {
                    Integer count = signCount.get(athletes);
                    if (count == null) {
                        signCount.put(athletes, 1);
                    } else if (count == 1) {
                        signCount.put(athletes, 2);
                    }else {
                        LOGGER.error("导入报名表失败，一个人不能报超过两个项目");
                        return;
                    }
                    Student s = new Student();
                    s.setSex(sex);
                    s.setName(athletes);
                    s.setCid(this.classes.getId());
                    s.setGid(this.classes.getGid());
                    s.setCt(new Date());
                    list.add(s);
                }
            });
        }

     /*   if (list.size() >= BATCH_COUNT) {
            saveData();
            list.clear();
        }*/
    }


    /**
     * 解析头
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {

        int sheetIndex = context.readSheetHolder().getSheetNo();
        int rowIndex = context.readRowHolder().getRowIndex();

        if (sheetIndex == 0) {

            if (headMap.size() != 6) {
                return;  //todo 我也不知道为啥
            }

            if (this.classes == null) {
                this.classes = new Classes();
            }


            //学生报名单
            /*
             * 分组：	高职			报名单位：	09技师模具
             * 教练：
             * 联系人：				联系电话：	13859930921
             */

            if (rowIndex == 2) {
                String groupName = headMap.get(1);
                if ("分组：".equals(headMap.get(0)) && StringUtils.hasText(groupName)) {
                    Group group = groupService.getGroupByName(groupName);
                    if (group != null) {
                        classes.setGid(group.getId());
                    }
                } else {
                    LOGGER.error("报名分组信息错误！");
                }

                String classesName = headMap.get(5);
                if ("报名单位：".equals(headMap.get(4)) && StringUtils.hasText(classesName)) {
                    classes.setName(classesName);
                }else{
                    LOGGER.error("报名班级信息错误！");
                }
            }

            if (rowIndex == 3) {
                String coachName = headMap.get(1);
                if ("教练：".equals(headMap.get(0)) && StringUtils.hasText(coachName)) {
                    classes.setCoach(coachName);
                }
            }

            if (rowIndex == 4) {
                String leaderName = headMap.get(1);
                if ("联系人：".equals(headMap.get(0)) && StringUtils.hasText(leaderName)) {
                    classes.setLeader(leaderName);
                }

                String tel = headMap.get(5);
                if ("联系电话：".equals(headMap.get(4)) && StringUtils.hasText(tel)) {
                    classes.setTel(tel);
                }
            }
            if(rowIndex == 5){
                Classes classes1 = classesService.getClassesByName(classes.getName());
                if (classes1 == null) {
                    classes.setCt(new Date());
                    classesService.insertClasses(classes);
                } else {
                    classes.setId(classes1.getId());
                    classes.setCt(new Date());
                    classesService.updateClasses(classes);
                }
            }
        } else if (sheetIndex == 1) {
            //教师报名单
        }


        LOGGER.info("解析到一行头数据:{}", JSON.toJSONString(headMap));
//        super.invokeHeadMap(headMap, context);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        signCount = null;
        list.clear();
        LOGGER.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        LOGGER.info("{}条数据，开始存储数据库！", list.size());
        studentService.deleteByCid(this.classes.getId());
        studentService.insertBatch(list);
        this.classes = null;
        LOGGER.info("存储数据库成功！");
    }
}
