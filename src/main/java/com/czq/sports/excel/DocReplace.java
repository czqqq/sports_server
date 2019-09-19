package com.czq.sports.excel;


import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.*;

import javax.xml.crypto.Data;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

public class DocReplace {

    public static void searchAndReplace(String srcPath, String destPath, Map<String, String> map) {
        try {
            XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(srcPath));
            /**
             * 替换段落中的指定文字
             */
            Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();
            while (itPara.hasNext()) {
                XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
                Set<String> set = map.keySet();
                Iterator<String> iterator = set.iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    List<XWPFRun> run = paragraph.getRuns();
                    for (int i = 0; i < run.size(); i++) {
                        if (run.get(i).getText(run.get(i).getTextPosition()) != null &&
                                run.get(i).getText(run.get(i).getTextPosition()).equals(key)) {
                            /**
                             * 参数0表示生成的文字是要从哪一个地方开始放置,设置文字从位置0开始
                             * 就可以把原来的文字全部替换掉了
                             */
                            run.get(i).setText(map.get(key), 0);
                        }
                    }
                }
            }

            /**
             * 替换表格中的指定文字
             */
            Iterator<XWPFTable> itTable = document.getTablesIterator();
            while (itTable.hasNext()) {
                XWPFTable table = (XWPFTable) itTable.next();
                int count = table.getNumberOfRows();
                for (int i = 0; i < count; i++) {
                    XWPFTableRow row = table.getRow(i);
                    List<XWPFTableCell> cells = row.getTableCells();
                    for (XWPFTableCell cell : cells) {
                        for (Entry<String, String> e : map.entrySet()) {
                            if (cell.getText().equals(e.getKey())) {
                                cell.removeParagraph(0);
                                cell.setText(e.getValue());
                            }
                        }
                    }
                }
            }
            FileOutputStream outStream = null;
            outStream = new FileOutputStream(destPath);
            document.write(outStream);
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {


        //替换日期、星期几
/*        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
        SimpleDateFormat weekSdf = new SimpleDateFormat("EEEE");

        Calendar calendar = Calendar.getInstance();
        Date day1 = new Date();
        calendar.setTime(day1);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date day2 = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date day3 = calendar.getTime();

        Map<String, String> map = new HashMap<String, String>();
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

        int[] numbers = {51,30,11,8,8,21,33,14,8,8};

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
        map.put("p8g", String.valueOf(new Double(Math.ceil(numbers[8]/8.0)).intValue()));
        map.put("p9n", String.valueOf(numbers[9]));
        map.put("p9g", String.valueOf(new Double(Math.ceil(numbers[9]/8.0)).intValue()));





        String srcPath = "D:\\words\\9_竞赛日程_模板.docx";
        String destPath = "D:\\words\\9_竞赛日程.docx";
        searchAndReplace(srcPath, destPath, map);*/
    }
}