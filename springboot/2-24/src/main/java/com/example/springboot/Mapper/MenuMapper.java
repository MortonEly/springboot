package com.example.springboot.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    //@Select("SELECT * FROM sys_menu WHERE id IN (SELECT menu_id FROM sys_role_menu WHERE role_id = #{roleId}) ORDER BY sort_num")
    @Select("SELECT id, path FROM sys_menu WHERE id IN (SELECT menu_id FROM sys_role_menu WHERE role_id = #{roleId})")
    List<Menu> selectByIdList(@Param(value="roleId") Integer roleId);
}
