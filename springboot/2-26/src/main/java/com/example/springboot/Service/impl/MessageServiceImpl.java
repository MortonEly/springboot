package com.example.springboot.Service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.Mapper.MessageMapper;
import com.example.springboot.Mapper.UserMapper;
import com.example.springboot.Service.IMessageService;
import com.example.springboot.entity.Message;
import com.example.springboot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 吕宏博
 * @Date: 2024--03--02--15:38
 * @Description:
 */

@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {
    @Override
    public List<Message> processMessagesToTree() {
        // 获取所有留言列表
        List<Message> list = this.list();
        // 初始化一个HashMap用于存储留言ID和留言对象的映射，方便后续通过ID查找留言对象
        Map<Integer, Message> map = new HashMap<>();
        // 初始化一个列表用于存储最终的树形结构的根留言
        List<Message> output = new ArrayList<>();
        // 遍历所有留言，将每个留言对象添加到map中，并识别出根留言添加到output列表中
        for (Message message : list) {
            // 判断当前留言是否为根留言（没有父留言）
            if (message.getParentId() == null || message.getParentId().isEmpty()) {
                output.add(message); // 是根留言则添加到output列表中
            }
            map.put(message.getId(), message); // 将留言ID与留言对象的映射关系添加到map中
        }
        // 再次遍历所有留言，建立留言之间的父子关系
        for (Message message : list) {
            String parentId = message.getParentId(); // 获取当前留言的父留言ID
            // 如果当前留言有父留言
            if (parentId != null && !parentId.isEmpty()) {
                // 从map中找到父留言对象
                Message parentMessage = map.get(Integer.parseInt(parentId));
                // 如果父留言的子留言列表未初始化，则创建一个新的ArrayList
                if (parentMessage.getChild() == null) {
                    parentMessage.setChild(new ArrayList<>());
                }
                // 将当前留言添加到其父留言的子留言列表中
                parentMessage.getChild().add(message);
            }
        }
        // 返回包含树形结构根留言的列表
        return output;
    }
    /**
     * 获取评论信息的评分结果
     * @param foreignId 外键ID
     * @return 包含评分结果的Map
     */


}
