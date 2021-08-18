package com.skrivet.plugins.database.mybatis.annotations;

import com.skrivet.plugins.database.mybatis.resigtrar.MybatisScanConfiguredRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface Page {
    public static enum Type {
        MYSQL("mysql"), SQLSERVER("sqlServer"), ORACLE("oracle");
        String code;

        Type(String t) {
            this.code = t;
        }

        public String getCode() {
            return code;
        }
    }

    Type[] supports() default {Type.MYSQL, Type.SQLSERVER};

    String start() default "getPageStartNumber()";

    String offset() default "getPageOffsetNumber()";
}
