package com.czq.sports.excel;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.xmlbeans.XmlOptions;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
/**
 * 合并两个docx文档方法，对文档包含的图片无效
 * @author laitong.ma
 * @date 2018年9月17日
 */
public class POIMergeDocUtil {

    public static void main(String[] args) {
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
        mergeDoc(srcDocxs, destDocx);
    }


    /**
     * 合并docx文件
     *
     * @param srcDocxs 需要合并的目标docx文件
     * @param destDocx 合并后的docx输出文件
     */
    public static void mergeDoc(String[] srcDocxs, String destDocx) {

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
    private static void appendBody(CTBody src, CTBody append) throws Exception {
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
}