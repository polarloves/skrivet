package com.skrivet.plugins.security.core.service;

import com.skrivet.core.common.exception.BizExp;
import com.skrivet.plugins.security.core.entity.PermissionSet;

public interface PermissionSetService {
    public PermissionSet selectPermissionSetByUserId(String userId) throws BizExp;
}
