package com.skrivet.supports.file.web.impl.mongodb;

import com.skrivet.plugins.database.mongodb.annotations.MongoScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MongoScan(basePackages = "com.skrivet.supports.file.web.impl.mongodb", db = "file",registerTemplate = false)
public class FileStoreConfig {
}
