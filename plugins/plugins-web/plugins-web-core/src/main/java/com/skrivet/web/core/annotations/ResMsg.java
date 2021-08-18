package com.skrivet.web.core.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;


/**
 * 响应注解
 *
 * @author n
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Component
public @interface ResMsg {


    /**
     * 标签，用于记录Log日志,如不设置，默认使用类名+方法名
     *
     * @return 标签
     */
    public String tag() default "";


    /**
     * 日志写入策略
     *
     * @return {@link LogPolicy}
     */
    public LogPolicy logPolicy() default LogPolicy.ERROR_ONLY;
}
