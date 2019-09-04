package com.czq.sports.excel;

import lombok.Data;

import java.util.Date;

/**
 * 基础数据类
 *
 **/
@Data
public class UploadData {
    private String groupName;
    private String projectName;
    private String athletes1;
    private String athletes2;
    private String limit;
    private String remark;
}


/**
 *
 * 组  别	项  目	运动员a	运动员b	限报人数	备   注
 * 学生男子	100米	        		    2
 * 学生男子	200米			            2
 */