//package Com.Demo.Core.Config;
//
//import Com.Demo.Core.Entity.LoginUser;
//import Com.Demo.Core.Entity.SysUser;
//import Com.Demo.Core.Exception.BusinessException;
//import Com.Demo.Core.Mapper.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.xml.ws.Action;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
///**
// * @Auther: 吕宏博
// * @Date: 2024--10--28--19:59
// * @Description:
// */
//@Service
//public class UserDetailServiceImpl implements UserDetailsService {
//
//    @Autowired
//    private UserMapper userMapper;
//
//    @Autowired
//    private RoleMapper roleMapper;
//
//    @Autowired
//    private UserRoleMapper userRoleMapper;
//
//
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // 查询用户信息
//        SysUser sysUser = userMapper.selectByNameAll(username);
//        if (Objects.isNull(sysUser)) {
//            throw new UsernameNotFoundException("用户不存在");
//        }
//        // 查询用户ID
//        Integer userId = sysUser.getId(); // 假设 sysUser 有一个 getId() 方法
//        if (userId == null) {
//            throw new UsernameNotFoundException("用户不存在");
//        }
//
//        // 查询用户角色ID
//        Integer roleId = userRoleMapper.selectRoleIdByUserId(userId);
//        if (roleId == null) {
//            throw new UsernameNotFoundException("用户没有角色");
//        }
//
//        // 查询角色名称
//        List<String> roleNames = roleMapper.selectRoleNameByRoleId(roleId);
//        if (roleNames == null || roleNames.isEmpty()) {
//            throw new UsernameNotFoundException("用户没有角色");
//        }
//
//
//
//
//        // 查询用户权限
//        List<String> permissionNames = new ArrayList<>();
////        for (String roleName : roleNames) {
////            // 查找角色ID（假设你有一个方法获取角色ID）
////            Integer roleId = roleMapper.findRoleIdByName(roleName); // 需在 RoleMapper 中添加此方法
////            if (roleId != null) {
////                List<String> permissions = permissionMapper.findPermissionsByRoleId(roleId);
////                if (permissions != null) {
////                    permissionNames.addAll(permissions);
////                }
////            }
////        }
//
//        // 构建 LoginUser 对象
//        return new LoginUser(sysUser, roleNames, permissionNames);
//    }
//}
