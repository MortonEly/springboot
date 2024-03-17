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

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Auther: 吕宏博
 * @Date: 2024--03--02--15:23
 * @Description:
 */
@ApiModel(value = "文章对象", description = "系统文章表")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("message")
public class Message {

        @ApiModelProperty("评论ID")
        @TableId(type = IdType.AUTO)
        private Integer id;

        @ApiModelProperty("用户名")
        @TableField(value = "username")
        private String username;


        @ApiModelProperty("外部关联ID，根据实际需求使用")
        @TableField(value = "foreign_id")
        private String foreignId;

        @ApiModelProperty("父留言ID，用于实现留言回复功能")
        @TableField(value = "parent_id")
        private String parentId;

        @ApiModelProperty("文章内容")
        @TableField(value = "content")
        private String content;

        @ApiModelProperty("文章或者某一个内容 id （ 一个文章多条评论）")
        @TableField(value = "item_id")
        private String itemId;  // 文章或者某一个内容 id （ 一个文章多条评论）
        @ApiModelProperty("文件创建时间")
        @TableField(value = "create_time")
        private Date createTime;

        @ApiModelProperty("是否已删除")
        @TableField(value = "is_deleted")
        private Integer isDeleted;

        @TableField(exist = false)
        @ApiModelProperty("子评论")
        private List<Message> child;    // 本评论下的子评论     子 一个父回复有多个子回复

        @TableField(value = "rate")
        private BigDecimal rate;

}
