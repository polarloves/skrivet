package com.skrivet.plugins.database.core.annotations;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface Dao {
    String value() default "";
}
