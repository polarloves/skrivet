package com.skrivet.core.common.exception.account;

import com.skrivet.core.common.enums.Code;
import com.skrivet.core.common.exception.IgnoreLoggedExp;

/**
 * 认证账号过期异常
 */
public class IllegalTokenExp extends IgnoreLoggedExp {

    public IllegalTokenExp() {
        super(Code.ILLEGAL_TOKEN_EXPIRE.getCode(),null);
    }

}
