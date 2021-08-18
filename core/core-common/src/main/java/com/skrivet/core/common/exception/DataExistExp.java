package com.skrivet.core.common.exception;

import com.skrivet.core.common.enums.Code;

/**
 * 数据已存在异常
 */
public class DataExistExp extends IgnoreLoggedExp {
    public DataExistExp() {
        this(null);
    }

    public DataExistExp(String tip) {
        super(Code.COMMON_DATA_EXIST.getCode(), tip);
    }

}
