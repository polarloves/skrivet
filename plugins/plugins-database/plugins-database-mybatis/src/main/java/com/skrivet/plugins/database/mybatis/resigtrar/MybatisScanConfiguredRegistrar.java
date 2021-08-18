package com.skrivet.plugins.database.mybatis.resigtrar;

import com.skrivet.components.mybatis.factory.DynamicSqlSessionFactoryBean;
import com.skrivet.plugins.database.mybatis.annotations.DaoScan;
import com.skrivet.plugins.database.mybatis.config.RefreshableMapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;
import skrivet.springframework.database.relation.DataBaseAutoConfiguredRegistrar;

public class MybatisScanConfiguredRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    private Environment environment;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        boolean dynamicDatabase = environment.getProperty("skrivet.dynamicDatabase", boolean.class, false);
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(DaoScan.class.getName()));
        String basePackages = annotationAttributes.getString("basePackages");
        if (StringUtils.isEmpty(basePackages)) {
            throw new IllegalArgumentException("you must config basePackages");
        }
        String db = annotationAttributes.getString("db");
        String location = annotationAttributes.getString("location");
        logger.info("start config mybatis,database name is：{},package name is:{},mapping file path is:{}", db, basePackages, location);
        //注入sqlSessionFactory
        if (registry.containsBeanDefinition(db + "SqlSessionFactory")) {
            BeanDefinition sqlSessionFactory = registry.getBeanDefinition(db + "SqlSessionFactory");
            logger.debug("start merge SqlSessionFactoryBean,id:{},mapperLocations:{}", db + "SqlSessionFactory", location);
            mergeValue(sqlSessionFactory, "mapperLocations", location);
            BeanDefinition mapperScannerConfigurer = registry.getBeanDefinition(db + "MapperScannerConfigurer");
            mergeValue(mapperScannerConfigurer, "basePackage", basePackages);
            logger.debug("start merge MapperScannerConfigurer,id:{},basePackage:{}", db + "MapperScannerConfigurer", basePackages);
        } else {
            String dataSourceName = StringUtils.isEmpty(db) ? DataBaseAutoConfiguredRegistrar.DEFAULT_DATASOURCE_NAME : (db + "DB");
            logger.debug("start init SqlSessionFactoryBean,id:{},dataSourceName:{},mapperLocations:{}", db + "SqlSessionFactory", dataSourceName, location);
            RootBeanDefinition sqlSessionFactory = new RootBeanDefinition();
            sqlSessionFactory.setBeanClass(DynamicSqlSessionFactoryBean.class);
            sqlSessionFactory.getPropertyValues().add("dataSource", new RuntimeBeanReference(dataSourceName));
            sqlSessionFactory.getPropertyValues().add("mapperLocations", location);
            sqlSessionFactory.getPropertyValues().add("database", db);
            if (dynamicDatabase) {
                sqlSessionFactory.setScope("refresh");
            }
            registry.registerBeanDefinition(db + "SqlSessionFactory", sqlSessionFactory);
            logger.debug("start init MapperScannerConfigurer,id:{},basePackage:{}", db + "MapperScannerConfigurer", basePackages);
            RootBeanDefinition mapperScannerConfigurer = new RootBeanDefinition(RefreshableMapperScannerConfigurer.class);
            mapperScannerConfigurer.getPropertyValues().add("sqlSessionFactoryBeanName", db + "SqlSessionFactory");
            mapperScannerConfigurer.getPropertyValues().add("dynamic", dynamicDatabase);
            mapperScannerConfigurer.getPropertyValues().add("basePackage", basePackages);
            mapperScannerConfigurer.getPropertyValues().add("annotationClass", "com.skrivet.plugins.database.core.annotations.Dao");
            registry.registerBeanDefinition(db + "MapperScannerConfigurer", mapperScannerConfigurer);
        }
        logger.info("mybatis config succeed...");
    }

    private void mergeValue(BeanDefinition beanDefinition, String key, String value) {
        if (StringUtils.isEmpty(value)) {
            return;
        }
        Object orgValue = beanDefinition.getPropertyValues().getPropertyValue(key).getValue();
        beanDefinition.getPropertyValues().removePropertyValue(key);
        beanDefinition.getPropertyValues().addPropertyValue(key, orgValue + "," + value);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
