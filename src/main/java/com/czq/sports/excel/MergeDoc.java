package com.czq.sports.excel;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.xmlbeans.XmlOptions;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MergeDoc {


    public static void main (String[] args) throws Exception {
        InputStream in1 = null;
        InputStream in2 = null;
        OPCPackage src1Package = null;
        OPCPackage src2Package = null;

        OutputStream dest = new FileOutputStream("d:\\words\\dest.docx");
        try {
            in1 = new FileInputStream("d:\\words\\0_目录.docx");
            in2 = new FileInputStream("d:\\words\\1_竞赛规程.docx");
            src1Package = OPCPackage.open(in1);
            src2Package = OPCPackage.open(in2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        XWPFDocument src1Document = new XWPFDocument(src1Package);
        CTBody src1Body = src1Document.getDocument().getBody();
        XWPFParagraph p = src1Document.createParagraph();
        //设置分页符
        p.setPageBreak(true);
        XWPFDocument src2Document = new XWPFDocument(src2Package);
        CTBody src2Body = src2Document.getDocument().getBody();
        appendBody(src1Body, src2Body);
        src1Document.write(dest);

    }

    private static void appendBody(CTBody src, CTBody append) throws Exception {
        XmlOptions optionsOuter = new XmlOptions();
        optionsOuter.setSaveOuter();
        String appendString = append.xmlText(optionsOuter);
        String srcString = src.xmlText();
        String prefix = srcString.substring(0,srcString.indexOf(">")+1);
        String mainPart = srcString.substring(srcString.indexOf(">")+1,srcString.lastIndexOf("<"));
        String sufix = srcString.substring( srcString.lastIndexOf("<") );
        String addPart = appendString.substring(appendString.indexOf(">") + 1, appendString.lastIndexOf("<"));
        CTBody makeBody = CTBody.Factory.parse(prefix+mainPart+addPart+sufix);
        src.set(makeBody);
    }
}
