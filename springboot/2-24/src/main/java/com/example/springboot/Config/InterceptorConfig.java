package com.example.springboot.Config;

import com.auth0.jwt.JWT;
import com.example.springboot.Config.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.xml.ws.Action;

/**
 * @title: InterceptorConfig
 * @Author gjt
 * @Date: 2020-12-21
 * @Description：
 * 配置拦截器，用于对请求进行拦截和处理。在这段代码中，InterceptorConfig类实现了WebMvcConfigurer接口，并重写了addInterceptors方法。
 *
 * 在addInterceptors方法中，首先创建了一个JwtInterceptor对象，并将其添加到拦截器注册表registry中。拦截器会拦截所有请求，并通过判断是否有@LoginRequired注解来决定是否需要登录。
 *
 * 然后使用excludePathPatterns方法排除一些特定的路径，这些路径不会被拦截。在这个例子中，排除了登录、注册、导入、导出、文件相关的请求以及一些静态资源请求。这些路径可以根据具体需求进行配置。
 *
 * 最后，通过@Bean注解将JwtInterceptor对象注册为Spring的Bean，以便其他地方进行调用。
 *
 * 总的来说，这段代码的主要作用是配置拦截器，并设置拦截和排除的路径规则，用于实现对请求的权限验证和处理。具体的业务逻辑需要在JwtInterceptor类中实现。
 */
//@EnableWebMvc
//public class InterceptorConfig implements WebMvcConfigurer {
//    @Autowired
//    private JwtInterceptor jwtInterceptor;
//        @Override
//        public void addInterceptors(InterceptorRegistry registry) {
//            //注册自己的拦截器,并设置拦截的请求路径
//            //addPathPatterns为拦截此请求路径的请求
//            //excludePathPatterns为不拦截此路径的请求
//            registry.addInterceptor(jwtInterceptor)
//                    .excludePathPatterns("/user/login", "/user/register",
//                            "/**/import", "/**/export"
//                    );
//@Override
//public void addInterceptors(InterceptorRegistry registry) {
//    registry.addInterceptor(jwtInterceptor).addPathPatterns("/**").excludePathPatterns(
//            "/user/login",
//            "/user/register",
//            "/swagger-resources/**"
//            ,"/webjars/**"
//            ,"/v2/**"
//            ,"/doc.html/**"
//            ,"/swagger-ui.html/**"
//    );
//}

            //注册TestInterceptor拦截器
//        InterceptorRegistration registration = registry.addInterceptor(jwtInterceptor());
//        registration.addPathPatterns("/**");                      //添加拦截路径
//        registration.excludePathPatterns(                         //添加不拦截路径
//            "/**/*.html",            //html静态资源
//            "/**/*.js",              //js静态资源
//            "/**/*.css",             //css静态资源
//            "/**/*.woff",
//            "/**/*.ttf",
//            "/swagger-ui.html"
//        );
//        }
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/**").excludePathPatterns(
                "/user/login",
                "/user/register",
                "/swagger-resources/**"
                , "/webjars/**"
                , "/v2/**"
                ,"/doc.html/**"
                , "/swagger-ui.html/**"
                ,"/druid/**"
        );
    }

    /**
     * 映射路径修改:这段代码意思就配置一个拦截器， 如果访问路径是addResourceHandler中的filepath 这个路径
     * 那么就 映射到访问本地的addResourceLocations 的参数的这个路径上，
     * 这样就可以让别人访问服务器的本地文件了，比如本地图片或者本地音乐视频什么的。
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/File/show/**").addResourceLocations("file:D:\\java files\\2\\springboot\\article");
//        registry.addResourceHandler("/Article/show/**").addResourceLocations("file:D:\\java files\\8\\files\\");
        registry.addResourceHandler("/File/show/**").addResourceLocations("file:D:/java files/2/springboot/article/");
        registry.addResourceHandler("/Article/show/**").addResourceLocations("file:D:/java files/8/files/");
    }
}

