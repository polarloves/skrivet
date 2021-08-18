package com.skrivet.plugins.security.shiro.aop;

import com.skrivet.plugins.security.core.aop.AbstractSecurityAdvisor;
import com.skrivet.plugins.security.core.aop.handler.SecurityHandler;
import com.skrivet.plugins.security.core.enums.SecurityType;
import com.skrivet.plugins.security.core.service.PermissionSetService;
import com.skrivet.plugins.security.core.service.UserService;

import com.skrivet.plugins.security.shiro.aop.handlers.RequirePermissionHandler;
import com.skrivet.plugins.security.shiro.aop.handlers.RequireRoleHandler;
import com.skrivet.plugins.security.shiro.aop.handlers.RequireUserHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class ShiroSecurityAdvisor extends AbstractSecurityAdvisor {
    private static final Class<? extends Annotation>[] CONTROLLER_ANNOTATION_CLASSES = new Class[]{Controller.class, RestController.class};

    public ShiroSecurityAdvisor() {
        super();
    }

    @Override
    protected SecurityType securityType() {
        return SecurityType.WEB;
    }

    @Override
    public boolean isClassMatches(Class<?> aClass) {
        return containsAnnotation(aClass,CONTROLLER_ANNOTATION_CLASSES);
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
