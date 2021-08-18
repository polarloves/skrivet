package com.skrivet.components.mongodb.factory;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.domain.EntityScanner;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.mapping.model.FieldNamingStrategy;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

public class MongoMappingContextFactoryBean implements FactoryBean<MongoMappingContext>, InitializingBean, EnvironmentAware, ApplicationContextAware {
    private MongoProperties properties;
    private MongoMappingContext proxy;
    private Environment environment;
    private ApplicationContext applicationContext;
    private MongoCustomConversions mongoCustomConversions;

    @Override
    public MongoMappingContext getObject() throws Exception {
        if (this.proxy == null) {
            this.afterPropertiesSet();
        }
        return this.proxy;
    }

    public MongoProperties getProperties() {
        return properties;
    }

    public void setProperties(MongoProperties properties) {
        this.properties = properties;
    }

    public MongoCustomConversions getMongoCustomConversions() {
        return mongoCustomConversions;
    }

    public void setMongoCustomConversions(MongoCustomConversions mongoCustomConversions) {
        this.mongoCustomConversions = mongoCustomConversions;
    }

    @Override
    public Class<?> getObjectType() {
        return MongoMappingContext.class;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        PropertyMapper mapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
        MongoMappingContext context = new MongoMappingContext();
        mapper.from(properties.isAutoIndexCreation()).to(context::setAutoIndexCreation);
        context.setInitialEntitySet(new EntityScanner(applicationContext).scan(Document.class, Persistent.class));
        Class<?> strategyClass = properties.getFieldNamingStrategy();
        if (strategyClass != null) {
            context.setFieldNamingStrategy((FieldNamingStrategy) BeanUtils.instantiateClass(strategyClass));
        }
        context.setSimpleTypeHolder(mongoCustomConversions.getSimpleTypeHolder());
        this.proxy = context;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
