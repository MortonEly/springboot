package com.example.springboot.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Auther: 吕宏博
 * @Date: 2024--03--04--10:18
 * @Description:
 */
@Data
@TableName("part_time_jobs")
@ApiModel(value = "工作对象", description = "系统工作表")
@ToString
public class PartTimeJob {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("ID名")
    private Integer id;

    @TableField(value = "title")
    @ApiModelProperty("兼职名称")
    private String title;

    @TableField(value = "description")
    @ApiModelProperty("工作内容描述")
    private String description;

    @TableField(value = "location")
    @ApiModelProperty("工作地点")
    private String location;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty("创建时间")
    @TableField(value = "create_time")
    private Date createTime;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("更新时间")
    @TableField(value = "update_time")
    private Date updateTime;

    @ApiModelProperty("是否已删除")
    @TableField(value = "is_deleted")
    private Integer isDeleted;

    @ApiModelProperty("薪资")
    @TableField(value = "salary")
    private  Long salary;
}
