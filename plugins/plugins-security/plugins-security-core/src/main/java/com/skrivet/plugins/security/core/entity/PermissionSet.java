package com.skrivet.plugins.security.core.entity;

import com.skrivet.core.common.entity.BasicEntity;

import java.util.Set;

public class PermissionSet extends BasicEntity {
    /** 角色列表 **/
    private Set<String> roles;
    /** 权限列表 **/
    private Set<String> permissions;

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }
}
