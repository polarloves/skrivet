package com.skrivet.plugins.queue.core.aop;

import com.skrivet.core.common.aop.AbstractAnnotationAdvisor;
import com.skrivet.plugins.queue.core.annotations.Publish;
import com.skrivet.plugins.queue.core.publish.Publisher;

import java.lang.reflect.Method;

public class PublishAnnotationAdvisor extends AbstractAnnotationAdvisor {
    private Publisher publisher;

    public PublishAnnotationAdvisor(Publisher publisher) {
        this.publisher = publisher;
        setAdvice(new PublishMethodInterceptor(publisher));
    }

    @Override
    public boolean matches(Method method, Class<?> aClass) {
        return !isProxy(aClass) && containsAnnotation(method, new Class[]{Publish.class});
    }

}
