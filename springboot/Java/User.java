package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//@Data注解是lombok的注解，简化实体类编写，自动生成get/set以及toString等方法
//1.@table和@entity注解的区别
//
//@entity当实体类名和表名相同，实体属性和表字段相同时，会默认完成实体属性和表字段的映射，当表名和实体类名不同，表字段和实体属性不同时，需要使用@table和@column注解完成实体和表直接的映射
//
//2.@table注解默认的情况下只会完成表和实体之间的映射，但是当在application,yml文件中填加了配置之后就会创建表并完成映射
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user1")
@TableName(value = "user1")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String phone;
    private String address;
    /**
     * @TableField(value = "")
     *     这是Java注解@TableField的使用示例，可以用于定义实体类字段和数据库表字段的映射关系。
     *
     *     在注解@TableField中，value属性用于指定实体类字段与数据库表字段的对应关系。你需要将对应的数据库表字段名作为value属性的值，例如：
     *
     *
     *     @TableField(value = "column_name")
     *     private String fieldName;
     *     这样就将实体类中的fieldName字段与数据库表中的"column_name"字段进行了映射。
     */
    private String avatar;
}
