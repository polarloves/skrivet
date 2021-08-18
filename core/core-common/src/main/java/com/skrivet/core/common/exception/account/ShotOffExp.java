package com.skrivet.core.common.exception.account;

import com.skrivet.core.common.enums.Code;
import com.skrivet.core.common.exception.IgnoreLoggedExp;

/**
 * 踢出下线的异常
 */
public class ShotOffExp extends IgnoreLoggedExp {

    public ShotOffExp(String tip) {
        super(Code.ACCOUNT_FORCE_DOWN_LINE.getCode(), tip);
    }

    public ShotOffExp() {
        super(Code.ACCOUNT_FORCE_DOWN_LINE.getCode(), null);
    }
}