package com.example.springboot.util;

//TokenUtil的工具类，用于处理用户登录时的身份验证和生成JWT（JSON Web Token）的功能
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.example.springboot.Service.IUserService;
import com.example.springboot.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
@Component//将该类声明为Spring的组件，由Spring进行管理和实例化
public class TokenUtil {
    // 声明静态变量staticUserService，并且使用@Resource注解自动注入实例userService
    private static IUserService staticUserService;

    @Resource
    private IUserService userService;

    // 使用@PostConstruct注解的init方法，在实例化之后将userService赋值给staticUserService
    @PostConstruct
    public void init() {
        staticUserService = userService;
    }

    // 静态方法，用于获取当前用户信息
    public static User getCurrentUser() {
        // 获取请求的属性
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String token = request.getHeader("token");
            if (StrUtil.isNotBlank(token)) {
                try {
                    // 解析token获取userId，并通过staticUserService获取用户信息
                    String userId = JWT.decode(token).getAudience().get(0);
                    return staticUserService.getById(Integer.valueOf(userId));
                } catch (JWTDecodeException e) {
                    // 实际项目中可能需要记录日志或抛出自定义异常
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    // 静态方法，用于生成JWT token
    public static String generateToken(String userId, String secret, long expireTimeInMilliseconds) {
        // 设置token过期时间
        Date expireDate = new Date(System.currentTimeMillis() + expireTimeInMilliseconds);
        // 创建并返回token
        return JWT.create()
                .withAudience(userId)
                .withExpiresAt(expireDate)
                .sign(Algorithm.HMAC256(secret));
    }


}
