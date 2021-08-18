package com.skrivet.plugins.security.service.aop;

import com.skrivet.core.common.aop.Order;
import com.skrivet.plugins.security.core.aop.AbstractSecurityAdvisor;
import com.skrivet.plugins.security.core.aop.handler.SecurityHandler;
import com.skrivet.plugins.security.core.enums.SecurityType;
import com.skrivet.plugins.security.service.aop.handlers.RequirePermissionHandler;
import com.skrivet.plugins.security.service.aop.handlers.RequireRoleHandler;
import com.skrivet.plugins.security.service.aop.handlers.RequireUserHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class ServiceSecurityAdvisor extends AbstractSecurityAdvisor {
    private static final Class<? extends Annotation>[] SERVICE_ANNOTATION_CLASSES = new Class[]{Component.class, Service.class, Repository.class};

    public ServiceSecurityAdvisor() {
        super();
    }


    @Override
    public int getOrder() {
        return Order.SECURITY_SERVICE;
    }

    @Override
    protected SecurityType securityType() {
        return SecurityType.SERVICE;
    }

    @Override
    public boolean isClassMatches(Class<?> aClass) {
        return containsAnnotation(aClass,SERVICE_ANNOTATION_CLASSES);
    }

    @Override
    public List<SecurityHandler> handlers() {
        List<SecurityHandler> handlers=new ArrayList<>();
        handlers.add(new RequireUserHandler());
        handlers.add(new RequireRoleHandler());
        handlers.add(new RequirePermissionHandler());
        return handlers;
    }
}
