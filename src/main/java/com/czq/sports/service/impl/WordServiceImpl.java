package com.czq.sports.service.impl;

import com.czq.sports.mapper.ClassesMapper;
import com.czq.sports.mapper.GroupMapper;
import com.czq.sports.mapper.StudentMapper;
import com.czq.sports.pojo.Classes;
import com.czq.sports.pojo.Group;
import com.czq.sports.pojo.Student;
import com.czq.sports.service.WordService;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlOptions;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblGrid;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblGridCol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WordServiceImpl implements WordService {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private ClassesMapper classesMapper;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void generateWord() {


        //生成 7_参赛人员统计表
//   todo     numberStatistics();

        //生成8_号码对照表
        studentNo();



        String[] srcDocxs = {
                "d:\\words\\0_目录.docx",
                "d:\\words\\1_竞赛规程.docx",
                "d:\\words\\2_四个名单.docx",
                "d:\\words\\3_开幕和闭幕议程.docx",
                "d:\\words\\4_田径运动会精神文明奖评比条件和办法.docx",
                "d:\\words\\5_开幕式闭幕式方案.docx",
                "d:\\words\\6_广播体操比赛评分标准.docx",
                "d:\\words\\7_参赛人员统计表.docx",
                "d:\\words\\8_号码对照表.docx",
                "d:\\words\\9_竞赛日程.docx",
                "d:\\words\\10_分组分道表.docx",
                "d:\\words\\11_趣味比赛.docx",
                "d:\\words\\12_安保工作方案.docx",
                "d:\\words\\13_应急预案和应急措施.docx",
                "d:\\words\\14_田径纪录.docx"
        };
        String destDocx = "d:\\words\\章程.docx";
//   todo     mergeDoc(srcDocxs, destDocx);


    }


    /**
     * 合并docx文件
     *
     * @param srcDocxs 需要合并的目标docx文件
     * @param destDocx 合并后的docx输出文件
     */
    private void mergeDoc(String[] srcDocxs, String destDocx) {

        OutputStream dest = null;
        List<OPCPackage> opcpList = new ArrayList<OPCPackage>();
        int length = null == srcDocxs ? 0 : srcDocxs.length;
        /**
         * 循环获取每个docx文件的OPCPackage对象
         */
        for (int i = 0; i < length; i++) {
            String doc = srcDocxs[i];
            OPCPackage srcPackage = null;
            try {
                srcPackage = OPCPackage.open(doc);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (null != srcPackage) {
                opcpList.add(srcPackage);
            }
        }

        int opcpSize = opcpList.size();
        //获取的OPCPackage对象大于0时，执行合并操作
        if (opcpSize > 0) {
            try {
                dest = new FileOutputStream(destDocx);
                XWPFDocument src1Document = new XWPFDocument(opcpList.get(0));
                CTBody src1Body = src1Document.getDocument().getBody();
                //设置分页符
                XWPFParagraph p1 = src1Document.createParagraph();
                p1.setPageBreak(true);

                //OPCPackage大于1的部分执行合并操作
                if (opcpSize > 1) {

                    for (int i = 1; i < opcpSize; i++) {
                        OPCPackage src2Package = opcpList.get(i);
                        XWPFDocument src2Document = new XWPFDocument(src2Package);
                        CTBody src2Body = src2Document.getDocument().getBody();
                        if (i != opcpSize -1) {
                            //设置分页符
                            XWPFParagraph p2 = src2Document.createParagraph();
                            p2.setPageBreak(true);
                        }
                        appendBody(src1Body, src2Body);
                    }
                }
                //将合并的文档写入目标文件中
                src1Document.write(dest);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //注释掉以下部分，去除影响目标文件srcDocxs。
				/*for (OPCPackage opcPackage : opcpList) {
					if(null != opcPackage){
						try {
							opcPackage.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}*/
                //关闭流
                IOUtils.closeQuietly(dest);
            }
        }


    }

    /**
     * 合并文档内容
     *
     * @param src    目标文档
     * @param append 要合并的文档
     * @throws Exception
     */
    private void appendBody(CTBody src, CTBody append) throws Exception {
        XmlOptions optionsOuter = new XmlOptions();
        optionsOuter.setSaveOuter();
        String appendString = append.xmlText(optionsOuter);
        String srcString = src.xmlText();
        String prefix = srcString.substring(0, srcString.indexOf(">") + 1);
        String mainPart = srcString.substring(srcString.indexOf(">") + 1,
                srcString.lastIndexOf("<"));
        String sufix = srcString.substring(srcString.lastIndexOf("<"));
        String addPart = appendString.substring(appendString.indexOf(">") + 1,
                appendString.lastIndexOf("<"));
        CTBody makeBody = CTBody.Factory.parse(prefix + mainPart + addPart
                + sufix);
        src.set(makeBody);
    }

    /**
     * 人数统计
     */
    private void numberStatistics() {
        //获取统计
        List<Map<String,String>> results = studentMapper.numberStatistics();

        try {
            //创建表格
            //Blank Document
            XWPFDocument doc= new XWPFDocument();

            //Write the Document in file system
            FileOutputStream out = new FileOutputStream(new File("d:\\words\\7_参赛人员统计表.docx"));
            //添加标题
            XWPFParagraph titleParagraph = doc.createParagraph();

            //设置段落居中
            titleParagraph.setAlignment(ParagraphAlignment.CENTER);

            XWPFRun titleParagraphRun = titleParagraph.createRun();
            titleParagraphRun.setText("第十九届田径运动会学生组参赛人数统计表");
            titleParagraphRun.setColor("000000");
            titleParagraphRun.setFontSize(20);
            titleParagraphRun.setBold(true);
            titleParagraphRun.addBreak();


            //create table
            XWPFTable table = doc.createTable();

            //设置指定宽度
            int[] colWidths = {1000,1200,1200,1200,1200,1200,1200,1200};
            CTTbl ttbl = table.getCTTbl();
            CTTblGrid tblGrid = ttbl.addNewTblGrid();
            for (int i : colWidths) {
                CTTblGridCol gridCol = tblGrid.addNewGridCol();
                gridCol.setW(new BigInteger(i+""));

            }

            //表头
            XWPFTableRow rowHead = table.getRow(0);
            rowHead.setHeight(500);
//            XWPFTableCell cell = rowHead.getCell(0);
//            cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            XWPFParagraph cellParagraph = rowHead.getCell(0).getParagraphs().get(0);
            cellParagraph.setAlignment(ParagraphAlignment.CENTER); //设置表头单元格居中
            XWPFRun cellParagraphRun  = cellParagraph.createRun();
            cellParagraphRun.setFontSize(10); //设置表头单元格字号
            cellParagraphRun.setBold(true); //设置表头单元格加粗
            cellParagraphRun.setText("序号");


            String[] rows = {"代表队","号码起止","男高","男中","女高","女中","合计"};
            for (String row : rows) {
                cellParagraph = rowHead.addNewTableCell().getParagraphs().get(0);
                cellParagraph.setAlignment(ParagraphAlignment.CENTER); //设置表头单元格居中
                cellParagraph.setVerticalAlignment(TextAlignment.CENTER); //设置表头单元格居中
                cellParagraphRun  = cellParagraph.createRun();
                cellParagraphRun.setFontSize(10); //设置表头单元格居中
                cellParagraphRun.setBold(true); //设置表头单元格加粗
                cellParagraphRun.setText(row);
            }

            for (int i = 0; i < results.size(); i++) {
                Map<String, String> result = results.get(i);
                XWPFTableRow tableRowTwo = table.createRow();
                for (int j = 0; j < 8; j++) {
                    XWPFParagraph cell = tableRowTwo.getCell(j).getParagraphs().get(0);
                    cell.setAlignment(ParagraphAlignment.CENTER);
                }
                tableRowTwo.getCell(0).setText(i + 1 + "");
                tableRowTwo.getCell(1).setText(result.get("班级"));
                tableRowTwo.getCell(2).setText(result.get("最小号码") +" ~ "+ result.get("最大号码"));
                tableRowTwo.getCell(3).setText(((Object)result.get("男高")).toString());
                tableRowTwo.getCell(4).setText(((Object)result.get("男中")).toString());
                tableRowTwo.getCell(5).setText(((Object)result.get("女高")).toString());
                tableRowTwo.getCell(6).setText(((Object)result.get("女中")).toString());
                tableRowTwo.getCell(7).setText(((Object)result.get("合计")).toString());
            }

            doc.write(out);
            out.close();
            System.out.println("create_table.docx written successully");
        } catch (IOException e) {
            logger.error("创建人数统计表格失败",e);
        }
    }

    private void studentNo() {
        //设置编号
//todo        studentMapper.updateNo();

        //处理为分组数据
        List<Group> groups = groupMapper.selectAvailAble();
        List<Classes> classes = classesMapper.selectAvailable();
        List<Student> students = studentMapper.selectAvailable();
        Map<Integer, List<Classes>> group2Classes = new HashMap<>();
        Map<Integer, List<Student>> classes2Student = new HashMap<>();

        for (Group group : groups) {
            Integer gid = group.getId();
            List<Classes> classes1 = new ArrayList<>();
            for (Classes aClass : classes) {
                if (aClass.getGid().equals(gid)) {
                    classes1.add(aClass);
                }

                Integer cid = aClass.getId();
                List<Student> students1 = new ArrayList<>();
                for (Student student : students) {
                    if (student.getCid().equals(cid)) {
                        students1.add(student);
                    }
                }
                classes2Student.put(cid, students1);
            }
            group2Classes.put(gid,classes1);
        }

        try {
            //创建表格
            //Blank Document
            XWPFDocument doc= new XWPFDocument();

            //Write the Document in file system
            FileOutputStream out = new FileOutputStream(new File("d:\\words\\8_号码对照表.docx"));
            //添加标题
            XWPFParagraph titleParagraph = doc.createParagraph();
            //设置段落居中
            titleParagraph.setAlignment(ParagraphAlignment.CENTER);

            XWPFRun titleParagraphRun = titleParagraph.createRun();
            titleParagraphRun.setText("第十九届田径运动会运动员名单号码对照表");
            titleParagraphRun.setColor("000000");
            titleParagraphRun.setFontSize(20);
            titleParagraphRun.setBold(true);
            titleParagraphRun.addBreak();


            //填充内容： 组别 -> 班级 -> 男女 -> 人员
            XWPFParagraph paragraph;
            for (Group group : groups) {
                //组名
                paragraph = doc.createParagraph();
                paragraph.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun groupNameRun = paragraph.createRun();
                groupNameRun.setText("（" + group.getName() + "组）");
                groupNameRun.setFontSize(18);
                groupNameRun.setBold(true);
                groupNameRun.addBreak();

                //班级名
                List<Classes> cList = group2Classes.get(group.getId());
                for (Classes c : cList) {
                    XWPFRun classesNameRun = paragraph.createRun();
                    classesNameRun.setText(c.getName());
                    classesNameRun.setFontSize(14);
                    classesNameRun.setBold(true);
                    classesNameRun.addBreak();

                    List<Student> sList = classes2Student.get(c.getId());

                    paragraph = doc.createParagraph();
                    paragraph.setAlignment(ParagraphAlignment.LEFT);
                    XWPFRun cgroup = paragraph.createRun();
                    cgroup.setText("男子"+ group.getName()+"：");
                    cgroup.addBreak();


                    boolean createdWoman = false;
                    XWPFRun sClass = paragraph.createRun();
                    String text = "";
                    int count = 1;
                    for (Student s : sList) {
                        if (s.getSex() == 1) {
                            if (!createdWoman) {
                                sClass.setText(text);
                                text = "";

                                cgroup = paragraph.createRun();
                                cgroup.setText("女子"+ group.getName()+"：");
                                cgroup.addBreak();
                                createdWoman = true;
                                count = 1;
                            }
                        }

                        text += s.getNo() + "  " + s.getName() +  ( count == 4 ? "\n\r" : "\t\t");

                     count++;
                    }
                    sClass.setText(text);


                }


            }









            doc.write(out);
            out.close();
            System.out.println("生成8_号码对照表.docx 成功");
        } catch (IOException e) {
            logger.error("创建号码对照表失败",e);
        }
    }


}
