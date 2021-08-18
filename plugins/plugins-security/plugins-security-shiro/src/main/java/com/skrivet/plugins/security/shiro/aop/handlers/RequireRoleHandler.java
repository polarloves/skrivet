package com.skrivet.plugins.security.shiro.aop.handlers;

import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.exception.BizExp;
import com.skrivet.core.common.exception.account.AccountNotLoginExp;
import com.skrivet.core.common.exception.account.PermissionDeniedExp;
import com.skrivet.plugins.security.core.annotations.RequiresRoles;
import com.skrivet.plugins.security.core.aop.handler.AbstractSecurityHandler;
import com.skrivet.plugins.security.core.enums.Logical;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;

import java.lang.annotation.Annotation;
import java.util.Arrays;

public class RequireRoleHandler extends AbstractSecurityHandler {
    @Override
    public boolean support(Class<? extends Annotation> annotation) {
        return annotation == RequiresRoles.class;
    }

    @Override
    public void check(LoginUser loginUser, Annotation annotation) throws BizExp {
        if (SecurityUtils.getSubject().getPrincipal() == null) {
            throw new AccountNotLoginExp();
        }
        RequiresRoles rrAnnotation = (RequiresRoles) annotation;
        String[] roles = rrAnnotation.value();
        try {
            if (roles.length == 1) {
                SecurityUtils.getSubject().checkRole(roles[0]);
            } else if (Logical.AND.equals(rrAnnotation.logical())) {
                SecurityUtils.getSubject().checkRoles(Arrays.asList(roles));
            } else {
                if (Logical.OR.equals(rrAnnotation.logical())) {
                    boolean hasAtLeastOneRole = false;
                    for (String role : roles) {
                        if (SecurityUtils.getSubject().hasRole(role)) {
                            hasAtLeastOneRole = true;
                        }
                    }
                    if (!hasAtLeastOneRole) {
                        SecurityUtils.getSubject().checkRole(roles[0]);
                    }
                }
            }
        } catch (AuthorizationException e) {
            throw new PermissionDeniedExp();
        }
    }
}

