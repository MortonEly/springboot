package com.example.springboot.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.Dict;

import org.apache.ibatis.annotations.Mapper;



@Mapper
public interface DictMapper extends BaseMapper<Dict> {
}