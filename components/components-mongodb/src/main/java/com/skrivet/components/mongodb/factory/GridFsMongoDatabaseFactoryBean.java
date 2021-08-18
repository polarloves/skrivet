package com.skrivet.components.mongodb.factory;

import com.mongodb.ClientSessionOptions;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoDatabase;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * mongodb存储文件的操作类
 */
public class GridFsMongoDatabaseFactoryBean implements MongoDatabaseFactory {

    private final MongoDatabaseFactory mongoDatabaseFactory;

    private final MongoProperties properties;

    public GridFsMongoDatabaseFactoryBean(MongoDatabaseFactory mongoDatabaseFactory, MongoProperties properties) {
        Assert.notNull(mongoDatabaseFactory, "MongoDatabaseFactory must not be null");
        Assert.notNull(properties, "Properties must not be null");
        this.mongoDatabaseFactory = mongoDatabaseFactory;
        this.properties = properties;
    }

    @Override
    public MongoDatabase getMongoDatabase() throws DataAccessException {
        String gridFsDatabase = this.properties.getGridFsDatabase();
        if (StringUtils.hasText(gridFsDatabase)) {
            return this.mongoDatabaseFactory.getMongoDatabase(gridFsDatabase);
        }
        return this.mongoDatabaseFactory.getMongoDatabase();
    }

    @Override
    public MongoDatabase getMongoDatabase(String dbName) throws DataAccessException {
        return this.mongoDatabaseFactory.getMongoDatabase(dbName);
    }

    @Override
    public PersistenceExceptionTranslator getExceptionTranslator() {
        return this.mongoDatabaseFactory.getExceptionTranslator();
    }

    @Override
    public ClientSession getSession(ClientSessionOptions options) {
        return this.mongoDatabaseFactory.getSession(options);
    }

    @Override
    public MongoDatabaseFactory withSession(ClientSession session) {
        return this.mongoDatabaseFactory.withSession(session);
    }

    public MongoDatabaseFactory getMongoDatabaseFactory() {
        return mongoDatabaseFactory;
    }

    public MongoProperties getProperties() {
        return properties;
    }
}