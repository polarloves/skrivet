package com.skrivet.core.common.exception.account;

import com.skrivet.core.common.enums.Code;
import com.skrivet.core.common.exception.IgnoreLoggedExp;

/**
 * 认证账号被禁言的异常
 */
public class AccountDisableExp extends IgnoreLoggedExp {

    public AccountDisableExp() {
        super(Code.ACCOUNT_DISABLE.getCode(),null);
    }
    public AccountDisableExp(String tip) {
        super(Code.ACCOUNT_DISABLE.getCode(),tip);
    }
}
