package com.czq.sports.controller;

import com.czq.sports.service.WordService;
import com.czq.sports.utils.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/word")
public class WordController {

    @Autowired
    private WordService wordService;


    @RequestMapping("generate")
    public BaseResult generate() {
        BaseResult result = new BaseResult();
        wordService.generateWord();
        return result;
    }



}
