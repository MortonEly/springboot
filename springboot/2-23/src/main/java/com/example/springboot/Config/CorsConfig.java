package com.example.springboot.Config;
/**
* 解决跨越问题
* */
/**
 * 跨域配置类，用于处理跨域请求
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    // 当前跨域请求最大有效时长。这里默认1天
    private static final long MAX_AGE = 24 * 60 * 60;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 1 设置访问源地址
        corsConfiguration.addAllowedHeader("*"); // 2 设置访问源请求头
        corsConfiguration.addAllowedMethod("*"); // 3 设置访问源请求方法
        corsConfiguration.setMaxAge(MAX_AGE);
        source.registerCorsConfiguration("/**", corsConfiguration); // 4 对接口配置跨域设置
        return new CorsFilter(source);
    }
}
//
//@Configuration
//public class CorsConfig implements WebMvcConfigurer {
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                //是否发送Cookie
//                .allowCredentials(true)
//                //放行哪些原始域
//                .allowedOrigins("http://localhost:8080")
//                .allowedMethods("*")
//                .allowedHeaders("*")
//                .exposedHeaders("*")
//                .allowCredentials(true);  // 允许发送 cookies
//    }
//}
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                //是否发送Cookie
//                .allowCredentials(true)
//                //放行哪些原始域
//                .allowedOrigins("http://localhost:8080")
//                .allowedMethods("*")
//                .allowedHeaders("*")
//                .exposedHeaders("*")
//                .allowCredentials(true);  // 允许发送 cookies
//        registry.addMapping("/**")
//                .allowedOriginPatterns("http://localhost:8080")
//                .allowedOrigins("*")
//                .allowedMethods("GET","POST","PUT","OPTIONS","DELETE","PATCH")
//                .allowCredentials(true).maxAge(3600);
        // 当前跨域请求最大有效时长。这里默认1天
//@Override
//public void addCorsMappings(CorsRegistry registry) {
//    registry.addMapping("/**")
//            .allowedOrigins("*")  // 允许所有域名进行跨域访问，可以根据实际需求设置具体的域名
//            .allowedMethods("GET", "POST", "PUT", "DELETE")  // 允许的请求方法
//            .allowedHeaders("*")  // 允许的请求头
//            .allowCredentials(true) // 是否允许发送cookie
//            .maxAge(3600)  // 预检请求的有效期，单位秒
//            .exposedHeaders("Authorization");  // 允许前端获取的响应头
//
//    registry.addMapping("/v2/api-docs")  // 放行Swagger接口文档请求
//            .allowedOrigins("*")
//            .allowedMethods("GET");
//
//    registry.addMapping("/doc.html**")  // 放行Swagger UI页面请求
//            .allowedOrigins("*")
//            .allowedMethods("GET");
//}
//}
