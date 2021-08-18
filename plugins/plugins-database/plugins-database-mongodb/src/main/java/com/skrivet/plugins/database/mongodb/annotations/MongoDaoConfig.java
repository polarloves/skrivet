package com.skrivet.plugins.database.mongodb.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface MongoDaoConfig {

    boolean registerGridFsTemplate() default false;


    boolean registerTemplate() default true;
}
