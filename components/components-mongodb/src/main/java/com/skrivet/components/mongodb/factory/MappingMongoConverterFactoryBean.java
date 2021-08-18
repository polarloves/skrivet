package com.skrivet.components.mongodb.factory;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

public class MappingMongoConverterFactoryBean implements FactoryBean<MappingMongoConverter>, InitializingBean {
    private MongoProperties properties;
    private MongoMappingContext context;
    private MappingMongoConverter proxy;
    private MongoDatabaseFactory mongoDatabaseFactory;
    private MongoCustomConversions conversions;

    @Override
    public MappingMongoConverter getObject() throws Exception {
        if (this.proxy == null) {
            this.afterPropertiesSet();
        }
        return this.proxy;
    }

    @Override
    public Class<?> getObjectType() {
        return MappingMongoConverter.class;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDatabaseFactory);
        MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, context);
        mappingConverter.setCustomConversions(conversions);
        this.proxy=mappingConverter;
    }


    public MongoProperties getProperties() {
        return properties;
    }

    public void setProperties(MongoProperties properties) {
        this.properties = properties;
    }

    public MongoDatabaseFactory getMongoDatabaseFactory() {
        return mongoDatabaseFactory;
    }

    public void setMongoDatabaseFactory(MongoDatabaseFactory mongoDatabaseFactory) {
        this.mongoDatabaseFactory = mongoDatabaseFactory;
    }

    public MongoMappingContext getContext() {
        return context;
    }

    public void setContext(MongoMappingContext context) {
        this.context = context;
    }

    public MongoCustomConversions getConversions() {
        return conversions;
    }

    public void setConversions(MongoCustomConversions conversions) {
        this.conversions = conversions;
    }
}
