package Com.Demo.Core.SpareResources;

import Com.Demo.Core.Entity.*;
import Com.Demo.Core.Exception.BusinessException;
import Com.Demo.Core.Mapper.*;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Auther: 吕宏博
 * @Date: 2024--10--26--16:15
 * @Description:    从数据库中获取用户信息,将用户信息送到SpringSecurity上下文中
 */
//    @Transactional
//    @Service
//    public class UserRolePerDetailsServiceImpl implements UserDetailsService {
//        @Autowired
//        private UserMapper userMapper;
//
//        @Autowired
//        private RoleMapper roleMapper;
//
//        @Autowired
//        private UserRoleMapper userRoleMapper;
//
//        @Autowired
//        private PermissionMapper permissionMapper;
//
//        @Autowired
//        private RolePermissionMapper rolePermissionMapper;
//
//        @Override
//        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//            // 查询用户信息
//            SysUser sysUser=userMapper.selectByNameAll(username);
//            if (Objects.isNull(sysUser)){
//                throw  new BusinessException(401,"用户不存在");
//            }
//            // 获取当前用户角色ID列表
//            List<Integer> roleIdList = userRoleMapper.getRoleIdsByUserId(sysUser.getId());
//
//            // 当前用户的角色名称列表
//            List<String> roleNames = new ArrayList<>();
//            // 当前用户的权限名称列表
//            List<String> permissionNames = new ArrayList<>();
//
//            if (!CollectionUtils.isEmpty(roleIdList)) {
//                // 获取角色名称列表
//                List<SysRole> roles = roleMapper.getRolesByRoleIds(roleIdList);
//                roleNames = roles.stream().map(SysRole::getRoleName).collect(Collectors.toList());
//
//                // 根据角色 ID 列表获取角色权限列表
//                List<SysRolePermission> rolePermissions = rolePermissionMapper.getPermissionListByRoleIds(roleIdList);
//                List<Integer> permissionIds = rolePermissions.stream()
//                        .map(SysRolePermission::getPermissionId)
//                        .collect(Collectors.toList());
//
//                // 根据权限ID列表获取权限名称
//                if (!CollectionUtils.isEmpty(permissionIds)) {
//                    List<SysPermission> permissions = permissionMapper.getPermissionsByIds(permissionIds);
//                    permissionNames = permissions.stream()
//                            .map(SysPermission::getPermissionName)
//                            .collect(Collectors.toList());
//                }
//            }
//
//            return new LoginUser(sysUser, roleNames, permissionNames);
//        }
//    }
