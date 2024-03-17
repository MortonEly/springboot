package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel(value = "文件对象", description = "系统字典表")
@Data
@TableName("sys_file")
public class FilesClass {
    @ApiModelProperty("id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField(value = "name")
    @ApiModelProperty("文件名")
    private String name;

    @TableField(value = "type")
    @ApiModelProperty("文件类型")
    private String type;

    @TableField(value = "size")
    @ApiModelProperty("文件大小")
    private Long size;

    @TableField(value = "url")
    @ApiModelProperty("文件大小")
    private String url;

    @TableField(value = "is_delete")
    @ApiModelProperty("文件大小")
    private Integer isDelete;

    @TableField(value = "enable")
    @ApiModelProperty("文件大小")
    private Integer enable;

    @TableField(value = "md5")
    @ApiModelProperty("文件大小")
    private String md5;

}
