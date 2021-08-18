package com.skrivet.plugins.web.impl.doc;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;
import springfox.documentation.spring.web.readers.operation.OperationParameterReader;
import springfox.documentation.spring.web.readers.parameter.ParameterTypeReader;

@Component
public class DocBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        String names[] = configurableListableBeanFactory.getBeanDefinitionNames();
        for (int i = 0; i < names.length; i++) {
            BeanDefinition beanDefinition = configurableListableBeanFactory.getBeanDefinition(names[i]);
            if(ParameterTypeReader.class.getCanonicalName().equals(beanDefinition.getBeanClassName())){
                beanDefinition.setBeanClassName(DocParameterTypeReader.class.getCanonicalName());
            }
            if(OperationParameterReader.class.getCanonicalName().equals(beanDefinition.getBeanClassName())){
                beanDefinition.setBeanClassName(DocOperationParameterReader.class.getCanonicalName());
            }
        }
    }
}
