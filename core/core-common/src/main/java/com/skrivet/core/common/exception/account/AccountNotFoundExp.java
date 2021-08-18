package com.skrivet.core.common.exception.account;

import com.skrivet.core.common.enums.Code;
import com.skrivet.core.common.exception.IgnoreLoggedExp;

import java.io.Serializable;

/**
 * 账号未找到异常
 */
public class AccountNotFoundExp extends IgnoreLoggedExp {
    public AccountNotFoundExp() {
        super(Code.ACCOUNT_UNKNOWN.getCode(),null);
    }
}
