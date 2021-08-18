package com.skrivet.plugins.security.core.aop.handler;

import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.exception.BizExp;
import com.skrivet.plugins.security.core.entity.User;
import com.skrivet.plugins.security.core.service.PermissionSetService;
import com.skrivet.plugins.security.core.service.UserService;

import java.lang.annotation.Annotation;

public interface SecurityHandler {
    public boolean support(Class<? extends Annotation> annotation);

    public void check(LoginUser loginUser, Annotation annotation) throws BizExp;
}
