package com.skrivet.plugins.security.service.aop.handlers;

import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.exception.BizExp;
import com.skrivet.plugins.security.core.aop.handler.AbstractSecurityHandler;
import com.skrivet.plugins.security.core.entity.User;
import com.skrivet.plugins.security.core.annotations.RequiresPermissions;
import com.skrivet.plugins.security.core.annotations.RequiresRoles;
import com.skrivet.plugins.security.core.annotations.RequiresUser;

import java.lang.annotation.Annotation;

public class RequireUserHandler extends AbstractSecurityHandler {
    @Override
    public boolean support(Class<? extends Annotation> annotation) {
        return annotation == RequiresPermissions.class || annotation == RequiresRoles.class || annotation == RequiresUser.class;
    }

    @Override
    public void check(LoginUser loginUser, Annotation annotation) throws BizExp {
        super.check(loginUser, annotation);
    }
}
