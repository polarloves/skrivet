package com.skrivet.plugins.security.shiro.aop.handlers;

import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.exception.BizExp;
import com.skrivet.core.common.exception.account.AccountNotLoginExp;
import com.skrivet.core.common.exception.account.PermissionDeniedExp;
import com.skrivet.plugins.security.core.annotations.RequiresPermissions;
import com.skrivet.plugins.security.core.aop.handler.AbstractSecurityHandler;
import com.skrivet.plugins.security.core.enums.Logical;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;

import java.lang.annotation.Annotation;

public class RequirePermissionHandler extends AbstractSecurityHandler {
    @Override
    public boolean support(Class<? extends Annotation> annotation) {
        return annotation == RequiresPermissions.class;
    }

    @Override
    public void check(LoginUser loginUser, Annotation annotation) throws BizExp {
        if (SecurityUtils.getSubject().getPrincipal() == null) {
            throw new AccountNotLoginExp();
        }
        RequiresPermissions rpAnnotation = (RequiresPermissions) annotation;
        String[] perms = rpAnnotation.value();
        Subject subject = SecurityUtils.getSubject();
        try {
            if (perms.length == 1) {
                subject.checkPermission(perms[0]);
            } else if (Logical.AND.equals(rpAnnotation.logical())) {
                subject.checkPermissions(perms);
            } else {
                if (Logical.OR.equals(rpAnnotation.logical())) {
                    boolean hasAtLeastOnePermission = false;
                    for (String permission : perms) {
                        if (subject.isPermitted(permission)) {
                            hasAtLeastOnePermission = true;
                        }
                    }
                    if (!hasAtLeastOnePermission) {
                        subject.checkPermission(perms[0]);
                    }
                }
            }
        } catch (AuthorizationException e) {
            throw new PermissionDeniedExp();
        }

    }

}
