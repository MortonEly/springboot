package com.example.springboot.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_log")
public class SysLog {
    @ApiModelProperty("文章id")
    @TableId(type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("用户名")
    private String  username;

    private String record;

    private String type;
    @ApiModelProperty("创建时间")
    private Date createTime;
}
