package com.lazyboyl.log.service.impl;

import com.lazyboyl.log.service.OperateLogService;
import com.lazyboyl.log.util.LogJsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @author linzf
 * @since 2020/10/22
 * 类描述：
 */
@Service
public class DbOperateLogService implements OperateLogService {

    /**
     * 开启日志
     */
    private static Logger LOGGER = LoggerFactory.getLogger(DbOperateLogService.class);

    /**
     * 数据库操作对象
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void saveLog(String fullMethod, Map<String, String> requestParameters, long requestTime, String responseParameters) {
        if (jdbcTemplate != null) {
            try {
                jdbcTemplate.update("insert into t_aop_log(fullMethod,requestParameters,requestTime,responseParameters,createDate) values(?,?,?,?,?)", fullMethod, LogJsonUtils.objToJson(requestParameters), requestTime, responseParameters, new Date());
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.info("错误信息为：{}", e.getMessage());
            }
        }
    }
}
