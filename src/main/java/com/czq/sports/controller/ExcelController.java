package com.czq.sports.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.czq.sports.excel.DownloadData;
import com.czq.sports.excel.UploadData;
import com.czq.sports.excel.UploadDataListener;
import com.czq.sports.utils.BaseResult;
import com.czq.sports.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/upload")
public class ExcelController {

    @Autowired
    private UploadDataListener uploadDataListener;

    @RequestMapping("uploadExcel")
    public BaseResult uploadExcel(@RequestParam("file") MultipartFile file) {
        BaseResult result = new BaseResult();
        if (file == null) {
            result.setMsg("上传excel错误，文件为空");
            result.setCode(ResultCode.FAILURE);
        } else {
            try {

//                List<String[]> resultr = POIUtil.readExcel(file);
//                System.out.println(resultr);
//                EasyExcel.read(file.getInputStream(), UploadData.class, uploadDataListener).sheet().headRowNumber(7).doRead();

                ExcelReader excelReader = EasyExcel.read(file.getInputStream(), UploadData.class, uploadDataListener).build();

                ReadSheet readSheet = EasyExcel.readSheet(0).headRowNumber(7).build();
                excelReader.read(readSheet);
                excelReader.finish();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 文件下载
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DownloadData}
     * <p>
     * 2. 设置返回的 参数
     * <p>
     * 3. 直接写，这里注意，finish的时候会自动关闭OutputStream,当然你外面再关闭流问题不大
     */
    @GetMapping("download")
    public void download(HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=demo.xlsx");
        try {
            EasyExcel.write(response.getOutputStream(), DownloadData.class).sheet("模板").doWrite(data());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件上传
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link UploadData}
     * <p>
     * 2. 由于默认异步读取excel，所以需要创建excel一行一行的回调监听器，参照{@link UploadDataListener}
     * <p>
     * 3. 直接读即可
     */
    @PostMapping("upload")
    public String upload(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), UploadData.class, new UploadDataListener()).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }

    private List<DownloadData> data() {
        List<DownloadData> list = new ArrayList<DownloadData>();
        for (int i = 0; i < 10; i++) {
            DownloadData data = new DownloadData();
            data.setString("字符串" + 0);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }

    /**
     * 读多个sheet,这里注意一个sheet不能读取多次，多次读取需要重新读取文件
     * <p>1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>2. 由于默认异步读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoDataListener}
     * <p>3. 直接读即可
     */
  /*  @Test
    public void repeatedRead() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        ExcelReader excelReader = EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).build();
        ReadSheet readSheet1 = EasyExcel.readSheet(0).build();
        ReadSheet readSheet2 = EasyExcel.readSheet(1).build();
        excelReader.read(readSheet1);
        excelReader.read(readSheet2);
        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();
    }*/

}
