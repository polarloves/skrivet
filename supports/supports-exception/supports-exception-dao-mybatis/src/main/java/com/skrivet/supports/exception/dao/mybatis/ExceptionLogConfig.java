package com.skrivet.supports.exception.dao.mybatis;

import com.skrivet.plugins.database.mybatis.annotations.DaoScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@DaoScan(db = "exception",basePackages = "com.skrivet.supports.exception.dao.mybatis", location = "classpath*:mapping/module/exceptionLog/**.xml")
public class ExceptionLogConfig {
}