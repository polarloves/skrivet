package com.skrivet.core.common.config;

import com.skrivet.core.common.aop.PublishEventAnnotationAdvisor;
import com.skrivet.core.common.convert.CodeConvert;
import com.skrivet.core.common.convert.EntityConvert;
import com.skrivet.core.common.convert.IdGenerator;
import com.skrivet.core.common.convert.impl.CodeConvertImpl;
import com.skrivet.core.common.convert.impl.JsonEntityConvert;
import com.skrivet.core.common.convert.impl.UUIDGenerator;
import com.skrivet.core.common.event.SkrivetEvent;
import com.skrivet.core.common.event.SkrivetEventSource;
import com.skrivet.core.common.listener.SkrivetEventListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.event.EventListener;
import org.springframework.util.CollectionUtils;

import java.util.List;

@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@Configuration
@EnableAutoConfiguration
public class CommonConfig {
    //事件监听器
    @Autowired(required = false)
    private List<SkrivetEventListenerAdapter> skrivetEventListenerList;

    public CommonConfig(List<SkrivetEventListenerAdapter> skrivetEventListenerList) {
        this.skrivetEventListenerList = skrivetEventListenerList;
    }
    //注册事件发布的切入点
    @Bean
    public PublishEventAnnotationAdvisor publishEventAnnotationAdvisor(ApplicationEventPublisher publisher) {
        PublishEventAnnotationAdvisor publishEventAnnotationAdvisor = new PublishEventAnnotationAdvisor(publisher);
        return publishEventAnnotationAdvisor;
    }
    //事件监听器
    @EventListener
    public void onEvent(SkrivetEvent skrivetEvent) {
        SkrivetEventSource skrivetEventSource = (SkrivetEventSource) skrivetEvent.getSource();
        if (!CollectionUtils.isEmpty(skrivetEventListenerList)) {
            skrivetEventListenerList.stream().forEach(listener -> {
                if(listener.supports(skrivetEventSource.getName(),skrivetEventSource.getAction())){
                    listener.onEvent(skrivetEventSource.getId());
                }
            });
        }
    }


    @Bean
    @ConditionalOnMissingBean(CodeConvert.class)
    public CodeConvert codeConvert() {
        CodeConvertImpl codeConvert = new CodeConvertImpl();
        return codeConvert;
    }

    @Bean
    @ConditionalOnMissingBean(IdGenerator.class)
    public IdGenerator uuidGenerator() {
        UUIDGenerator uuidGenerator = new UUIDGenerator();
        return uuidGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(EntityConvert.class)
    public EntityConvert entityConvert() {
        EntityConvert uuidGenerator = new JsonEntityConvert();
        return uuidGenerator;
    }

}
