package com.example.springboot.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.entity.Message;

import java.util.List;


/**
 * @Auther: 吕宏博
 * @Date: 2024--03--02--15:38
 * @Description:
 */
public interface IMessageService extends IService<Message> {
    List<Message> processMessagesToTree();


}
