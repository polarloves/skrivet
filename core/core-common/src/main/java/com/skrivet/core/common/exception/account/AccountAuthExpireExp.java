package com.skrivet.core.common.exception.account;

import com.skrivet.core.common.enums.Code;
import com.skrivet.core.common.exception.IgnoreLoggedExp;

/**
 * 认证账号过期异常
 */
public class AccountAuthExpireExp extends IgnoreLoggedExp {

    public AccountAuthExpireExp() {
        super(Code.ACCOUNT_AUTH_EXPIRE.getCode(),null);
    }

}
