package com.skrivet.core.common.exception;

import com.skrivet.core.common.enums.Code;

/**
 * 校验异常
 */
public class ValidationExp extends IgnoreLoggedExp {
    public ValidationExp() {
        this(null);
    }

    public ValidationExp(String tip) {
        super(Code.COMMON_VALIDATION.getCode(), tip);
    }

}
