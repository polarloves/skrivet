package com.skrivet.plugins.database.relation;

import com.skrivet.core.toolkit.PropertyToolkit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.MethodParameter;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Set;

import static skrivet.springframework.database.relation.DataBaseAutoConfiguredRegistrar.DATABASE_PREFIX;


public class DataSourceFactoryBean implements FactoryBean<DataSource>, InitializingBean, DisposableBean, EnvironmentAware, BeanFactoryAware {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    private Class<? extends DataSource> dataSourceClass;
    private DataSource proxy;
    private Environment environment;
    private String name;
    private BeanFactory beanFactory;
    private String destroyMethod;


    public void setName(String name) {
        this.name = name;
    }

    @Override
    public DataSource getObject() throws Exception {
        if (this.proxy == null) {
            this.afterPropertiesSet();
        }
        return this.proxy;
    }

    @Override
    public Class<?> getObjectType() {
        return this.proxy == null ? DataSource.class : proxy.getClass();
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        this.destroyMethod = environment.getProperty(DATABASE_PREFIX + "." + name + ".destroyMethod");
        this.dataSourceClass = (Class<? extends DataSource>) Class.forName(environment.getProperty(DATABASE_PREFIX + "." + name + ".type"));
        Set<String> properties = PropertyToolkit.getChildNames(DATABASE_PREFIX + "." + name + ".properties", environment);
        logger.info("start init datasource named {},its destroy method is {} ,config info is {}", name, destroyMethod, properties);
        this.proxy = dataSourceClass.newInstance();
        AbstractBeanFactory beanFactory = (AbstractBeanFactory) this.beanFactory;
        TypeConverter typeConverter = beanFactory.getTypeConverter();
        for (String property : properties) {
            String propertyName = property;
            PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(dataSourceClass, propertyName);
            MethodParameter methodParam = BeanUtils.getWriteMethodParameter(propertyDescriptor);
            String originalValue = environment.getProperty(DATABASE_PREFIX + "." + name + ".properties." + propertyName);
            Object newValue = typeConverter.convertIfNecessary(originalValue, propertyDescriptor.getPropertyType(), methodParam);
            propertyDescriptor.getWriteMethod().invoke(proxy, newValue);
        }
        logger.info("config datasource named {} succeed!", name);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void destroy() throws Exception {
        logger.info("start destroy datasource {}", name);
        if (!StringUtils.isEmpty(destroyMethod)) {
            Method method = BeanUtils.findMethod(dataSourceClass, destroyMethod);
            method.invoke(this.proxy);
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
