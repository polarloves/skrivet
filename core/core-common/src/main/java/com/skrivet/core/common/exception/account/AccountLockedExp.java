package com.skrivet.core.common.exception.account;

import com.skrivet.core.common.enums.Code;
import com.skrivet.core.common.exception.IgnoreLoggedExp;

import java.io.Serializable;

/**
 * 认证账号被锁定的异常
 */
public class AccountLockedExp extends IgnoreLoggedExp {
    public AccountLockedExp() {
        super(Code.ACCOUNT_LOCKED.getCode(),null);
    }
}
