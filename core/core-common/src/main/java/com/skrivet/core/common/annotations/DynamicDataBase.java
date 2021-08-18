package com.skrivet.core.common.annotations;

import java.lang.annotation.*;

/**
 * 动态数据源标识，用来标识service/controller/dao使用动态数据源
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface DynamicDataBase {
}
