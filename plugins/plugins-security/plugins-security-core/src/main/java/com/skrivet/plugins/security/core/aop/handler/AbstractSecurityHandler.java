package com.skrivet.plugins.security.core.aop.handler;


import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.exception.BizExp;
import com.skrivet.core.common.exception.account.AccountNotLoginExp;
import java.lang.annotation.Annotation;

public abstract class AbstractSecurityHandler implements SecurityHandler {


    @Override
    public void check(LoginUser loginUser, Annotation annotation) throws BizExp {
        if (loginUser == null) {
            throw new AccountNotLoginExp();
        }
    }
}
