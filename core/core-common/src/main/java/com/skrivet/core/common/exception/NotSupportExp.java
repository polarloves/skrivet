package com.skrivet.core.common.exception;

import com.skrivet.core.common.enums.Code;

/**
 * 不支持的异常
 */
public class NotSupportExp extends BuildableExp {

    public NotSupportExp() {
        this(null);
    }

    public NotSupportExp(String tip) {
        super(Code.SERVER_NOT_SUPPORT.getCode(), tip);
    }
}
