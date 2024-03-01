package com.example.springboot.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.Mapper.ArticleMapper;
import com.example.springboot.Service.IArticleService;
import com.example.springboot.entity.Article;
import org.springframework.stereotype.Service;

/**
 * @Auther: 吕宏博
 * @Date: 2024--02--23--16:12
 * @Description:
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {
}
