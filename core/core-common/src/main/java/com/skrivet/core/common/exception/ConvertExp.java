package com.skrivet.core.common.exception;

import com.skrivet.core.common.enums.Code;

import java.io.Serializable;

/**
 * 转换异常
 */
public class ConvertExp extends BuildableExp {
    public ConvertExp() {
        this(null);
    }

    public ConvertExp(String tip) {
        super(Code.SERVER_BEAN_CONVERT.getCode(), tip);
    }

}
