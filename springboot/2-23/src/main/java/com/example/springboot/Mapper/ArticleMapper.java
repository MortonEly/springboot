package com.example.springboot.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.Article;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Auther: 吕宏博
 * @Date: 2024--02--23--16:11
 * @Description:
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}
