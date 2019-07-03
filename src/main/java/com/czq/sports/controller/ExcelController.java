package com.czq.sports.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ExcelController {

    @RequestMapping("uploadExcel")
    public void uploadExcel(@RequestParam("fileName") MultipartFile file){
        if(file.isEmpty()){
            System.out.println("错误");
        }
    }

    

}
