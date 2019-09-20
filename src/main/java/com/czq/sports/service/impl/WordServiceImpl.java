package com.czq.sports.service.impl;

import com.czq.sports.excel.DocReplace;
import com.czq.sports.mapper.ClassesMapper;
import com.czq.sports.mapper.GroupMapper;
import com.czq.sports.mapper.StudentMapper;
import com.czq.sports.mapper.StudentProjectMapper;
import com.czq.sports.pojo.Classes;
import com.czq.sports.pojo.Group;
import com.czq.sports.pojo.Student;
import com.czq.sports.pojo.WordAthleteBean;
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
import org.springframework.util.StringUtils;

import java.io.*;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WordServiceImpl implements WordService {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private ClassesMapper classesMapper;
    @Autowired
    private StudentProjectMapper studentProjectMapper;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void generateWord() {


        //生成 7_参赛人员统计表
//        numberStatistics();

        //生成8_号码对照表
//        studentNo();
        //生成9_竞赛日程
//        raceDate(null,null);

        //生成10_分组分道表
        groupLine();

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
//        mergeDoc(srcDocxs, destDocx);


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
        studentMapper.updateNo();

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

                    XWPFRun manGroup = paragraph.createRun();
                    XWPFRun manStudent = paragraph.createRun();
                    XWPFRun womanGroup = paragraph.createRun();
                    XWPFRun womanStudent = paragraph.createRun();

                    manGroup.setText("男子"+ group.getName()+"：");
                    manGroup.addBreak();

                    boolean createdWoman = false;
                    String text = "",womanText = "";
                    int count = 1,womanCount = 1;
                    for (Student s : sList) {
                        if (s.getSex() == 1) {
                            if (!createdWoman) {
                                womanGroup.setText("女子"+ group.getName()+"：");
                                womanGroup.addBreak();
                                createdWoman = true;
                            }

                            if (womanCount == 5) {
                                womanStudent.setText(womanText);
                                womanStudent.addBreak();
                                womanText = s.getNo() + "  " + s.getName() + "\t\t";
                                womanCount = 1;
                            } else {
                                womanText += s.getNo() + "  " + s.getName() + "\t\t";
                            }
                            womanCount++;
                        }else{

                            if (count == 5) {
                                manStudent.setText(text);
                                manStudent.addBreak();
                                text = s.getNo() + "  " + s.getName() + "\t\t";
                                count = 1;
                            }else{
                                text += s.getNo() + "  " + s.getName() + "\t\t";
                            }
                            count++;
                        }
                    }
                    if (StringUtils.hasText(text)) {
                        manStudent.setText(text);
                        manStudent.addBreak();
                    }
                    if (StringUtils.hasText(womanText)) {
                        womanStudent.setText(womanText);
                        womanStudent.addBreak();
                    }

                }
            }

            doc.write(out);
            out.close();
            System.out.println("生成8_号码对照表.docx 成功");
        } catch (IOException e) {
            logger.error("创建号码对照表失败",e);
        }
    }

    private void raceDate(String srcPath, String destPath) {
        //从数据库查找统计数据
        List<Map<String,Object>> results = studentProjectMapper.selectStatistics();
        Map<String, String> map = new HashMap<String, String>();
        int count = 0;
        int sex = 0;
        String athletes = "";
        String nos = "";
        String classes = "";
        String project = "";
        String group = "";
        int p0n=0,p1n=0,p2n=0,p3n=0,p4n=0,p5n=0,p6n=0,p7n=0,p8n=0,p9n=0,p10n=0,
                _2p1n=0,_2p2n=0,_2p3n=0,_2p4n=0,_2p5n=0,_2p6n=0,_2p7n=0,_2p8n=0,_2p9n=0,_2p10n=0,_2p11n=0,_2p12n=0,_2p13n=0,
                _3p1n=0,_3p2n=0,_3p3n=0,_3p4n=0,_3p5n=0,_3p6n=0,_3p7n=0,_3p8n=0,_3p9n=0,_3p10n=0,
                _4p1n=0,_4p2n=0,_4p3n=0,_4p4n=0,_4p5n=0,_4p6n=0,_4p7n=0,_4p8n=0,
                _5p1n=0,_5p2n=0,_5p3n=0,_5p4n=0,_5p5n=0;

        for (Map<String, Object> result : results) {
            count = Integer.parseInt(result.get("count").toString());
            sex =  Integer.parseInt(result.get("sex").toString());
            athletes = result.get("athletes").toString();
            nos = result.get("nos").toString();
            classes = result.get("classes").toString();
            project = result.get("project").toString();
            group = result.get("group").toString();


            //把顺序打乱，重新传入map
            String[] names = athletes.split(",");
            String[] nol = nos.split(",");
            String[] classesl = classes.split(",");
            List<WordAthleteBean> list = new ArrayList<>(names.length);
            for (int i = 0; i < names.length; i++) {
                WordAthleteBean w = new WordAthleteBean();
                w.setNo(nol[i]);
                w.setName(names[i]);
                w.setClasses(classesl[i]);
                list.add(w);
            }
            Collections.shuffle(list);


            StringBuilder nAthletes = new StringBuilder();
            StringBuilder nNos  = new StringBuilder();
            StringBuilder nClasses = new StringBuilder();

            for (int i = 0; i < list.size(); i++) {
                nAthletes.append(list.get(i).getName())
                        .append((i==list.size()-1)? "":",");
                nNos.append(list.get(i).getNo())
                        .append((i==list.size()-1)? "":",");
                nClasses.append(list.get(i).getClasses())
                        .append((i==list.size()-1)? "":",");
            }

            //传入
            String info = nAthletes.toString()+";"+nNos.toString()+";"+nClasses.toString();

            if(group.equals("中职")){
                if (project.equals("男子100米")) {
                    p0n = count;
                    map.put("Anames0", info);
                } else if (project.equals("女子200米")) {
                    p2n = count;
                    map.put("Anames2", info);
                } else if (project.equals("男子800米")) {
                    p4n = count;
                    map.put("Anames4", info);
                } else if (project.equals("女子800米")) {
                    p6n = count;
                    map.put("Anames6", info);
                } else if (project.equals("女子跳远")) {
                    p9n = count;
                    map.put("Anames9", info);
                } else if (project.equals("男子跳高")) {
                    p10n = count;
                    map.put("Anames10", info);
                }
                /////////////////////////
                else if (project.equals("女子100米栏")) {
                    _2p1n = count;
                    map.put("2Anames1", info);
                } else if (project.equals("男子110米栏")) {
                    _2p3n = count;
                    map.put("2Anames3", info);
                } else if (project.equals("男子400米")) {
                    _2p5n = count;
                    map.put("2Anames5", info);
                } else if (project.equals("女子1500米")) {
                    _2p8n = count;
                    map.put("2Anames8", info);
                } else if (project.equals("男子1500米")) {
                    _2p9n = count;
                    map.put("2Anames9", info);
                } else if (project.equals("男子铅球")) {
                    _2p12n = count;
                    map.put("2Anames12", info);
                }
                /////////////////////////////
                else if (project.equals("女子100米")) {
                    _3p1n = count;
                    map.put("3Anames1", info);
                } else if (project.equals("男子200米")) {
                    _3p3n = count;
                    map.put("3Anames3", info);
                } else if (project.equals("女子400米")) {
                    _3p7n = count;
                    map.put("3Anames7", info);
                } else if (project.equals("男子三级跳远")) {
                    _3p9n = count;
                    map.put("3Anames9", info);
                }
                ///////////////////////////////////

                else if (project.equals("女子4×100接力")) {
                    _4p1n = count;
                    map.put("4Anames1", info);
                } else if (project.equals("男子4×400接力")) {
                    _4p3n = count;
                    map.put("4Anames3", info);
                } else if (project.equals("男子跳远")) {
                    _4p5n = count;
                    map.put("4Anames5", info);
                } else if (project.equals("女子铅球")) {
                    _4p7n = count;
                    map.put("4Anames7", info);
                }

                //////////////////////////////////////
                else if (project.equals("男子4×100接力")) {
                    _5p1n = count;
                    map.put("5Anames1", info);
                } else if (project.equals("男子5000米")) {
                    _5p3n = count;
                    map.put("5Anames3", info);
                } else if (project.equals("女子跳高")) {
                    _5p5n = count;
                    map.put("5Anames5", info);
                }



            }else if(group.equals("高职")){
                if (project.equals("男子100米")) {
                    p1n = count;
                    map.put("Anames1", info);
                } else if (project.equals("女子200米")) {
                    p3n = count;
                    map.put("Anames3", info);
                } else if (project.equals("男子800米")) {
                    p5n = count;
                    map.put("Anames5", info);
                } else if (project.equals("女子800米")) {
                    p7n = count;
                    map.put("Anames7", info);
                } else if (project.equals("男子铅球")) {
                    p8n = count;
                    map.put("Anames8", info);
                }
                //////////////////////////////////////
                else if (project.equals("女子100米栏")) {
                    _2p2n = count;
                    map.put("2Anames2", info);
                } else if (project.equals("男子110米栏")) {
                    _2p4n = count;
                    map.put("2Anames4", info);
                } else if (project.equals("男子400米")) {
                    _2p6n = count;
                    map.put("2Anames6", info);
                } else if (project.equals("女子1500米")) {
                    _2p7n = count;
                    map.put("2Anames7", info);
                } else if (project.equals("男子1500米")) {
                    _2p10n = count;
                    map.put("2Anames10", info);
                } else if (project.equals("女子跳远")) {
                    _2p11n = count;
                    map.put("2Anames11", info);
                } else if (project.equals("男子跳高")) {
                    _2p13n = count;
                    map.put("2Anames13", info);
                }
                ////////////////////////////////////////////
                else if (project.equals("女子100米")) {
                    _3p2n = count;
                    map.put("3Anames2", info);
                } else if (project.equals("男子200米")) {
                    _3p4n = count;
                    map.put("3Anames4", info);
                } else if (project.equals("女子3000米")) {
                    _3p5n = count;
                    map.put("3Anames5", info);
                } else if (project.equals("男子5000米")) {
                    _3p6n = count;
                    map.put("3Anames6", info);
                } else if (project.equals("女子400米")) {
                    _3p8n = count;
                    map.put("3Anames8", info);
                } else if (project.equals("女子铅球")) {
                    _3p10n = count;
                    map.put("3Anames10", info);
                }

                //////////////////////
                else if (project.equals("女子4×100接力")) {
                    _4p2n = count;
                    map.put("4Anames2", info);
                } else if (project.equals("男子4×400接力")) {
                    _4p4n = count;
                    map.put("4Anames4", info);
                } else if (project.equals("男子跳远")) {
                    _4p6n = count;
                    map.put("4Anames6", info);
                } else if (project.equals("女子跳高")) {
                    _4p8n = count;
                    map.put("4Anames8", info);
                }

                ///////////////////////////////
                else if (project.equals("男子4×100接力")) {
                    _5p2n = count;
                    map.put("5Anames2", info);
                } else if (project.equals("男子三级跳远")) {
                    _5p4n = count;
                    map.put("5Anames4", info);
                }
            }else{
                //todo 团体
            }

        }


        int[] numbers = {p0n,p1n,p2n,p3n,p4n,p5n,p6n,p7n,p8n,p9n,p10n,
                _2p1n,_2p2n,_2p3n,_2p4n,_2p5n,_2p6n,_2p7n,_2p8n,_2p9n,_2p10n,_2p11n,_2p12n,_2p13n,
                _3p1n,_3p2n,_3p3n,_3p4n,_3p5n,_3p6n,_3p7n,_3p8n,_3p9n,_3p10n,
                _4p1n,_4p2n,_4p3n,_4p4n,_4p5n,_4p6n,_4p7n,_4p8n,
                _5p1n,_5p2n,_5p3n,_5p4n,_5p5n};




        //替换日期、星期几
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
        SimpleDateFormat weekSdf = new SimpleDateFormat("EEEE");

        Calendar calendar = Calendar.getInstance();
        Date day1 = new Date();
        calendar.setTime(day1);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date day2 = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date day3 = calendar.getTime();


        map.put("day_one_1", sdf.format(day1));
        map.put("day_one_2", sdf.format(day1));
        map.put("day_two_1", sdf.format(day2));
        map.put("day_two_2", sdf.format(day2));
        map.put("day_3", sdf.format(day3));
        map.put("day_one_1_week", weekSdf.format(day1));
        map.put("day_one_2_week", weekSdf.format(day1));
        map.put("day_two_1_week", weekSdf.format(day2));
        map.put("day_two_2_week", weekSdf.format(day2));
        map.put("day_3_week", weekSdf.format(day3));



        //替换项目人数、分组数



        map.put("p0n", String.valueOf(numbers[0]));
        map.put("p0g", String.valueOf(new Double(Math.ceil(numbers[0]/8.0)).intValue()));
        map.put("p1n", String.valueOf(numbers[1]));
        map.put("p1g", String.valueOf(new Double(Math.ceil(numbers[1]/8.0)).intValue()));
        map.put("p2n", String.valueOf(numbers[2]));
        map.put("p2g", String.valueOf(new Double(Math.ceil(numbers[2]/8.0)).intValue()));
        map.put("p3n", String.valueOf(numbers[3]));
        map.put("p3g", String.valueOf(new Double(Math.ceil(numbers[3]/8.0)).intValue()));
        map.put("p4n", String.valueOf(numbers[4]));
        map.put("p4g", String.valueOf(new Double(Math.ceil(numbers[4]/8.0)).intValue()));
        map.put("p5n", String.valueOf(numbers[5]));
        map.put("p5g", String.valueOf(new Double(Math.ceil(numbers[5]/8.0)).intValue()));
        map.put("p6n", String.valueOf(numbers[6]));
        map.put("p6g", String.valueOf(new Double(Math.ceil(numbers[6]/8.0)).intValue()));
        map.put("p7n", String.valueOf(numbers[7]));
        map.put("p7g", String.valueOf(new Double(Math.ceil(numbers[7]/8.0)).intValue()));
        map.put("p8n", String.valueOf(numbers[8]));
        map.put("p9n", String.valueOf(numbers[9]));
        map.put("p10n", String.valueOf(numbers[10]));


        map.put("2p1n", String.valueOf(numbers[11]));
        map.put("2p1g", String.valueOf(new Double(Math.ceil(numbers[11]/8.0)).intValue()));
        map.put("2p2n", String.valueOf(numbers[12]));
        map.put("2p2g", String.valueOf(new Double(Math.ceil(numbers[12]/8.0)).intValue()));
        map.put("2p3n", String.valueOf(numbers[13]));
        map.put("2p3g", String.valueOf(new Double(Math.ceil(numbers[13]/8.0)).intValue()));
        map.put("2p4n", String.valueOf(numbers[14]));
        map.put("2p4g", String.valueOf(new Double(Math.ceil(numbers[14]/8.0)).intValue()));
        map.put("2p5n", String.valueOf(numbers[15]));
        map.put("2p5g", String.valueOf(new Double(Math.ceil(numbers[15]/8.0)).intValue()));
        map.put("2p6n", String.valueOf(numbers[16]));
        map.put("2p6g", String.valueOf(new Double(Math.ceil(numbers[16]/8.0)).intValue()));
        map.put("2p7n", String.valueOf(numbers[17]));
        map.put("2p7g", String.valueOf(new Double(Math.ceil(numbers[17]/25.0)).intValue()));
        map.put("2p8n", String.valueOf(numbers[18]));
        map.put("2p8g", String.valueOf(new Double(Math.ceil(numbers[18]/25.0)).intValue()));
        map.put("2p9n", String.valueOf(numbers[19]));
        map.put("2p9g", String.valueOf(new Double(Math.ceil(numbers[19]/25.0)).intValue()));
        map.put("2p10n", String.valueOf(numbers[20]));
        map.put("2p10g", String.valueOf(new Double(Math.ceil(numbers[20]/25.0)).intValue()));
        map.put("2p11n", String.valueOf(numbers[21]));
        map.put("2p12n", String.valueOf(numbers[22]));
        map.put("2p13n", String.valueOf(numbers[23]));


        map.put("3p1n", String.valueOf(numbers[24]));
        map.put("3p1g", String.valueOf(new Double(Math.ceil(numbers[24]/8.0)).intValue()));
        map.put("3p2n", String.valueOf(numbers[25]));
        map.put("3p2g", String.valueOf(new Double(Math.ceil(numbers[25]/8.0)).intValue()));
        map.put("3p3n", String.valueOf(numbers[26]));
        map.put("3p3g", String.valueOf(new Double(Math.ceil(numbers[26]/8.0)).intValue()));
        map.put("3p4n", String.valueOf(numbers[27]));
        map.put("3p4g", String.valueOf(new Double(Math.ceil(numbers[27]/8.0)).intValue()));
        map.put("3p5n", String.valueOf(numbers[28]));
        map.put("3p6n", String.valueOf(numbers[29]));
        map.put("3p7n", String.valueOf(numbers[30]));
        map.put("3p7g", String.valueOf(new Double(Math.ceil(numbers[30]/8.0)).intValue()));
        map.put("3p8n", String.valueOf(numbers[31]));
        map.put("3p8g", String.valueOf(new Double(Math.ceil(numbers[31]/8.0)).intValue()));
        map.put("3p9n", String.valueOf(numbers[32]));
        map.put("3p10n", String.valueOf(numbers[33]));

        map.put("4p1n", String.valueOf(numbers[34]));
        map.put("4p1g", String.valueOf(new Double(Math.ceil(numbers[34]/8.0)).intValue()));
        map.put("4p2n", String.valueOf(numbers[35]));
        map.put("4p2g", String.valueOf(new Double(Math.ceil(numbers[35]/8.0)).intValue()));
        map.put("4p3n", String.valueOf(numbers[36]));
        map.put("4p3g", String.valueOf(new Double(Math.ceil(numbers[36]/8.0)).intValue()));
        map.put("4p4n", String.valueOf(numbers[37]));
        map.put("4p4g", String.valueOf(new Double(Math.ceil(numbers[37]/8.0)).intValue()));
        map.put("4p5n", String.valueOf(numbers[38]));
        map.put("4p6n", String.valueOf(numbers[39]));
        map.put("4p7n", String.valueOf(numbers[40]));
        map.put("4p8n", String.valueOf(numbers[41]));


        map.put("5p1n", String.valueOf(numbers[42]));
        map.put("5p1g", String.valueOf(new Double(Math.ceil(numbers[42]/8.0)).intValue()));
        map.put("5p2n", String.valueOf(numbers[43]));
        map.put("5p2g", String.valueOf(new Double(Math.ceil(numbers[43]/8.0)).intValue()));
        map.put("5p3n", String.valueOf(numbers[44]));
        map.put("5p4n", String.valueOf(numbers[45]));
        map.put("5p5n", String.valueOf(numbers[46]));

        if (srcPath == null && destPath == null) {
            srcPath = "D:\\words\\9_竞赛日程_模板.docx";
            destPath = "D:\\words\\9_竞赛日程.docx";
            DocReplace.searchAndReplace(srcPath, destPath, map);

            logger.info("生成9_竞赛日程.docx 成功");
        } else{
            DocReplace.searchAndReplace2(srcPath, destPath, map);
            logger.info("生成10_分组分道表.docx 成功");
        }

    }

    private void groupLine() {

        raceDate("D:\\words\\10_分组分道表_模板.docx","D:\\words\\10_分组分道表.docx");
        /*
        try {
            //创建表格
            //Blank Document
            XWPFDocument doc= new XWPFDocument();

            //Write the Document in file system
            FileOutputStream out = new FileOutputStream(new File("d:\\words\\10_分组分道表.docx"));
            //添加标题
            XWPFParagraph titleParagraph = doc.createParagraph();
            //设置段落居中
            titleParagraph.setAlignment(ParagraphAlignment.CENTER);

            XWPFRun titleParagraphRun = titleParagraph.createRun();
            titleParagraphRun.setText("运动员分组分道表");
            titleParagraphRun.setColor("000000");
            titleParagraphRun.setFontSize(20);
            titleParagraphRun.setBold(true);
            titleParagraphRun.addBreak();

            String[] units = {"第一单元","第二单元","第三单元","第四单元"};
            String[] types = {"<径赛>","<田赛>"};
            XWPFParagraph paragraph;
            XWPFParagraph paragraph2;
            for (String unit : units) {
                paragraph = doc.createParagraph();
                paragraph.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun unitRun = paragraph.createRun();
                unitRun.setText(unit);
                unitRun.setFontSize(18);
                unitRun.setBold(true);
                unitRun.addBreak();

                for (String type : types) {
                    XWPFRun typeRun = paragraph.createRun();
                    typeRun.setText(type);
                    typeRun.setFontSize(14);
                    typeRun.setBold(true);
                    typeRun.addBreak();

                    //print head 打印头
                    paragraph2 = doc.createParagraph();
                    paragraph2.setAlignment(ParagraphAlignment.LEFT);
                    XWPFRun headRun = paragraph2.createRun();
                    headRun.setText("组│\t一道\t\t二道\t\t三道\t\t四道\t\t五道\t\t六道\t\t七道\t\t八道");
                    headRun.addBreak();
                    headRun = paragraph2.createRun();
                    headRun.setText("─╂──────────────────────────────────────");
                    headRun.addBreak();
                    for (int i = 0; i <5; i++) {
                        XWPFRun hr = paragraph2.createRun();
                        hr.setText(i + " │ ");
                        hr.addBreak();
                    }

                }

            }





            doc.write(out);
            out.close();
            System.out.println("生成10_分组分道表.docx 成功");
        } catch (IOException e) {
            logger.error("生成10_分组分道表.docx 失败",e);
        }*/


    }
}
