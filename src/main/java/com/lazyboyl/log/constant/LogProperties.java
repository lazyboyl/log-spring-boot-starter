package com.lazyboyl.log.constant;

/**
 * @author linzf
 * @since 2020/10/13
 * 类描述： 日志的配置文件
 */
public enum LogProperties {

    EXPRESSION("log.aspectJ.expression","public * com.lazyboyl.log.controller.*.*(..)"),
    ISOPENDATABASE("log.aspectJ.database",false);


    private String key;

    private Object defaultValue;

    LogProperties(String key, Object defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public String getKey() {
        return key;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

}
