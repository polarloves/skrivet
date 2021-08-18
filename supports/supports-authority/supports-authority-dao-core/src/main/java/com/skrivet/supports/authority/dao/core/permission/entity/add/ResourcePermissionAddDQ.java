package com.skrivet.supports.authority.dao.core.permission.entity.add;

import com.skrivet.core.common.entity.BasicEntity;

import java.util.List;

public class ResourcePermissionAddDQ extends BasicEntity {
    private String resourceId;
    private List<ResourcePermission> permissions;

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public List<ResourcePermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<ResourcePermission> permissions) {
        this.permissions = permissions;
    }

    public static class ResourcePermission extends BasicEntity {
        private String id;
        private String permissionId;

        public ResourcePermission() {
        }

        public ResourcePermission(String id, String permissionId) {
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
