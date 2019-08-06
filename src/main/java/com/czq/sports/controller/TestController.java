package com.czq.sports.controller;

import com.czq.sports.utils.BaseResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/get_table_data")
    public BaseResult getTableData() {
        BaseResult res = new BaseResult();
        Map<String, String> data = new HashMap<>();
        data.put("name","陈皮");
        data.put("email","123@666.com");
        data.put("createTime","2019-01-01");
        res.setData(data);
        return res;
    }


    @RequestMapping("/get_drag_list")
    public BaseResult getDragList() {
        BaseResult res = new BaseResult();
        Map<String, String> data = new HashMap<>();
        data.put("name","陈皮");
        data.put("id", "5");
        res.setData(data);
        return res;
    }

    @RequestMapping("/save_error_logger")
    public BaseResult saveErrorLogger() {
        return new BaseResult();
    }



}
