package com.czq.sports.controller;

import com.czq.sports.utils.BaseResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
public class ExcelController {

    @RequestMapping("uploadExcel")
    public BaseResult uploadExcel(@RequestParam("file") MultipartFile file){
        if(file == null){
            System.out.println("上传excel错误，文件为空");
        }


        return new BaseResult();
    }

    

}
