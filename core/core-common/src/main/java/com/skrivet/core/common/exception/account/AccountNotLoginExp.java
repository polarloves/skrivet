package com.skrivet.core.common.exception.account;

import com.skrivet.core.common.enums.Code;
import com.skrivet.core.common.exception.IgnoreLoggedExp;

import java.io.Serializable;

/**
 * 未登录异常
 */
public class AccountNotLoginExp extends IgnoreLoggedExp {

    public AccountNotLoginExp() {
        super(Code.ACCOUNT_NOT_LOGIN.getCode(),null);
    }
}
