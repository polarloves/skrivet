package com.skrivet.core.common.exception;

import com.skrivet.core.common.enums.Code;

/**
 * 未知异常
 */
public class UnknownExp extends BuildableExp{

    public UnknownExp() {
        this(null);
    }

    public UnknownExp(String tip) {
        super(Code.SERVER_UNKNOWN.getCode(), tip);
    }
}
