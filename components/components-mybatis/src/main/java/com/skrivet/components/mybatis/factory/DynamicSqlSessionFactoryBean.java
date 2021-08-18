package com.skrivet.components.mybatis.factory;

import com.skrivet.components.mybatis.builder.YmlBuilder;
import com.skrivet.core.toolkit.PropertyToolkit;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Properties;
import java.util.Set;

import static com.skrivet.components.mybatis.builder.YmlBuilder.DATABASE_PREFIX;


public class DynamicSqlSessionFactoryBean extends SqlSessionFactoryBean implements EnvironmentAware {
    private String database;
    private Environment environment;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        super.onApplicationEvent(event);

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String name = getConfigName();
        YmlBuilder builder = new YmlBuilder(new Properties(), environment, name);
        Configuration configuration = builder.parse();
        setConfiguration(configuration);
        super.afterPropertiesSet();
    }

    @Override
    public SqlSessionFactory getObject() throws Exception {
        return super.getObject();
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    private String getConfigName() {
        Set<String> names = PropertyToolkit.getChildNames(DATABASE_PREFIX, environment);
        for (String name : names) {
            if (StringUtils.isEmpty(database)) {
                //找默认的数据库
                boolean isDefault = environment.getProperty(DATABASE_PREFIX + "." + name + ".default", Boolean.class, false);
                if (isDefault) {
                    return name;
                }
            } else {
                //获取names属性
                List<String> dbs = environment.getProperty(DATABASE_PREFIX + "." + name + ".names", List.class);
                for (String db : dbs) {
                    if (db.equalsIgnoreCase(database)) {
                        return name;
                    }
                }
            }

        }
        return null;
    }
}
