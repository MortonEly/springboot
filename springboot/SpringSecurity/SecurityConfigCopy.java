//package Com.Demo.Core.Config;
//
//
//import Com.Demo.Core.Filter.JwtAuthenticationTokenFilter;
//import Com.Demo.Core.Jwt.JwtUtil;
//import Com.Demo.Core.Result.Result;
//import com.alibaba.fastjson.JSON;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * @Auther: 吕宏博
// * @Date: 2024--10--21--15:36
// * @Description:    Spring Security配置类
// */
//@Slf4j
//@Configuration
//@EnableGlobalMethodSecurity(securedEnabled = true) // 此注解表明这是一个安全配置类
//public class SecurityConfigCopy {
//
//    @Autowired
//    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
//
//    @Autowired
//    private UserDetailServiceImpl userDetailsService;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//    /**
//     * 密码编码器，使用 BCrypt 编码
//     * @return PasswordEncoder 对象
//     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//    /**
//     * 配置认证管理器
//     * @param
//     * */
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
//        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
//        auth.authenticationProvider(authenticationProvider());
//        return auth.build();
//    }
//
//    /**
//     * SpringSecurity 过滤器
//     * */
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        // 关闭 CSRF 保护
//        http.csrf()
//                .and()
//                .headers()
//                .frameOptions()
//                .disable();
//
//        // 设置为无状态会话
//        http.sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 使用无状态会话策略
//                .and();
////        //登录控制
////        http.formLogin()
////                //登录页面
////                .loginPage()
////                //登录请求的接口
////                        .loginProcessingUrl()
//
//        // 配置请求权限
//        http.authorizeHttpRequests()
//                .antMatchers("/user/login", "/user/logout", "/test", "/hello").permitAll() // 确保这些路径允许匿名访问
//                .anyRequest().authenticated() // 其他请求需要认证
//                .and();
//
//        //会话控制
////        http.sessionManagement()
////                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        // 注册 JWT 过滤器，在 UsernamePasswordAuthenticationFilter 之前
//        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
//
//        // 配置异常处理器
//        http.exceptionHandling()
//                .authenticationEntryPoint(unauthorizedHandler()) // 未授权访问处理器
//                .accessDeniedHandler(accessDeniedHandler()) // 权限不足处理器
//                .and();
//
//        // 配置登出逻辑
//        http.logout()
//                .logoutSuccessHandler(logoutSuccessHandler()); // 登出成功处理器
//        return http.build();
//    }
//
//    /**
//     *
//     * */
//    @Bean
//    public AuthenticationSuccessHandler loginSuccessHandler() {
//        return (HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("application/json");
//            response.setStatus(HttpStatus.OK.value());
//            String json = JSON.toJSONString(Result.success("登录成功"));
//            response.getWriter().print(json);
//        };
//    }
//    /**
//     * 自定义未授权访问处理器
//     */
//    @Bean
//    public AuthenticationEntryPoint unauthorizedHandler() {
//        return (HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) -> {
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("application/json");
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            String json = JSON.toJSONString(Result.error("用户未登录或登录已过期，请重新登录"));
//            response.getWriter().print(json);
//        };
//    }
//
//    /**
//     * 自定义登出成功处理器
//     */
//    @Bean
//    public LogoutSuccessHandler logoutSuccessHandler() {
//        return (HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {
//            String token = request.getHeader("Authorization");
//            if (token != null && !token.isEmpty()) {
//                jwtUtil.invalidateToken(token);
//            }
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("application/json");
//            String json = JSON.toJSONString(Result.success("退出成功"));
//            response.getWriter().print(json);
//        };
//    }
//    /**
//     *权限不足处理器
//     * */
//    @Bean
//    public AccessDeniedHandler accessDeniedHandler() {
//        return (HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) -> {
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("application/json");
//            response.setStatus(HttpStatus.FORBIDDEN.value()); // 修改为403
//            String json = JSON.toJSONString(Result.error("权限不足"));
//            response.getWriter().print(json);
//        };
//    }
//}
