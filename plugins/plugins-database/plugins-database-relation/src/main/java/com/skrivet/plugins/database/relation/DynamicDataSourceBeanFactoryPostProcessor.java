package com.skrivet.plugins.database.relation;


import com.skrivet.core.common.annotations.DynamicDataBase;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@ConditionalOnProperty(name = "skrivet.dynamicDatabase", havingValue = "true")
public class DynamicDataSourceBeanFactoryPostProcessor implements BeanFactoryPostProcessor, EnvironmentAware {

    private Environment environment;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        try {
            for (String name : configurableListableBeanFactory.getBeanDefinitionNames()) {
                BeanDefinition beanDefinition = configurableListableBeanFactory.getBeanDefinition(name);
                String className = beanDefinition.getBeanClassName();
                if (!StringUtils.isEmpty(className) && (className.startsWith("com.skrivet") || className.startsWith(environment.getProperty("skrivet.project.package")))) {
                    Class clz = Class.forName(className);
                    if (clz.getAnnotation(DynamicDataBase.class) != null) {
                        beanDefinition.setScope("refresh");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment=environment;
    }
}
