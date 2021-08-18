package com.skrivet.core.common.exception;

import com.skrivet.core.common.enums.Code;

/**
 * 数据不存在的异常
 */
public class DataNotExistExp extends IgnoreLoggedExp {
    public DataNotExistExp() {
        this(null);
    }

    public DataNotExistExp(String tip) {
        super(Code.COMMON_DATA_NOT_EXIST.getCode(), tip);
    }

}
