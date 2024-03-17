package com.example.springboot.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.Mapper.NoticeMapper;
import com.example.springboot.Service.INoticeService;
import com.example.springboot.entity.Notice;
import org.springframework.stereotype.Service;

/**
 * @Auther: 吕宏博
 * @Date: 2024--02--25--14:09
 * @Description:
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {
}
