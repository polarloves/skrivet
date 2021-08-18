package com.skrivet.plugins.queue.core.aop;

import com.skrivet.core.common.entity.ValueWrapper;
import com.skrivet.core.common.util.SpringElUtil;
import com.skrivet.plugins.queue.core.annotations.Publish;
import com.skrivet.plugins.queue.core.publish.Publisher;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ReflectiveMethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.annotation.AnnotationUtils;

import java.io.Serializable;

public class PublishMethodInterceptor implements MethodInterceptor {
    private Publisher publisher;

    public PublishMethodInterceptor(Publisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        if (AopUtils.isAopProxy(methodInvocation.getThis())) {
            return methodInvocation.proceed();
        }
        Publish publish = methodInvocation.getMethod().getAnnotation(Publish.class);
        Object obj = null;
        if (publish.opportunity() == Publish.Opportunity.AFTER) {
            obj = methodInvocation.proceed();
        }
        if (publisher != null) {
            //发送消息...
            String[] paramNames = new DefaultParameterNameDiscoverer().getParameterNames(methodInvocation.getMethod());
            String channel = publish.channelUseExpression() ? SpringElUtil.parserValue(publish.channel(), methodInvocation.getArguments(), paramNames, String.class) : publish.channel();
            Object value = publish.valueUseExpression() ? SpringElUtil.parserValue(publish.value(), methodInvocation.getArguments(), paramNames, obj) : publish.value();
            publisher.publish(channel, new ValueWrapper((Serializable) value), publish.mode());
        }
        if (publish.opportunity() == Publish.Opportunity.BEFORE) {
            obj = methodInvocation.proceed();
        }

        return obj;
    }
}
