package com.example.springboot.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.Common.Result;
import com.example.springboot.entity.Role;
import com.example.springboot.entity.RoleMenu;

import java.util.List;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lv
 * @since 2023-10-22
 */
public interface IRoleService extends IService<Role> {
    Result saveRoleMenu(Integer roleId, List<Integer> menuIds);

    List<RoleMenu> selectMenuByRole(Integer roleId);
}
