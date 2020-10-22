package com.lazyboyl.log.config;


import com.lazyboyl.log.service.OperateLogService;
import com.lazyboyl.log.util.LogJsonUtils;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author linzf
 * @since 2020/10/13
 * 类描述： 方法拦截业务处理类
 */
public class AdapterServiceMonitorInterceptor implements MethodInterceptor {

    /**
     * 是否开启数据库记录功能
     */
    private Boolean isOpenDatabase;

    /**
     * 日志自定义操作的service
     */
    private OperateLogService operateLogService;


    public AdapterServiceMonitorInterceptor( Boolean isOpenDatabase,OperateLogService operateLogService) {
        this.isOpenDatabase = isOpenDatabase;
        this.operateLogService = operateLogService;
    }

    /**
     * 开启日志
     */
    private static Logger LOGGER = LoggerFactory.getLogger(AdapterServiceMonitorInterceptor.class);

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        String fullMethod = methodInvocation.getMethod().toString();
        // "method " + methodInvocation.getMethod() + " is called on " + methodInvocation.getThis() + " with args " + methodInvocation.getArguments()
        LOGGER.info("响应方法为：{}", fullMethod);
        // 打印请求参数
        Object[] args = methodInvocation.getArguments();
        Map<String, String> requestParameters = new HashMap<>();
        Parameter[] parameters = methodInvocation.getMethod().getParameters();
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            // 参数为HttpServletRequest,HttpServletResponse和MultipartFile，不打印日志
            if (arg instanceof HttpServletRequest || arg instanceof HttpServletResponse || arg instanceof MultipartFile) {
                continue;
            }
            requestParameters.put(parameters[i].getName(), LogJsonUtils.objToJson(arg));
            LOGGER.info("请求参数: {}", LogJsonUtils.objToJson(arg));
        }
        long start_time = System.currentTimeMillis();
        // 执行相应的方法
        Object ret = methodInvocation.proceed();
        long end_time = System.currentTimeMillis();
        long requestTime = end_time - start_time;
        LOGGER.info("执行完成耗时为：{}毫秒", requestTime);
        String responseParameters = LogJsonUtils.objToJson(ret);
        LOGGER.info("执行返回为：{}", responseParameters);
        if (isOpenDatabase) {
            operateLogService.saveLog(fullMethod, requestParameters, requestTime, responseParameters);
        }
        return ret;
    }


}
