package com.skrivet.plugins.security.service.aop.handlers;

import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.exception.BizExp;
import com.skrivet.core.common.exception.account.PermissionDeniedExp;
import com.skrivet.plugins.security.core.annotations.RequiresRoles;
import com.skrivet.plugins.security.core.aop.handler.AbstractSecurityHandler;
import com.skrivet.plugins.security.core.entity.PermissionSet;
import com.skrivet.plugins.security.core.entity.User;
import com.skrivet.plugins.security.core.enums.Logical;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;

public class RequireRoleHandler extends AbstractSecurityHandler {
    @Override
    public boolean support(Class<? extends Annotation> annotation) {
        return annotation == RequiresRoles.class;
    }

    @Override
    public void check(LoginUser loginUser, Annotation annotation) throws BizExp {
        super.check(loginUser, annotation);
        RequiresRoles requiresRoles = (RequiresRoles) annotation;
        Logical logical = requiresRoles.logical();
        String[] values = requiresRoles.value();
        boolean flag = false;
        if (logical == Logical.AND) {
            flag = true;
        }
        for (String role : values) {
            boolean hasRole = false;
            if (!CollectionUtils.isEmpty(loginUser.getRoles())) {
                for (String userRole : loginUser.getRoles()) {
                    if (role.equals(userRole)) {
                        hasRole = true;
                        break;
                    }
                }
            }
            switch (logical) {
                case OR:
                    flag = flag || hasRole;
                    break;
                case AND:
                    flag = flag && hasRole;
                    break;
            }
        }
        if (!flag) {
            throw new PermissionDeniedExp();
        }
    }
}
