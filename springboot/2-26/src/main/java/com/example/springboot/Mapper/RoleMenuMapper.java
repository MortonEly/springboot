package com.example.springboot.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.RoleMenu;
import org.apache.ibatis.annotations.Mapper;
/**
 * @Auther: 吕宏博
 * @Date: 2024--03--01--10:05
 * @Description:    角色--菜单
 */

@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

}
