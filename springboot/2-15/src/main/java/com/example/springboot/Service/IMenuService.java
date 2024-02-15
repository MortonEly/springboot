package com.example.springboot.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.entity.Menu;

import java.util.List;

/**
 * <p>
 * 系统菜单表 服务类
 * </p>
 *
 * @author lv
 * @since 2024-01-17
 */
public interface IMenuService extends IService<Menu> {
    /**
     * 查询数据库数据，并处理后返回 树形数据
     * @return 树形数据
     */
    List<Menu> findAll(String name);
}
