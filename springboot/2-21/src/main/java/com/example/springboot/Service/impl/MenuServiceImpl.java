package com.example.springboot.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.Mapper.MenuMapper;
import com.example.springboot.Service.IMenuService;
import com.example.springboot.entity.Menu;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    /**
     * 能分页
     * */
    @Override
    public List<Menu> findAll(Integer pageNum, Integer pageSize, String name) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort_num");

        if (StringUtils.isNotEmpty(name)){
            queryWrapper.like("name", name);
        }

        Page<Menu> page = new Page<>(pageNum, pageSize);
        IPage<Menu> menuPage = this.page(page, queryWrapper);

        List<Menu> menuList = menuPage.getRecords();

        // 构建一级菜单
        List<Menu> parentMenuList = menuList.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
        for (Menu parentMenu : parentMenuList) {
            List<Menu> childMenuList = menuList.stream()
                    .filter(menu -> menu.getPid() != null && menu.getPid().equals(parentMenu.getId()))
                    .collect(Collectors.toList());
            // 处理子菜单逻辑...
            // 将子菜单设置到父菜单中
            parentMenu.setChildren(childMenuList);
        }

        return parentMenuList;

    }
    /***
     *不分页
     *
     *  * 查询所有菜单信息，并将其按照层级关系组织成树形结构。
     *  *
     *  * @param name 菜单名称（可选）
     *  * @return 所有菜单的树形结构列表
     *
     */
    @Override
    public List<Menu> findAl(String name) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort_num");
        // 如果传入了菜单名称，则使用 like 条件进行模糊查询
        if (StringUtils.isNotEmpty(name)){
            queryWrapper.like("name",name);
        }
        // 查询所有菜单信息，并按照 sort_num 字段升序排序
        List<Menu> MenuList = this.baseMapper.selectList(queryWrapper);//menu全部菜单信息
       //构建一级菜单
        // 将菜单按照层级关系组织成树形结构
        List<Menu> ParentMenuList=MenuList.stream().filter(menu -> menu.getPid()==null).collect(Collectors.toList());
        for (Menu ParentMenu : ParentMenuList) {
            List<Menu> childMenuList = MenuList.stream()
                    .filter(menu -> menu.getPid() != null && menu.getPid().equals(ParentMenu.getId()))
                    .collect(Collectors.toList());
            // 处理子菜单逻辑...
            // 将子菜单设置到父菜单中
            ParentMenu.setChildren(childMenuList);
        }
        // 返回所有一级菜单的列表
        return ParentMenuList;
    }


}
