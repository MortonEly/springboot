//package com.example.ttdlaw.springboot.Shiro.Config;
//
//import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
//import org.apache.shiro.mgt.DefaultSubjectDAO;
//import org.apache.shiro.spring.LifecycleBeanPostProcessor;
//import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.DependsOn;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.logging.Filter;
//
///**
// * @Auther: 吕宏博
// * @Date: 2024--05--16--22:11
// * @Description:
// */
//public class ShiroConfig {
//    @Bean("securityManager")
//    public DefaultWebSecurityManager getManager(MyRealm realm) {
//        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
//
//        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
//        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
//        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
//        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
//
//        manager.setSubjectDAO(subjectDAO);
//        // 使用自己的 Realm
//        manager.setRealm(realm);
//
//        return manager;
//    }
//
//    @Bean("shiroFilter")
//    public ShiroFilterFactoryBean factory(DefaultWebSecurityManager securityManager) {
//        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
//
//        // 添加自己的过滤器并且取名为jwt
//        Map<String, Filter> filterMap = new HashMap<>();
//        filterMap.put("jwt", new JWTFilter());
//        factoryBean.setFilters(filterMap);
//
//        factoryBean.setSecurityManager(securityManager);
//        factoryBean.setUnauthorizedUrl("/401");
//
//        Map<String, String> filterRuleMap = new HashMap<>();
//        // 所有请求通过我们自己的jwt filter
//        filterRuleMap.put("/**", "jwt");
//        // 访问401和404页面不通过我们的jwt filter
//        filterRuleMap.put("/401", "anon");
//        factoryBean.setFilterChainDefinitionMap(filterRuleMap);
//
//        return factoryBean;
//    }
//    @Bean
//    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
//        return new LifecycleBeanPostProcessor();
//    }
//
//    @Bean
//    @DependsOn("lifecycleBeanPostProcessor")
//    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
//        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
//        advisorAutoProxyCreator.setProxyTargetClass(true);
//        return advisorAutoProxyCreator;
//    }
//
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
//        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
//        return authorizationAttributeSourceAdvisor;
//    }
//}
