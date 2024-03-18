package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Auther: 吕宏博
 * @Date: 2024--02--24--15:07
 * @Description:
 */

@Data
@TableName("notice")
@ApiModel(value = "公告对象", description = "系统公告表")
@ToString
public class Notice {


    @TableId(type = IdType.AUTO)
    private Integer id;


    @TableField(value = "title")
    private String title;

    @TableField(value = "content")
    private String content;

    @TableField(value = "user")
    private String user;

    @TableField(value = "url")
    private String url;


    @TableField(value = "create_time")
    private Date createTime;
}
