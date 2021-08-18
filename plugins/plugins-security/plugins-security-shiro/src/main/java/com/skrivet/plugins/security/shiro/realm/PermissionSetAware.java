package com.skrivet.plugins.security.shiro.realm;

import com.skrivet.plugins.security.core.entity.PermissionSet;

public interface PermissionSetAware {
    public PermissionSet getPermissionSet(String userId);
}
