package com.example.springboot.Config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.springboot.Common.CommonAuth;
import com.example.springboot.Common.NoAuth;
import com.example.springboot.Common.ResponseEnum;
import com.example.springboot.Exception.ServiceException;
import com.example.springboot.Mapper.MenuMapper;
import com.example.springboot.Service.IUserService;
import com.example.springboot.entity.Menu;
import com.example.springboot.entity.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 这段代码是一个拦截器（Interceptor），用于对请求进行拦截和处理。在HandlerInterceptor接口中，preHandle方法在请求处理之前被调用。
 *
 * 在JwtInterceptor类的preHandle方法中，首先获取请求的URI，并判断是否是以"/user"开头的请求。如果是，则需要进行权限验证，否则直接放行。
 *
 * 如果是需要进行权限验证的请求，首先从请求头中获取token参数，如果token为空，则抛出异常，提示用户未登录。
 *
 * 然后从token中解析出用户id，通过id查询数据库获取用户信息。如果用户不存在，则抛出异常，提示用户注册一个帐号。
 *
 * 接着，使用JWTVerifier来验证token的合法性，如果验证失败，则抛出异常，提示用户token验证失败，请重新登录。
 *
 * 如果都验证通过，则返回true，请求继续向下执行。如果返回false，则请求被拦截，不会继续向下执行。
 * */
/**
 * JwtInterceptor是一个拦截器（Interceptor），用于对请求进行拦截和处理。
 * 在HandlerInterceptor接口中，preHandle方法在请求处理之前被调用。
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private IUserService userService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private MenuMapper menuMapper;

    /**
     * preHandle方法在请求处理之前被调用。在该方法中，首先获取请求的URI，并判断是否是以"/user"开头的请求。
     * 如果是，则需要进行权限验证，否则直接放行。如果是需要进行权限验证的请求，首先从请求头中获取token参数，
     * 如果token为空，则抛出异常，提示用户未登录。
     */
//    @Override
//    @ApiOperation(value = "权限验证拦截器", notes = "对请求进行拦截和权限验证")
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//            throws Exception {
//    }
}
//        String requestURI = request.getRequestURI();
//        if (requestURI.startsWith("/user")) { // 判断请求是否是以"/user"开头
//            String token = request.getHeader("token");
//            if (StrUtil.isBlank(token)) {
//                throw new ServiceException(401, "未登录，请先登录");
//            }
//            String userId;
//            try {
//                userId = JWT.decode(token).getAudience().get(0);
//            } catch (JWTDecodeException j) {
//                throw new RuntimeException("401");
//            }
//            User user = userService.getById(userId);
//            if (user == null) {
//                throw new ServiceException(401, "用户不存在，请注册一个");
//            }
//            // 验证 token
//            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
//            try {
//                jwtVerifier.verify(token);
//            } catch (JWTVerificationException e) {
//                throw new ServiceException(401, "token验证失败，请重新登录");
//            }
//            return true; // 验证通过放行
//        }
//        return true; // 其他路径直接放行
//    }
//}
//          判断请求是否为error页面
//            String requestURI = request.getRequestURI();
//
//            if(requestURI.equals("/error")){
//                return false;
//            }
//        // 判断handler是否为HandlerMethod实例
//            if(!(handler instanceof HandlerMethod)){
//                return true;
//            }
//        // 判断方法上是否有NoAuth注解，如果有则直接放行
//            NoAuth noAuth = ((HandlerMethod) handler).getMethodAnnotation(NoAuth.class);
//            if(null != noAuth){
//                return true;
//            }
//        // 获取请求头部中的token
//            String token = request.getHeader("token");
//        // 如果token为空，则验证失败，需要重新登录
//            if(StringUtils.isBlank(token)){
//                throw new ServiceException(401, "token验证失败，请重新登录");
//            }
//        // 解析token中的userId
//            String userId;
//            try{
//                userId = JWT.decode(token).getAudience().get(0);
//            }catch(JWTDecodeException e){
//                throw new ServiceException(401, "token验证失败，请重新登录");
//            }
//        // 根据userId获取用户信息
//            User user = userService.getById(userId);
//            if(null == user){
//                throw new ServiceException(401, "token验证失败，请重新登录");
//            }
//        // 从Redis缓存中获取用户信息
//            String userJson = stringRedisTemplate.opsForValue().get(token);
//            User redisUser = JSON.parseObject(userJson, User.class);
//        // 如果Redis缓存中的用户信息不为空，则进行进一步验证
//            if(null != redisUser){
//                // 判断Redis缓存中的用户信息与token中的userId是否一致
//                if(!redisUser.getId().toString().equals(userId)){
//                    throw new ServiceException(401, "token验证失败，请重新登录");
//                }
//                //判断访问的请求是不是公用的接口，登录了就可以访问
//                CommonAuth commonAuth = ((HandlerMethod) handler).getMethodAnnotation(CommonAuth.class);
//                if(commonAuth != null){
//                    return true;
//                }
//                //后台验证权限// 获取请求路径
//                requestURI = request.getRequestURI();
//                String[] split = requestURI.split("/");
//                String menuPath = "/" + split[1];
//                // 根据用户角色获取菜单列表
//                List<Menu> menuList = menuMapper.selectByIdList(redisUser.getRoleId());
//                System.out.println(redisUser.getRoleId());
//                // 获取菜单路径列表
//                List<String> menuPathList = menuList.stream().map(menu -> menu.getPath()).collect(Collectors.toList());
//                System.out.println(menuPathList);
//                // 判断访问的请求是否在菜单路径列表中，如果在则放行，否则验证失败
//                if(menuPathList.contains(menuPath)){
//                    System.out.println(menuPath);
//                    return true;
//                }else{
//                    throw new ServiceException(401, "token验证失败，请重新登录");
//                }
//            }else{
//                throw new ServiceException(401, "token验证失败，请重新登录");
//            }
//        }
//}

//            System.out.println("启动成功");
//            String requestURI = request.getRequestURI();
//            String token = request.getHeader("token");
//
//            if (!(handler instanceof HandlerMethod)) {
//                return true;
//            }
//
//            NoAuth noAuth = ((HandlerMethod) handler).getMethodAnnotation(NoAuth.class);
//            if (noAuth != null) {
//                return true;
//            }
//
//            if (requestURI.startsWith("/user")) {
//                if (StrUtil.isBlank(token)) {
//                    throw new ServiceException(ResponseEnum.UNAUTHORIZED.getCode(), ResponseEnum.UNAUTHORIZED.getMessage());
//                }
//
//                String userJson = stringRedisTemplate.opsForValue().get(token);
//                if (StrUtil.isBlank(userJson)) {
//                    throw new ServiceException(ResponseEnum.UNAUTHORIZED.getCode(), ResponseEnum.UNAUTHORIZED.getMessage());
//                }
//
//                User redisUser = JSON.parseObject(userJson, User.class);
//
//                try {
//                    String userId = JWT.decode(token).getAudience().get(0);
//                    User user = userService.getById(userId);
//
//                    if (user == null) {
//                        throw new ServiceException(401, "用户不存在，请注册一个");
//                    }
//
//                    JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
//                    jwtVerifier.verify(token);
//
//                    if (!redisUser.getId().toString().equals(userId)) {
//                        throw new ServiceException(401, "失败");
//                    }
//
//                    // 后台验证权限
//                    requestURI = request.getRequestURI();
//                    String[] split = requestURI.split("/");
//                    String menuPath = "/" + split[1];
//                    List<Menu> menuList = menuMapper.selectByIdList(redisUser.getRoleId());
//                    List<String> menuPathList = menuList.stream().map(Menu::getPath).collect(Collectors.toList());
//
//                    if (menuPathList.contains(menuPath)) {
//                        request.setAttribute("currentUser", user);
//                        return true;
//                    } else {
//                        throw new ServiceException(403, "无权访问");
//                    }
//                } catch (JWTDecodeException e) {
//                    throw new ServiceException(401, "token解析失败");
//                } catch (JWTVerificationException e) {
//                    throw new ServiceException(401, "token验证失败，请重新登录");
//                }
//            }
//
//            return false;
//        }
//}
