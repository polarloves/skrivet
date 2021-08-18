package com.skrivet.core.common.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 精通抽象切入点对象
 */
public abstract class AbstractAnnotationAdvisor extends StaticMethodMatcherPointcutAdvisor {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected boolean containsAnnotation(Method method, Class<? extends Annotation>[] classes) {
        for (Class<? extends Annotation> clz : classes) {
            Annotation a = AnnotationUtils.findAnnotation(method, clz);
            if (a != null) {
                return true;
            }
        }
        return false;
    }
    protected boolean isProxy(Class<?> aClass) {
        return (Proxy.isProxyClass(aClass) || aClass.getName().contains("$$"));
    }
    protected  boolean containsAnnotation(Class<?> aClass, Class<? extends Annotation>[] classes) {
        for (Class<? extends Annotation> clz : classes) {
            if (aClass.getAnnotation(clz) != null) {
                return true;
            }
        }
        return false;
    }
}
