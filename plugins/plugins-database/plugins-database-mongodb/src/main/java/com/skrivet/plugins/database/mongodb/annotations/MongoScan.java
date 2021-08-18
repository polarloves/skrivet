package com.skrivet.plugins.database.mongodb.annotations;

import com.skrivet.plugins.database.mongodb.registrar.MongoScanRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(MongoScanRegistrar.class)
public @interface MongoScan {
    /**
     * 扫描的包路径
     *
     * @return 扫描的包路径，多个以,隔开
     */
    String basePackages();

    /**
     * 数据库名称，如果为空则使用默认数据源
     *
     * @return 数据库名称
     */
    String db() default "";
    boolean registerTemplate() default true;
    boolean registerGridFsTemplate() default true;
}
