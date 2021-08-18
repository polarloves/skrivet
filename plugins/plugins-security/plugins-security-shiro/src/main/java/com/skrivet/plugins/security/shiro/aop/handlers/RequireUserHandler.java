package com.skrivet.plugins.security.shiro.aop.handlers;

import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.exception.BizExp;
import com.skrivet.core.common.exception.account.AccountAuthExpireExp;
import com.skrivet.core.common.exception.account.AccountNotLoginExp;
import com.skrivet.plugins.security.core.annotations.RequiresPermissions;
import com.skrivet.plugins.security.core.annotations.RequiresRoles;
import com.skrivet.plugins.security.core.annotations.RequiresUser;
import com.skrivet.plugins.security.core.aop.handler.AbstractSecurityHandler;
import com.skrivet.plugins.security.core.holder.FilterHolder;
import org.apache.shiro.SecurityUtils;

import java.lang.annotation.Annotation;

public class RequireUserHandler extends AbstractSecurityHandler {
    @Override
    public boolean support(Class<? extends Annotation> annotation) {
        return annotation == RequiresPermissions.class || annotation == RequiresRoles.class || annotation == RequiresUser.class;
    }

    @Override
    public void check(LoginUser loginUser, Annotation annotation) throws BizExp {
        if (SecurityUtils.getSubject().getPrincipal() == null) {
            if (FilterHolder.getCurrentIdentify() != null) {
                throw new AccountAuthExpireExp();
            } else {
                throw new AccountNotLoginExp();
            }

        }
    }

}
