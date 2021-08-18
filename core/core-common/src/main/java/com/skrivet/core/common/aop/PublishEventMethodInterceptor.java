package com.skrivet.core.common.aop;

import com.skrivet.core.common.annotations.PublishEvent;
import com.skrivet.core.common.event.SkrivetEvent;
import com.skrivet.core.common.event.SkrivetEventSource;
import com.skrivet.core.common.util.SpringElUtil;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.DefaultParameterNameDiscoverer;

/**
 * 发布事件的拦截器
 */
public class PublishEventMethodInterceptor implements MethodInterceptor {
    private ApplicationEventPublisher publisher;

    public PublishEventMethodInterceptor(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        if (AopUtils.isAopProxy(methodInvocation.getThis())) {
            return methodInvocation.proceed();
        }
        PublishEvent publishEvent = methodInvocation.getMethod().getAnnotation(PublishEvent.class);
        Object obj = null;
        if (publishEvent.opportunity() == PublishEvent.Opportunity.AFTER) {
            obj = methodInvocation.proceed();
        }
        //发送消息...
        String[] paramNames = new DefaultParameterNameDiscoverer().getParameterNames(methodInvocation.getMethod());
        Object id = SpringElUtil.parserValue(publishEvent.id(), methodInvocation.getArguments(), paramNames, obj);
        SkrivetEventSource skrivetEventSource = new SkrivetEventSource();
        skrivetEventSource.setId(id.toString());
        skrivetEventSource.setAction(publishEvent.action());
        skrivetEventSource.setName(publishEvent.name());
        publisher.publishEvent(new SkrivetEvent(skrivetEventSource));
        if (publishEvent.opportunity() == PublishEvent.Opportunity.BEFORE) {
            obj = methodInvocation.proceed();
        }

        return obj;
    }
}
