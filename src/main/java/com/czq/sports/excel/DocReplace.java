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
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
        Calendar calendar = Calendar.getInstance();
        Date day1 = new Date();
        calendar.setTime(day1);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date day2 = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date day3 = calendar.getTime();

        Map<String, String> map = new HashMap<String, String>();
        map.put("${name}", "hello");
        map.put("${day_one_1}", sdf.format(day1));
        map.put("${day_one_2}", sdf.format(day1));
        map.put("${day_two_1}", sdf.format(day2));
        map.put("${day_two_2}", sdf.format(day2));
        map.put("${day_three_1}", sdf.format(day3));

        String srcPath = "D:\\words\\9_竞赛日程_模板.docx";
        String destPath = "D:\\words\\9_竞赛日程.docx";
        searchAndReplace(srcPath, destPath, map);
        //https://www.jianshu.com/p/fbac813063fd
    }
}