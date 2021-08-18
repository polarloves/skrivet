package com.skrivet.core.common.aop;

import com.skrivet.core.common.annotations.PublishEvent;
import org.springframework.context.ApplicationEventPublisher;

import java.lang.reflect.Method;

/**
 * 发布事件的切入点
 */
public class PublishEventAnnotationAdvisor extends AbstractAnnotationAdvisor {
    private ApplicationEventPublisher publisher;

    public PublishEventAnnotationAdvisor(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
        setAdvice(new PublishEventMethodInterceptor(publisher));
    }

    @Override
    public boolean matches(Method method, Class<?> aClass) {
        return !isProxy(aClass) && containsAnnotation(method, new Class[]{PublishEvent.class});
    }

}
