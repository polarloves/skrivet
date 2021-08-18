package com.skrivet.core.common.exception;

/**
 * 不记录日志的异常
 */
public class IgnoreLoggedExp extends BuildableExp {

    public IgnoreLoggedExp(String code, String tip) {
        super(code, tip);
    }
}
