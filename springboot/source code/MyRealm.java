//package com.example.ttdlaw.springboot.Shiro.Realm;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// * @Auther: 吕宏博
// * @Date: 2024--05--17--16:04
// * @Description:
// */
//@Service
//public class MyRealm extends AuthorizingRealm {
//    private UserService userService;
//    private RedisUtils redisUtils;
//
//    @Autowired
//    public MyRealm(UserService userService, RedisUtils redisUtils) {
//        super();
//        this.userService = userService;
//        this.redisUtils = redisUtils;
//    }
//
//    @Override
//    public boolean supports(AuthenticationToken token) {
//        return token instanceof JwtToken;
//    }
//
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        // 实现授权逻辑
//        return null;
//    }
//
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//        // 实现认证逻辑
//        return null;
//    }
//}
