package com.example.demo.demos.Springboot.Shiro.Config;

import com.example.demo.demos.Springboot.Shiro.Realm.UserRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;
/**
 * @Auther: 吕宏博
 * @Date: 2024--03--21--10:22
 * @Description:
 */
@Configuration
public class ShiroConfig {
    /**
     * 创建 ShiroFilterFactoryBean 过滤器
     */
    public ShiroFilterFactoryBean getShiroFilterFactoryBean (@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        // 设置安全管理器
        filterFactoryBean.setSecurityManager(securityManager);
        return filterFactoryBean;
    }

    /**
     * 创建 DefaultWebSecurityManager
     *      @Qualifier 注解 表示传入的参数是下边那个放入spring容器中的bean
     *      @Qualifier 注解
     *      @Bean 注解里的 name 指定放到spring容器中的名字， 若不写， 默认为方法名
     */
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 这里要吧 userRealm 和 securityManager 关联
        securityManager.setRealm(userRealm);
        return securityManager;
    }
    /**
     * 创建 Realm
     * @Bean 的作用： 将该方法返回的对象放入spring容器， 以便给上边的方法使用
     */
    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }
}
