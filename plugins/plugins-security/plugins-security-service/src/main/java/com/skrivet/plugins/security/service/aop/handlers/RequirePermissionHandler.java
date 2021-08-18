package com.skrivet.plugins.security.service.aop.handlers;

import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.exception.BizExp;
import com.skrivet.core.common.exception.account.PermissionDeniedExp;
import com.skrivet.plugins.security.core.annotations.RequiresPermissions;
import com.skrivet.plugins.security.core.aop.handler.AbstractSecurityHandler;
import com.skrivet.plugins.security.core.entity.PermissionSet;
import com.skrivet.plugins.security.core.entity.User;
import com.skrivet.plugins.security.core.enums.Logical;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;

public class RequirePermissionHandler extends AbstractSecurityHandler {
    @Override
    public boolean support(Class<? extends Annotation> annotation) {
        return annotation == RequiresPermissions.class;
    }

    @Override
    public void check(LoginUser loginUser, Annotation annotation) throws BizExp {
        super.check(loginUser, annotation);
        RequiresPermissions requiresPermissions = (RequiresPermissions) annotation;
        Logical logical = requiresPermissions.logical();
        String[] values = requiresPermissions.value();
        boolean flag = false;
        if (logical == Logical.AND) {
            flag = true;
        }
        for (String role : values) {
            boolean hasPermission = false;
            if (!CollectionUtils.isEmpty(loginUser.getPermissions())) {
                for (String userPermission : loginUser.getPermissions()) {
                    if (role.equals(userPermission)) {
                        hasPermission = true;
                        break;
                    }
                }
            }
            switch (logical) {
                case OR:
                    flag = flag || hasPermission;
                    break;
                case AND:
                    flag = flag && hasPermission;
                    break;
            }
        }
        if (!flag) {
            throw new PermissionDeniedExp();
        }
    }

}
