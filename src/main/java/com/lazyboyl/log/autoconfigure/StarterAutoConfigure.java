package com.lazyboyl.log.autoconfigure;

import com.lazyboyl.log.config.AdapterServiceAdvisor;
import com.lazyboyl.log.config.AdapterServiceMonitorInterceptor;
import com.lazyboyl.log.scanner.LogScannerRegister;
import com.lazyboyl.log.service.OperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

/**
 * @author linzf
 * @since 2020/9/21
 * 类描述：
 */
@Configuration
@Import({LogScannerRegister.class})
@ConditionalOnProperty(
        prefix = "log.aspectJ",
        name = "enable",
        havingValue = "true"
)
public class StarterAutoConfigure  {

    @Value("${log.aspectJ.expression}")
    private String expression;

    @Value("${log.aspectJ.database}")
    private Boolean isOpenDatabase;

    @Autowired
    private OperateLogService operateLogService;

    @Bean(name = "adapterServiceAdvisor")
    public AdapterServiceAdvisor adapterServiceAdvisor() {
        AdapterServiceAdvisor advisor = new AdapterServiceAdvisor(expression);
        advisor.setAdviceBeanName("adapterServiceAdvice");
        advisor.setAdvice(new AdapterServiceMonitorInterceptor(isOpenDatabase, operateLogService));
        advisor.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return advisor;
    }
}
