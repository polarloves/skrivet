package skrivet.springframework.database.relation;

import com.skrivet.core.toolkit.PropertyToolkit;

import com.skrivet.plugins.database.relation.DataSourceFactoryBean;
import com.skrivet.plugins.database.relation.DynamicDataSourceTransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

public class DataBaseAutoConfiguredRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    public static final String DEFAULT_DATASOURCE_NAME = "dataSource";
    public static final String DEFAULT_TRANSACTION_MANAGER_NAME = "transactionManager";
    public static final String DATABASE_PREFIX = "skrivet.database";
    private Environment environment;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        logger.info("start register datasource and transactionManager...");
        //注册 数据源,事务管理器等
        Set<String> names = PropertyToolkit.getChildNames(DATABASE_PREFIX, environment);
        boolean dynamicDatabase = environment.getProperty("skrivet.dynamicDatabase", boolean.class, false);
        logger.debug("the database id is：{}", names);
        for (String name : names) {
            String type = environment.getProperty(DATABASE_PREFIX + "." + name + ".type");

            boolean isDefault = environment.getProperty(DATABASE_PREFIX + "." + name + ".default", Boolean.class, false);
            List<String> dbs = environment.getProperty(DATABASE_PREFIX + "." + name + ".names", List.class);
            if (StringUtils.isEmpty(type)) {
                throw new IllegalArgumentException("the datasource named '" + name + "',has not config DataSource class!");
            }
            if (!type.contains(".")) {
                logger.debug("{} is not a relation database,it will be registered by other module!", name);
                continue;
            }
            if (CollectionUtils.isEmpty(dbs)) {
                throw new IllegalArgumentException("the datasource named '" + name + "',has not config support databases!");
            }
            logger.info("start register database,id : {},datasource type : {} ,support databases : {}", name, type, dbs);
            GenericBeanDefinition dataSource = new GenericBeanDefinition();
            //使用factory bean ,以便热修复,原始的DataSource无法接收修复过程,代理模式更好一些...
            dataSource.setBeanClass(DataSourceFactoryBean.class);
            if (dynamicDatabase) {
                dataSource.setScope("refresh");
            }
            dataSource.getPropertyValues().add("name", name);
            logger.debug("the datasource which id is {}， its original id in spring is {}.", name, name + "DataSource");
            registry.registerBeanDefinition(name + "DataSource", dataSource);
            for (String db : dbs) {
                if (db.equals(name)) {
                    continue;
                }
                logger.debug("start register bean alias, its id is {},alias is {} ",name + "DataSource", db + "DB");
                registry.registerAlias(name + "DataSource", db + "DB");
            }
            if (isDefault) {
                logger.info("start config default datasource , its name is {}",  name + "dataSource");
                dataSource.setPrimary(true);
                registry.registerAlias(name + "DataSource", DEFAULT_DATASOURCE_NAME);
            }
            //注册事务管理器
            GenericBeanDefinition transactionManager = new GenericBeanDefinition();
            if (dynamicDatabase) {
                transactionManager.setScope("refresh");
            }
            transactionManager.setBeanClass(DynamicDataSourceTransactionManager.class);
            transactionManager.getPropertyValues().add("dataSource", new RuntimeBeanReference(name + "DataSource"));
            transactionManager.getPropertyValues().add("dataSourceName", name + "DataSource");
            transactionManager.getPropertyValues().add("dynamic", dynamicDatabase);
            registry.registerBeanDefinition(name + "TransactionManager", transactionManager);
            for (String db : dbs) {
                if (db.equals(name)) {
                    continue;
                }
                registry.registerAlias(name + "TransactionManager", db + "TM");
                logger.debug("start register bean alias, its id is {},alias is {} ", name + "TransactionManager", db + "TM");
            }
            if (isDefault) {
                logger.info("start register default transactionManager，bean name:{}", name + "TransactionManager");
                registry.registerAlias(name + "TransactionManager", DEFAULT_TRANSACTION_MANAGER_NAME);
            }
            logger.info("register datasource succeed! the id is {}", name);
        }
    }


    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }


}
