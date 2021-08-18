package com.skrivet.components.mongodb.factory;

import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.mongo.MongoClientFactory;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;


public class MongoClientFactoryBean implements FactoryBean<MongoClient>, InitializingBean, EnvironmentAware {
    private MongoProperties properties;
    private MongoClient proxy;
    private Environment environment;

    @Override
    public MongoClient getObject() throws Exception {
        if (this.proxy == null) {
            this.afterPropertiesSet();
        }
        return this.proxy;
    }

    @Override
    public Class<?> getObjectType() {
        return MongoClient.class;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        proxy = new MongoClientFactory(properties, environment,
                null).createMongoClient(null);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public MongoProperties getProperties() {
        return properties;
    }

    public void setProperties(MongoProperties properties) {
        this.properties = properties;
    }
}
