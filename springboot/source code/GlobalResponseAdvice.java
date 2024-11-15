package com.example.demo.core.Handler;

import com.example.demo.core.Result.Constants.HttpStatus;
import com.example.demo.core.Result.Result;
import com.example.demo.core.Result.ResultCode;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @Auther: 吕宏博
 * @Date: 2024--10--10--14:49
 * @Description:
 */
@RestControllerAdvice
public class GlobalResponseAdvice implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 获取当前的 HttpServletResponse
        HttpServletResponse servletResponse = getCurrentHttpResponse();
        // 获取请求的 URI
        String uri = Objects.requireNonNull(request.getURI()).getPath();

        // 排除 Swagger、Knife4j、静态资源、健康检查等请求的常用 URL 地址
        if (isExcludedUri(uri)) {
            return body;
        }

        // 排除 Swagger 和 Knife4j 请求
        if (uri.contains("/swagger") || uri.contains("/v2/api-docs") || uri.contains("/v3/api-docs") || uri.contains("/webjars") || uri.contains("/swagger-resources") || uri.contains("/doc.html")) {
            return body;
        }

        if (body instanceof Result) {
            return body;
        }
        // 获取 HTTP 状态码
        int statusCode = (servletResponse != null) ? servletResponse.getStatus() : HttpStatus.HTTP_STATUS_UNKNOWN;

        // 判断状态码类型并返回相应的 Result
        if (statusCode >= 400 && statusCode < 500) {
            // 客户端错误，返回 fail 响应
            return Result.fail(ResultCode.FAIL.getCode(), ResultCode.FAIL.getMsg(), body);
        } else if (statusCode >= 500) {
            // 服务器错误，返回 error 响应
            return Result.error(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMsg(), body);
        }
        // 默认返回成功响应
        return Result.success(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), body);
    }

    private HttpServletResponse getCurrentHttpResponse() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes != null ? attributes.getResponse() : null;
    }

    // 判断是否是需要排除的 URI
    private boolean isExcludedUri(String uri) {
        return uri.contains("/swagger") || uri.contains("/v2/api-docs") || uri.contains("/v3/api-docs") ||uri.contains("/druid/*")||
                uri.contains("/swagger-resources") || uri.contains("/webjars") ||
                uri.contains("/doc.html") || uri.contains("/favicon.ico") ||
                uri.contains("/css/") || uri.contains("/js/") || uri.contains("/images/") ||
                uri.contains("/fonts/") || uri.contains("/static/") || uri.contains("/public/") ||
                uri.contains("/health") || uri.contains("/actuator/health") ||
                uri.contains("/docs") || uri.contains("/api-docs");
    }
}
