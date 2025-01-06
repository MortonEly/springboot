package Com.Demo.Core.SpareResources;


import Com.Demo.Core.Util.Jwt.JwtUtil;
import Com.Demo.Core.Result.*;
import Com.Demo.Core.Service.Impl.PermissionServiceImpl;
import com.alibaba.fastjson.*;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.schema.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.*;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.ServerException;

/**
 * @Auther: 吕宏博
 * @Date: 2024--10--21--15:36
 * @Description:    Spring Security配置类
 */

//@Slf4j
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SecurityConfig {

//    @Autowired
//    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

//    @Autowired
//    private UserDetailServiceImpl userDetailsService;

//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @Autowired
//    private PermissionServiceImpl permissionService;

    /**
     * SpringSecurity 过滤器
     */
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        // 基于 token，不需要 csrf
//        http.csrf().disable()
//                .cors()
//                // 开启跨域以便前端调用接口
//                .and();
        // 注册 JWT 过滤器，在 UsernamePasswordAuthenticationFilter 之前
        //http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        // 配置请求权限
//        http.authorizeHttpRequests()
//                // 指定某些接口不需要通过验证即可访问。登录接口肯定是不需要认证的
//                .antMatchers("/user/login", "/user/logout", "/hello").permitAll() // 允许匿名访问这些接口
//                //.antMatchers("/test3").hasRole("admin") // admin 角色可以访问 /test3
//                //.antMatchers("/test3").hasAuthority("admin") // 使用权限控制
//                // .antMatchers("/test4").hasAnyAuthority("READ", "WRITE") // 允许 READ 或 WRITE 权限访问
//                .anyRequest().authenticated() // 其他请求需要认证
//                .and();

        // 设置为无状态会话
//        http.sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 使用无状态会话策略
//                .and();


        // 配置异常处理器
//        http.exceptionHandling()
//                .authenticationEntryPoint(unauthorizedHandler()) // 未授权访问处理器
//                .accessDeniedHandler(accessDeniedHandler()) // 权限不足处理器
//                .and();

        // 配置登出逻辑
//        http.logout()
//                .logoutSuccessHandler(logoutSuccessHandler()); // 登出成功处理器
//        return http.build();
//    }

    /**
     * 基于内存的用户认证
     * */
//    @Bean
//    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
//        UserDetails user =User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("123456"))
//                .roles("ADMIN") // 用户角色
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }
    /**
     * 密码编码器，使用 BCrypt 编码
     *
     * @return PasswordEncoder 对象
     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    /**
     * 自定义登录成功处理器
     *
     * @return AuthenticationSuccessHandler 对象
     */
//    @Value("${spring.security.type}")
//    private String Type;//  JSON
//
//    @Bean
//    public AuthenticationSuccessHandler loginSuccessHandler() throws IOException {
//        return (HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {
//            if ("JSON".equals(Type)) {
//                response.setCharacterEncoding("UTF-8");
//                response.setContentType("application/json");
//                response.setStatus(ResultCode.SUCCESS.getCode());
//                String json = JSON.toJSONString(Result.success("登录成功"));
//                response.getWriter().print(json);
//            } else {
//                // 处理非JSON的响应格式，如果有的话
//                response.setStatus(ResultCode.SUCCESS.getCode());
//                response.getWriter().write("登录成功");
//            }
//        };
//    }

    /**
     * 自定义登出成功处理器
     */
//    @Bean
//    public LogoutSuccessHandler logoutSuccessHandler() {
//        return (HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {
//            String token = request.getHeader("Authorization");
//            if (token != null && !token.isEmpty()) {
//                jwtUtil.invalidateToken(token);  // 无效化 token
//            }
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("application/json");
//            response.setStatus(ResultCode.SUCCESS.getCode()); // 使用200状态码表示成功
//
//            // 创建返回的JSON内容
//            String json = JSON.toJSONString(Result.success("退出成功"));
//            response.getWriter().print(json);
//        };
//    }

    /**
     * 获取认证管理器 AuthenticationManager，用于登录时的身份认证
     *
     * @param authenticationConfiguration 注入的认证配置
     * @return AuthenticationManager 认证管理器
     * @throws Exception 抛出异常
     */
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }

    /**
     * 自定义未授权访问处理器
     * 当用户尝试访问受保护资源时，如果没有提供有效凭证，将调用该处理器
     *
     * @return AuthenticationEntryPoint 对象
     */
//    @Bean
//    public AuthenticationEntryPoint unauthorizedHandler() {
//        return (HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) -> {
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("application/json");
//            response.setStatus(ResultCode.UNAUTHORIZED.getCode()); // 使用401状态码
//
//            // 创建错误信息JSON，并发送响应
//            String json = JSON.toJSONString(Result.error(ResultCode.UNAUTHORIZED.getMsg()));
//            response.getWriter().print(json);
//        };
//    }

    /**
     * 权限不足处理器
     */
//    @Bean
//    public AccessDeniedHandler accessDeniedHandler() {
//        return (HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) -> {
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("application/json");
//            response.setStatus(ResultCode.ACCESS_DENIED.getCode()); // 修改为403
//            String json = JSON.toJSONString(Result.error(ResultCode.ACCESS_DENIED.getMsg()));
//            response.getWriter().print(json);
//        };
//    }


    /**
     * Dao 认证提供者，用于自定义用户详情服务和密码编码器
     *
     * @return DaoAuthenticationProvider 对象
     */
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }

    /**
     * 自定义 AuthenticationManager Bean，用于 Spring Security 的身份验证
     *
     * @param http HttpSecurity 对象
     * @return AuthenticationManager 认证管理器
     * @throws Exception 抛出异常
     */
//    @Bean
//    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
//        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
//        auth.authenticationProvider(authenticationProvider());
//        return auth.build();
//    }

    /**
     *
     */
//    @Bean
//    public SimpleUrlAuthenticationFailureHandler simpleUrlAuthenticationFailureHandler() {
//        return new SimpleUrlAuthenticationFailureHandler() {
//            @Override
//            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException{
//                response.setCharacterEncoding("UTF-8");
//                response.setContentType("application/json");
//                response.setStatus(ResultCode.UNAUTHORIZED.getCode()); // 使用401状态码
//
//                // 创建返回的JSON内容
//                String json = JSON.toJSONString(Result.error(ResultCode.UNAUTHORIZED.getMsg()));
//                response.getWriter().print(json);
//            }
//        };
//    }
    /**
     * 配置忽略的 URL，比如 Swagger、Druid、Actuator 等
     */
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return web -> web.ignoring()
//                .antMatchers(
//                        // Swagger相关路径
//                        "/swagger-ui/**",         // Swagger UI 相关路径
//                        "/swagger-ui.html",       // Swagger UI 主页面（Swagger2）
//                        "/v2/api-docs",           // Swagger2 API 文档
//                        "/swagger-resources/**",  // Swagger2 资源路径
//                        "/webjars/**",            // Swagger Webjar 文件
//                        "/doc.html",              // Swagger2 文档页面
//                        "/v3/api-docs",           // Swagger3 API 文档
//                        "/swagger-resources/**",  // Swagger3 资源路径
//                        "/swagger-ui/**",         // Swagger3 UI 路径
//
//                        // Druid监控路径
//                        "/druid/**",              // Druid 监控路径
//
//                        // 静态资源路径
//                        "/css/**",                // CSS 文件路径
//                        "/index.html",            // 主页路径
//                        "/img/**",                // 图片文件路径
//                        "/fonts/**",              // 字体文件路径
//                        "/favicon.ico",           // Favicon 图标路径
//
//                        // 验证码路径
//                        "/verifyCode",            // 验证码路径
//
//                        // Spring Boot Actuator端点
//                        "/actuator/**",           // Actuator 所有端点
//                        "/actuator/health",       // 健康检查端点
//                        "/actuator/metrics",      // 系统指标端点
//                        "/actuator/info",         // 应用信息端点
//                        "/actuator/prometheus",   // Prometheus 监控端点
//                        "/actuator/env",          // 应用的环境信息
//                        "/actuator/loggers",      // 日志配置端点
//                        "/actuator/auditevents",  // 审计事件端点
//                        "/actuator/threaddump",   // 线程转储端点
//                        "/actuator/heapdump",     // 堆转储端点
//
//                        // 错误处理路径
//                        "/error",                 // 错误页面路径
//
//                        // 其他自定义路径
//                        "/api/**",                // API接口路径
//                        "/public/**",             // 公共资源路径
//
//                        // 登录登出相关路径
//                        "/login",                 // 登录路径
//                        "/logout",                // 登出路径
//                        "/oauth/**",              // OAuth2授权路径
//
//                        // WebSocket路径
//                        "/ws/**",                 // WebSocket路径
//                        "/socket/**"              // WebSocket路径
//                );
//    }
//}