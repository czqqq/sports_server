package com.czq.sports.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.holder.ReadSheetHolder;
import com.alibaba.fastjson.JSON;
import com.czq.sports.pojo.Classes;
import com.czq.sports.pojo.Group;
import com.czq.sports.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 模板的读取类
 *
 * @author Jiaju Zhuang
 */
public class UploadDataListener extends AnalysisEventListener<UploadData> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadDataListener.class);

    @Autowired
    private GroupService groupService;

    private static final int BATCH_COUNT = 100;
    List<UploadData> list = new ArrayList<UploadData>();

    @Override
    public void invoke(UploadData data, AnalysisContext context) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
        list.add(data);
        if (list.size() >= BATCH_COUNT) {
            saveData();
            list.clear();
        }
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {

        int sheetIndex = context.readSheetHolder().getSheetNo();
        int rowIndex = context.readRowHolder().getRowIndex();

        if (sheetIndex == 0) {

            Classes classes = new Classes();


            //学生报名单
            /**
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


        } else if (sheetIndex == 1) {
            //教师报名单
        }
        LOGGER.info("解析到一条头数据:{}", JSON.toJSONString(headMap));
//        super.invokeHeadMap(headMap, context);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        LOGGER.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        LOGGER.info("{}条数据，开始存储数据库！", list.size());
        LOGGER.info("存储数据库成功！");
    }
}
