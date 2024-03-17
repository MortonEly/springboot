package com.example.springboot.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Auther: 吕宏博
 * @Date: 2024--03--02--15:37
 * @Description: 消息
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {

}
