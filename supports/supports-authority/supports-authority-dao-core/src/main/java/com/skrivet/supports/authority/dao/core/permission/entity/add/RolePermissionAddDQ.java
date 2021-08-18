package com.skrivet.supports.authority.dao.core.permission.entity.add;

import com.skrivet.core.common.entity.BasicEntity;

import java.util.List;

public class RolePermissionAddDQ extends BasicEntity {
    private String roleId;
    private List<RolePermission> permissions;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public List<RolePermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<RolePermission> permissions) {
        this.permissions = permissions;
    }

    public static class RolePermission extends BasicEntity {
        private String id;
        private String permissionId;

        public RolePermission() {
        }

        public RolePermission(String id, String permissionId) {
            this.id = id;
            this.permissionId = permissionId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPermissionId() {
            return permissionId;
        }

        public void setPermissionId(String permissionId) {
            this.permissionId = permissionId;
        }
    }

}
