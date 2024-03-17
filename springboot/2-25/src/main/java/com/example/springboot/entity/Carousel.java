package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @Auther: 吕宏博
 * @Date: 2024--02--26--21:50
 * @Description:
 */
@ApiModel(value = "SysDict对象", description = "系统字典表")
@ToString
@Data
@TableName("Carousel")
public class Carousel {
    @ApiModelProperty("id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("轮播图名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("轮播图路径")
    @TableField("url")
    private String url;

    @ApiModelProperty("图片描述")
    @TableField("description")
    private String description;

}
