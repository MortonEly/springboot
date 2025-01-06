package Com.Demo.Core.SpareResources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
/**
 * @Auther: 吕宏博
 * @Date: 2024--10--29--12:06
 * @Description:
 */
//@Aspect
//@Component
//public class WebLogAspect {
//
//    private final static Logger logger = LoggerFactory.getLogger(WebLogAspect.class);
//
//    /**
//     * 定义切入点，匹配 controller 包下的所有请求
//     */
//    @Pointcut("execution(public * site.exception.springbootaopwebrequest.controller..*.*(..))")
//    public void webLog() {}
//
//    /**
//     * 在切点之前织入，打印请求日志
//     *
//     * @param joinPoint 连接点
//     * @throws Throwable 可能抛出的异常
//     */
//    @Before("webLog()")
//    public void doBefore(JoinPoint joinPoint) throws Throwable {
//        // 开始打印请求日志
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//
//        // 打印请求相关参数
//        logger.info("========================================== Start ==========================================");
//        // 打印请求 URL
//        logger.info("URL            : {}", request.getRequestURL().toString());
//        // 打印 HTTP 方法
//        logger.info("HTTP Method    : {}", request.getMethod());
//        // 打印调用 controller 的全路径以及执行方法
//        logger.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
//        // 打印请求的 IP
//        logger.info("IP             : {}", request.getRemoteAddr());
//        // 打印请求入参
//        logger.info("Request Args   : {}", new Gson().toJson(joinPoint.getArgs()));
//    }
//
//    /**
//     * 在切点之后织入，标记请求结束
//     *
//     * @throws Throwable 可能抛出的异常
//     */
//    @After("webLog()")
//    public void doAfter() throws Throwable {
//        logger.info("=========================================== End ===========================================");
//        // 每个请求之间空一行
//        logger.info("");
//    }
//
//    /**
//     * 环绕通知，用于记录请求处理时间
//     *
//     * @param proceedingJoinPoint 环绕连接点
//     * @return 原方法执行结果
//     * @throws Throwable 可能抛出的异常
//     */
//    @Around("webLog()")
//    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//        long startTime = System.currentTimeMillis();
//        Object result = proceedingJoinPoint.proceed();
//        // 打印出参
//        logger.info("Response Args  : {}", new Gson().toJson(result));
//        // 打印执行耗时
//        logger.info("Time-Consuming : {} ms", System.currentTimeMillis() - startTime);
//        return result;
//    }
//}
