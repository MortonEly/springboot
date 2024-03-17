package com.example.springboot.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.Dict;

import org.apache.ibatis.annotations.Mapper;

/**
 * @Auther: 吕宏博
 * @Date: 2024--03--01--10:05
 * @Description:    字典
 */

@Mapper
public interface DictMapper extends BaseMapper<Dict> {
}