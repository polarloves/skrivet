package com.skrivet.supports.authority.dao.core.role.entity.add;

import com.skrivet.core.common.entity.BasicEntity;

import java.util.List;

public class UserRoleAddDQ extends BasicEntity {
    private String userId;
    private List<UserRole> roles;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    public static class UserRole extends BasicEntity {
        private String id;
        private String roleId;

        public UserRole() {
        }

        public UserRole(String id, String roleId) {
            this.id = id;
            this.roleId = roleId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRoleId() {
            return roleId;
        }

        public void setRoleId(String roleId) {
            this.roleId = roleId;
        }
    }

}
