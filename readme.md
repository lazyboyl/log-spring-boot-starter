### 简介
在应用程序中你只需要简单添加我们的maven依赖，同时在application.yml或者application.properties配置文件中
设置好相应的配置即可实现日志的拦截。
### 要求
- jdk版本为1.8或1.8+
- spring-boot版本为2.0.1.RELEASE+
### 快速开始

#### 1、添加依赖:

```xml
      <dependency>
           <groupId>com.lazyboyl</groupId>
           <artifactId>log-spring-boot-starter</artifactId>
           <version>0.0.1-SNAPSHOT</version>
      </dependency>
```

### 2、配置详解
| 属性  | 默认值 | 说明 
|---|---|---
|log.aspectJ.enable|false|true：开启日志监控，false：关闭日志监控
|log.aspectJ.expression|无|aop扫描的包的位置，具体可以参照下面的例子的配置，此处的配置和aop的配置一样，若此处有多个扫描的路径，则使用双竖线来作为分隔符来进行处理
|log.aspectJ.database|false|默认不开启数据库信息的记录，若想要将请求的日志保存到数据库需要执行doc文件夹底下的t_aop_log.sql。

#### 3、在配置类中配置日志
在spring boot的application.yml或者application.properties配置文件中做好相应的配置，以下为application.properties的配置

```xml
log.aspectJ.enable=true
log.aspectJ.expression=public * com.xx.push.service.*.*(..) || public * com.xx.test.controller.*.*(..)
log.aspectJ.database=true
```

#### 4、自定义记录日志信息
有时候日志数据我们可能不想保存到数据库，我们希望使用异步的方式来保存我们的日志信息，这时候我们就可以通过实现
OperateLogService接口来实现我们的自定义日志收集功能，示例代码如下：

```java
@Service
@Primary
public class MqOperateLogService implements OperateLogService {

    @Override
    public void saveLog(String s, Map<String, String> map, long l, String s1) {
        System.out.println("-----MqOperateLogService------");
    }
}
```

在这边大家一定要把**@Primary**注解加到我们的实现的类上，否则会导致启动报错。

