package com.example.springboot.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.Mapper.PartTimeMapper;
import com.example.springboot.Service.IPartTimeService;
import com.example.springboot.entity.PartTimeJob;
import org.springframework.stereotype.Service;

/**
 * @Auther: 吕宏博
 * @Date: 2024--03--04--22:24
 * @Description:
 */
@Service
public class PartTimeServiceImpl extends ServiceImpl<PartTimeMapper, PartTimeJob> implements IPartTimeService {
}
