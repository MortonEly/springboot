package com.example.springboot.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.PartTimeJob;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Auther: 吕宏博
 * @Date: 2024--03--04--22:20
 * @Description:    工作情况
 */
@Mapper
public interface PartTimeMapper extends BaseMapper<PartTimeJob> {
}
