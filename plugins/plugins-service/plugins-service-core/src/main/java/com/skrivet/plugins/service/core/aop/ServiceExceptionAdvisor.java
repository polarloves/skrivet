package com.skrivet.plugins.service.core.aop;

import com.skrivet.core.common.aop.AbstractAnnotationAdvisor;
import com.skrivet.core.common.aop.Order;
import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ServiceExceptionAdvisor extends AbstractAnnotationAdvisor {
    private static final Class<? extends Annotation>[] SERVICE_ANNOTATION_CLASSES = new Class[]{Service.class};

    public ServiceExceptionAdvisor() {
        setAdvice(new ServiceExceptionMethodInterceptor());
    }

    @Override
    public int getOrder() {
        return Order.EXCEPTION_SERVICE;
    }

    @Override
    public boolean matches(Method method, Class<?> aClass) {
        return !isProxy(aClass) && containsAnnotation(aClass, SERVICE_ANNOTATION_CLASSES);
    }
}
