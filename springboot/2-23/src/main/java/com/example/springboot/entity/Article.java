package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Auther: 吕宏博
 * @Date: 2024--02--23--15:16
 * @Description:文章实体类
 */

@ApiModel(value = "文章对象", description = "系统文章表")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("article")
public class Article {
    @ApiModelProperty("id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("文章名字")
    @TableField(value = "name")
    private String name;

    @ApiModelProperty("文章类型")
    @TableField(value = "type")
    private String type;

    @ApiModelProperty("创建用户")
    @TableField(value = "user")
    private String user;

    @ApiModelProperty("封面url")
    @TableField(value = "url")
    private String url;

    @ApiModelProperty("文章内容")
    @TableField(value = "content")
    private String content;

    @ApiModelProperty("文件创建时间")
    @TableField(value = "create_time")
    private Date createTime;
}
