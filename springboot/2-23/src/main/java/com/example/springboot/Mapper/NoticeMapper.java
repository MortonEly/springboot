package com.example.springboot.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.Notice;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Auther: 吕宏博
 * @Date: 2024--02--25--14:06
 * @Description:
 */
@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {
}
