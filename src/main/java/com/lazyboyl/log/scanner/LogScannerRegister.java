package com.lazyboyl.log.scanner;

import com.lazyboyl.log.constant.LogProperties;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Properties;

/**
 * @author linzf
 * @since 2020/10/13
 * 类描述：
 */
public class LogScannerRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        LogScanner scanner = new LogScanner(registry);
        ConfigurableEnvironment c = (ConfigurableEnvironment) environment;
        MutablePropertySources m = c.getPropertySources();
        Properties p = new Properties();
        for (LogProperties lpp : LogProperties.values()) {
            String val = environment.getProperty(lpp.getKey());
            if (val == null || "".equals(val)) {
                p.put(lpp.getKey(), lpp.getDefaultValue());
            }
        }
        m.addFirst(new PropertiesPropertySource("defaultProperties", p));
        scanner.doScan("com.lazyboyl.log");
    }


}
