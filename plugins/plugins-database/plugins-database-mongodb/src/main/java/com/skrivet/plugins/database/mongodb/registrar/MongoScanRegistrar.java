package com.skrivet.plugins.database.mongodb.registrar;

import com.skrivet.plugins.database.core.annotations.Dao;
import com.skrivet.plugins.database.mongodb.annotations.MongoDaoConfig;
import com.skrivet.plugins.database.mongodb.annotations.MongoScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public class MongoScanRegistrar implements ImportBeanDefinitionRegistrar {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(MongoScan.class.getName()));
        final String dbName = annoAttrs.getString("db");
        final boolean registerGridFsTemplate = annoAttrs.getBoolean("registerGridFsTemplate");
        final boolean registerTemplate = annoAttrs.getBoolean("registerTemplate");
        ClassPathBeanDefinitionScanner classPathBeanDefinitionScanner = new ClassPathBeanDefinitionScanner(registry, false) {
            @Override
            protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
                //添加过滤条件
                addIncludeFilter(new AnnotationTypeFilter(Dao.class));
                //调用spring的扫描
                Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
                if (beanDefinitionHolders.size() != 0) {
                    GenericBeanDefinition definition;
                    for (BeanDefinitionHolder holder : beanDefinitionHolders) {
                        boolean registerGridFsTemplateInner = registerGridFsTemplate;
                        boolean registerTemplateInner = registerTemplate;
                        definition = (GenericBeanDefinition) holder.getBeanDefinition();
                        String beanClassName = definition.getBeanClassName();
                        try {
                            Class<?> clz = Class.forName(beanClassName);
                            MongoDaoConfig mongoDaoConfig = clz.getAnnotation(MongoDaoConfig.class);
                            if (mongoDaoConfig != null) {
                                registerTemplateInner = mongoDaoConfig.registerTemplate();
                                registerGridFsTemplateInner = mongoDaoConfig.registerGridFsTemplate();
                            }
                            if (registerTemplateInner) {
                                try {
                                    PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(Class.forName(beanClassName), "mongoTemplate");
                                    if (propertyDescriptor != null) {
                                        Method method = propertyDescriptor.getWriteMethod();
                                        if (method != null) {
                                            if (StringUtils.isEmpty(dbName)) {
                                                definition.getPropertyValues().addPropertyValue("mongoTemplate", new RuntimeBeanReference(MultiMongoConfiguredRegistrar.DEFAULT_MONGO_TEMPLATE));
                                            } else {
                                                definition.getPropertyValues().addPropertyValue("mongoTemplate", new RuntimeBeanReference(dbName + "MongoTemplate"));
                                            }
                                        } else {
                                            MongoScanRegistrar.this.logger.warn("in {}，no property named mongoTemplate,register mongoTemplate failed !", beanClassName);
                                        }
                                    } else {
                                        MongoScanRegistrar.this.logger.warn("in {}，no property named mongoTemplate,register mongoTemplate failed !", beanClassName);
                                    }

                                } catch (BeansException e) {
                                    MongoScanRegistrar.this.logger.warn("in {}，no property named mongoTemplate,register mongoTemplate failed !", beanClassName);
                                }
                            }
                            if (registerGridFsTemplateInner) {
                                try {
                                    PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(Class.forName(beanClassName), "gridFsTemplate");
                                    if (propertyDescriptor != null) {
                                        Method method = propertyDescriptor.getWriteMethod();
                                        if (method != null) {
                                            if (StringUtils.isEmpty(dbName)) {
                                                definition.getPropertyValues().addPropertyValue("gridFsTemplate", new RuntimeBeanReference(MultiMongoConfiguredRegistrar.DEFAULT_GRID_FS_TEMPLATE));
                                            } else {
                                                definition.getPropertyValues().addPropertyValue("gridFsTemplate", new RuntimeBeanReference(dbName + "GridFsTemplate"));
                                            }

                                        } else {
                                            MongoScanRegistrar.this.logger.warn("in {}，no property named gridFsTemplate,register gridFsTemplate failed !", beanClassName);
                                        }
                                    } else {
                                        MongoScanRegistrar.this.logger.warn("in {}，no property named gridFsTemplate,register gridFsTemplate failed !", beanClassName);
                                    }
                                } catch (BeansException e) {
                                    MongoScanRegistrar.this.logger.warn("in {}，no property named gridFsTemplate,register gridFsTemplate failed !", beanClassName);
                                }
                            }
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException("can not scan..." + beanClassName);
                        }
                    }
                }
                return beanDefinitionHolders;
            }
        };


        classPathBeanDefinitionScanner.setBeanNameGenerator(new AnnotationBeanNameGenerator() {
            @Override
            protected boolean isStereotypeWithNameValue(String annotationType, Set<String> metaAnnotationTypes, Map<String, Object> attributes) {
                return super.isStereotypeWithNameValue(annotationType, metaAnnotationTypes, attributes) || annotationType.equals(Dao.class.getCanonicalName());
            }
        });
        classPathBeanDefinitionScanner.scan(annoAttrs.getStringArray("basePackages"));
    }
}
