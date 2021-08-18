package com.skrivet.core.common.exception.account;

import com.skrivet.core.common.enums.Code;
import com.skrivet.core.common.exception.IgnoreLoggedExp;

/**
 * 密码不正确的异常
 */
public class IncorrectPasswordExp extends IgnoreLoggedExp {
    public IncorrectPasswordExp() {
        super(Code.ACCOUNT_INCORRECT_PASSWORD.getCode(),null);
    }
    public IncorrectPasswordExp(String tip) {
        super(Code.ACCOUNT_INCORRECT_PASSWORD.getCode(),tip);
    }
}
