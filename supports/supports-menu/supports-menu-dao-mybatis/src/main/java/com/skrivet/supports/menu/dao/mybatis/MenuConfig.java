package com.skrivet.supports.menu.dao.mybatis;

import com.skrivet.plugins.database.mybatis.annotations.DaoScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@DaoScan(db = "menu", basePackages = "com.skrivet.supports.menu.dao.mybatis", location = "classpath*:mapping/supports/menu/**.xml")
public class MenuConfig {
}