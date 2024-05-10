package com.example.demo.demos.Springboot.Shiro.Realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @Auther: 吕宏博
 * @Date: 2024--03--21--10:21
 * @Description:
 */
public class UserRealm extends AuthorizingRealm {
    /**
     * 执行授权逻辑
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权");
        return null;
    }
    /**
     * 执行认证逻辑
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
// 打印认证信息
        System.out.println("认证");

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        char[] password = token.getPassword(); // 这里获取到的是用户输入的密码的字符数组

        // 在这里你可以添加验证逻辑，比如与数据库中存储的用户信息进行比对等

        // 假设这里是一个简单示例，直接返回一个固定的用户名和密码进行验证
        String expectedUsername = "admin";
        String expectedPassword = "admin123";

        if (username.equals(expectedUsername) && String.valueOf(password).equals(expectedPassword)) {
            // 认证成功，返回一个 SimpleAuthenticationInfo 对象
            return new SimpleAuthenticationInfo(username, password, getName());
        } else {
            // 认证失败，抛出异常
            throw new AuthenticationException("用户名或密码错误");
        }
    }
}
