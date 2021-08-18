package com.skrivet.plugins.database.mongodb.registrar;

import com.skrivet.core.toolkit.PropertyToolkit;
import com.skrivet.components.mongodb.factory.GridFsMongoDatabaseFactoryBean;
import com.skrivet.components.mongodb.factory.MappingMongoConverterFactoryBean;
import com.skrivet.components.mongodb.factory.MongoClientFactoryBean;
import com.skrivet.components.mongodb.factory.MongoMappingContextFactoryBean;
import com.skrivet.components.mongodb.transaction.EmptyTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.MethodParameter;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Set;

public class MultiMongoConfiguredRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware, BeanFactoryAware {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    public static final String DEFAULT_GRID_FS_TEMPLATE = "gridFsTemplate";
    public static final String DEFAULT_MONGO_TEMPLATE = "mongoTemplate";
    public static final String DEFAULT_TRANSACTION_MANAGER_NAME = "transactionManager";
    public static final String DATABASE_PREFIX = "skrivet.database";
    private Environment environment;
    private BeanFactory beanFactory;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        logger.info("start register datasource and transactionManager...");
        //注册 数据源,事务管理器等
        Set<String> names = PropertyToolkit.getChildNames(DATABASE_PREFIX, environment);
        boolean dynamicDatabase = environment.getProperty("skrivet.dynamicDatabase", boolean.class, false);
        for (String name : names) {
            String type = environment.getProperty(DATABASE_PREFIX + "." + name + ".type");
            String tm = environment.getProperty(DATABASE_PREFIX + "." + name + ".transaction");
            if (type != null && type.equalsIgnoreCase("mongodb")) {
                if (!StringUtils.isEmpty(type) && type.equalsIgnoreCase("mongodb")) {
                    logger.debug("start register mongodb which named {}", name);
                    boolean isDefault = environment.getProperty(DATABASE_PREFIX + "." + name + ".default", Boolean.class, false);
                    List<String> dbs = environment.getProperty(DATABASE_PREFIX + "." + name + ".names", List.class);
                    logger.info("start register database,id : {},datasource type : {} ,support databases : {}", name, type, dbs);
                    GenericBeanDefinition mongoClient = new GenericBeanDefinition();
                    //使用factory bean ,以便热修复,原始的DataSource无法接收修复过程,代理模式更好一些...
                    mongoClient.setBeanClass(MongoClientFactoryBean.class);
                    AbstractBeanFactory beanFactory = (AbstractBeanFactory) this.beanFactory;
                    MongoProperties mongoProperties = new MongoProperties();
                    Set<String> properties = PropertyToolkit.getChildNames(DATABASE_PREFIX + "." + name + ".properties", environment);
                    TypeConverter typeConverter = beanFactory.getTypeConverter();
                    for (String property : properties) {
                        //生成mongoProperties
                        String propertyName = property;
                        PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(MongoProperties.class, propertyName);
                        MethodParameter methodParam = BeanUtils.getWriteMethodParameter(propertyDescriptor);
                        String originalValue = environment.getProperty(DATABASE_PREFIX + "." + name + ".properties." + propertyName);
                        Object newValue = typeConverter.convertIfNecessary(originalValue, propertyDescriptor.getPropertyType(), methodParam);
                        try {
                            propertyDescriptor.getWriteMethod().invoke(mongoProperties, newValue);
                        } catch (Exception e) {
                            throw new RuntimeException("failed analysis mongodb's property!", e);
                        }
                    }
                    mongoClient.getPropertyValues().add("properties", mongoProperties);
                    logger.debug("the mongoClient which id is {}， its original id in spring is {}.", name, name + "MongoClient");
                    registry.registerBeanDefinition(name + "MongoClient", mongoClient);
                    logger.debug("start register {}...", name + "MongoDatabaseFactory");
                    //注册MongoClientDatabaseFactory
                    GenericBeanDefinition mongoDatabaseFactory = new GenericBeanDefinition();
                    mongoDatabaseFactory.setBeanClass(SimpleMongoClientDatabaseFactory.class);
                    ConstructorArgumentValues values = new ConstructorArgumentValues();
                    values.addGenericArgumentValue(new RuntimeBeanReference(name + "MongoClient"));
                    values.addGenericArgumentValue(mongoProperties.getMongoClientDatabase());
                    mongoDatabaseFactory.setConstructorArgumentValues(values);
                    registry.registerBeanDefinition(name + "MongoDatabaseFactory", mongoDatabaseFactory);
                    logger.debug("start register {}...", name + "MongoMappingContext");
                    //注册MongoMappingContext
                    GenericBeanDefinition mongoMappingContext = new GenericBeanDefinition();
                    mongoMappingContext.setBeanClass(MongoMappingContextFactoryBean.class);
                    mongoMappingContext.getPropertyValues().add("properties", mongoProperties);
                    mongoMappingContext.getPropertyValues().add("mongoCustomConversions", new RuntimeBeanReference("mongoCustomConversions"));
                    registry.registerBeanDefinition(name + "MongoMappingContext", mongoMappingContext);
                    //注册MappingMongoConverter
                    logger.debug("start register {}...", name + "MappingMongoConverter");
                    GenericBeanDefinition mappingMongoConverter = new GenericBeanDefinition();
                    mappingMongoConverter.setBeanClass(MappingMongoConverterFactoryBean.class);
                    mappingMongoConverter.getPropertyValues().add("properties", mongoProperties);
                    mappingMongoConverter.getPropertyValues().add("conversions", new RuntimeBeanReference("mongoCustomConversions"));
                    mappingMongoConverter.getPropertyValues().add("context", new RuntimeBeanReference(name + "MongoMappingContext"));
                    mappingMongoConverter.getPropertyValues().add("mongoDatabaseFactory", new RuntimeBeanReference(name + "MongoDatabaseFactory"));
                    registry.registerBeanDefinition(name + "MappingMongoConverter", mappingMongoConverter);

                    logger.debug("start register {}...", name + "MongoTemplate");
                    //注册MongoTemplate
                    GenericBeanDefinition mongoTemplate = new GenericBeanDefinition();
                    mongoTemplate.setBeanClass(MongoTemplate.class);
                    ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
                    constructorArgumentValues.addGenericArgumentValue(new RuntimeBeanReference(name + "MongoDatabaseFactory"));
                    constructorArgumentValues.addGenericArgumentValue(new RuntimeBeanReference(name + "MappingMongoConverter"));
                    mongoTemplate.setConstructorArgumentValues(constructorArgumentValues);
                    registry.registerBeanDefinition(name + "MongoTemplate", mongoTemplate);
                    //注册GridFsMongoDatabase
                    logger.debug("start register {}...", name + "GridFsMongoDatabaseFactory");
                    GenericBeanDefinition gridFsMongoDatabase = new GenericBeanDefinition();
                    gridFsMongoDatabase.setBeanClass(GridFsMongoDatabaseFactoryBean.class);
                    ConstructorArgumentValues gridFsMongoDatabaseConstructorArgumentValues = new ConstructorArgumentValues();
                    gridFsMongoDatabaseConstructorArgumentValues.addGenericArgumentValue(new RuntimeBeanReference(name + "MongoDatabaseFactory"));
                    gridFsMongoDatabaseConstructorArgumentValues.addGenericArgumentValue(mongoProperties);
                    gridFsMongoDatabase.setConstructorArgumentValues(gridFsMongoDatabaseConstructorArgumentValues);
                    registry.registerBeanDefinition(name + "GridFsMongoDatabaseFactory", gridFsMongoDatabase);
                    logger.debug("start register {}...", name + "GridFsTemplate");
                    //注册GridFsTemplate
                    GenericBeanDefinition gridFsTemplate = new GenericBeanDefinition();
                    gridFsTemplate.setBeanClass(GridFsTemplate.class);
                    ConstructorArgumentValues gridFsTemplateConstructorArgumentValues = new ConstructorArgumentValues();
                    gridFsTemplateConstructorArgumentValues.addGenericArgumentValue(new RuntimeBeanReference(name + "GridFsMongoDatabaseFactory"));
                    gridFsTemplateConstructorArgumentValues.addGenericArgumentValue(new RuntimeBeanReference(name + "MappingMongoConverter"));
                    gridFsTemplate.setConstructorArgumentValues(gridFsTemplateConstructorArgumentValues);
                    registry.registerBeanDefinition(name + "GridFsTemplate", gridFsTemplate);
                    for (String db : dbs) {
                        if (db.equals(name)) {
                            continue;
                        }
                        logger.debug("start register bean alias, its id is {},alias is {} ", name + "MongoTemplate", db + "MongoTemplate");
                        registry.registerAlias(name + "MongoTemplate", db + "MongoTemplate");
                        logger.debug("start register bean alias, its id is {},alias is {}", name + "GridFsTemplate", db + "GridFsTemplate");
                        registry.registerAlias(name + "GridFsTemplate", db + "GridFsTemplate");
                    }
                    if (isDefault) {
                        logger.info("start config default MongoTemplate({}) and GridFsTemplate({})", name + "MongoTemplate", name + "GridFsTemplate");
                        mongoTemplate.setPrimary(true);
                        gridFsTemplate.setPrimary(true);
                        registry.registerAlias(name + "GridFsTemplate", DEFAULT_GRID_FS_TEMPLATE);
                        registry.registerAlias(name + "MongoTemplate", DEFAULT_MONGO_TEMPLATE);
                    }
                    //注册事务管理器
                    GenericBeanDefinition transactionManager = new GenericBeanDefinition();

                    if (dynamicDatabase) {
                        transactionManager.setScope("refresh");
                    }
                    if (tm != null && tm.equalsIgnoreCase("true")) {
                        ConstructorArgumentValues transactionManagerConstructorArgumentValues = new ConstructorArgumentValues();
                        transactionManagerConstructorArgumentValues.addGenericArgumentValue(new RuntimeBeanReference(name + "MongoDatabaseFactory"));
                        transactionManager.setBeanClass(MongoTransactionManager.class);
                        transactionManager.setConstructorArgumentValues(transactionManagerConstructorArgumentValues);
                    } else {
                        //默认不使用事务
                        transactionManager.setBeanClass(EmptyTransaction.class);
                    }
                    registry.registerBeanDefinition(name + "TransactionManager", transactionManager);
                    logger.debug("the transactionManager which id is {},its original id is{}", name, name + "TransactionManager");
                    for (String db : dbs) {
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
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}

