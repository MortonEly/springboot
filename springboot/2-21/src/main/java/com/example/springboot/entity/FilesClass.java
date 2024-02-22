package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;

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
    @ApiModelProperty("文件名")
    private String name;
    @ApiModelProperty("文件类型")
    private String type;
    @ApiModelProperty("文件大小")
    private Long size;
    private String url;
    private Boolean isDelete;
    private Boolean enable;
    private String md5;

}
