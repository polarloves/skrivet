package com.skrivet.core.common.annotations;

import java.lang.annotation.*;

/**
 * 校验忽略注解，加上此注解表示不进行校验
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidateIgnore {
}
