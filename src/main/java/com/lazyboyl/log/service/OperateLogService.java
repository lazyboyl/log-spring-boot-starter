package com.lazyboyl.log.service;

import java.util.Map;

/**
 * @author linzf
 * @since 2020/10/22
 * 类描述： 日志操作的service
 */
public interface OperateLogService {

    /**
     * 功能描述： 保存日志信息
     *
     * @param fullMethod         执行方法全路径
     * @param requestParameters  执行方法的入参
     * @param requestTime        方法执行时间
     * @param responseParameters 方法执行成功或失败范围
     */
    void saveLog(String fullMethod, Map<String, String> requestParameters, long requestTime, String responseParameters);

}
