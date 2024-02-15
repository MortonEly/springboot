package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@ApiModel(value = "SysDict对象", description = "系统字典表")
@ToString
@Data
@TableName("sys_dict")
public class Dict {

        @ApiModelProperty("id")
        @TableId(type = IdType.AUTO)
        private Integer id;
        @ApiModelProperty("name")
        private String name;
        private String value;
        private String type;


}
