package com.lazyboyl.log.config;

import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

/**
 * @author linzf
 * @since 2020/10/13
 * 类描述：
 */
public class AdapterServiceAdvisor extends AbstractBeanFactoryPointcutAdvisor {

    private String expression = "";

    public AdapterServiceAdvisor(String expression) {
        this.expression = expression;
    }

    @Override
    public Pointcut getPointcut() {
        AspectJExpressionPointcut adapterPointcut = new AspectJExpressionPointcut();
        //从配置文件中获取PointCut表达式 public * com.lazyboyl.log.controller.*.*(..)
        String[] expressions = expression.split("\\|\\|");
        String execution = "";
        for (String s : expressions) {
            if ("".equals(execution)) {
                execution = "execution(" + s + ")";
            } else {
                execution = execution + " || " + "execution(" + s + ")";
            }
        }
        adapterPointcut.setExpression(execution);
        return adapterPointcut;
    }

}
