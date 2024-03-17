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

        /***
         * CREATE TABLE `message` (
         *   `id` int NOT NULL AUTO_INCREMENT COMMENT '评论ID',
         *   `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户名',
         *   `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '留言内容',
         *   `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
         *   `parent_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '父留言ID，用于实现留言回复功能\r\n父评论ID（被回复的评论）',
         *   `foreign_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '0' COMMENT '根评论id',
         *   `is_deleted` tinyint DEFAULT '0' COMMENT '是否已删除',
         *   PRIMARY KEY (`id`)
         * ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
         */
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

        @ApiModelProperty("文件创建时间")
        @TableField(value = "create_time")
        private Date createTime;

        @ApiModelProperty("是否已删除")
        @TableField(value = "is_deleted")
        private Integer isDeleted;

        @TableField(exist = false)
        @ApiModelProperty("子评论")
        private List<Message> child;    // 本评论下的子评论

}
