package com.skrivet.core.common.exception.account;

import com.skrivet.core.common.enums.Code;
import com.skrivet.core.common.exception.IgnoreLoggedExp;

import java.io.Serializable;

/**
 * 权限被拒绝的异常
 */
public class PermissionDeniedExp extends IgnoreLoggedExp {

    public PermissionDeniedExp() {
        super(Code.ACCOUNT_PERMISSION_DENIED.getCode(), null);
    }
}
