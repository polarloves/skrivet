package com.skrivet.core.common.exception.account;

import com.skrivet.core.common.enums.Code;
import com.skrivet.core.common.exception.IgnoreLoggedExp;

import java.io.Serializable;

/**
 * 认证账号被删除的异常
 */
public class AccountDeletedExp extends IgnoreLoggedExp {

    public AccountDeletedExp() {
        super(Code.ACCOUNT_DELETE.getCode(),null);
    }

}
