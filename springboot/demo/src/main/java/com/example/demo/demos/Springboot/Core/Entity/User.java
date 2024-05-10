package com.example.demo.demos.Springboot.Core.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: 吕宏博
 * @Date: 2024--03--21--15:30
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 用户id
     */

    @ApiModelProperty("用户id")
    private Long id;

    /**
     * 用户名
     */
    @Column(name="username")
    @ApiModelProperty("用户名")
    private String username;

    /**
     * 加密后的密码
     */
    @Column(name="password")
    @ApiModelProperty("加密后的密码")
    private String password;

    /**
     * 创建时间
     */
    @Column(name="created_time")
    @ApiModelProperty("创建时间")
    private Date createdTime;

    /**
     * 是否启用: 1 - 启用, 0 - 禁止
     */
    @Column(name="is_active")
    @ApiModelProperty("是否启用: 1 - 启用, 0 - 禁止")
    private Integer isActive;
}
