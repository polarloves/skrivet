package com.skrivet.plugins.security.jwt.filter;

import com.skrivet.plugins.security.core.service.PermissionSetService;
import com.skrivet.plugins.security.core.service.ResourceService;
import com.skrivet.plugins.security.core.service.UserService;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.servlet.Filter;

public class JWTFilterFactoryBean implements FactoryBean<JWTFilter>, BeanPostProcessor {
    private JWTFilter instance;
    private String tokenKey;
    private String actionKey;
    private String signKey;
    private long timeout;
    private long refreshBeforeTimeout;
    private ResourceService resourceService;
    private PermissionSetService permissionSetService;
    private UserService userService;
    public JWTFilterFactoryBean(ResourceService resourceService,  UserService userService, PermissionSetService permissionSetService,String tokenKey, String actionKey, String signKey, long timeout, long refreshBeforeTimeout) {
        this.tokenKey = tokenKey;
        this.actionKey = actionKey;
        this.signKey = signKey;
        this.timeout = timeout;
        this.refreshBeforeTimeout = refreshBeforeTimeout;
        this.resourceService = resourceService;
        this.userService=userService;
        this.permissionSetService=permissionSetService;
    }

    @Override
    public JWTFilter getObject() throws Exception {
        if (this.instance == null) {
            this.instance = this.createInstance();
        }

        return this.instance;
    }

    protected JWTFilter createInstance() {
        JWTFilter filter = new JWTFilter(resourceService,userService,permissionSetService,
                tokenKey, actionKey, signKey, timeout, refreshBeforeTimeout);
        return filter;
    }

    public void loadDataBasePermissions() {
        if (this.instance != null) {
            this.instance.loadDataBasePermissions();
        }
    }

    @Override
    public Class<?> getObjectType() {
        return Filter.class;
    }
}
