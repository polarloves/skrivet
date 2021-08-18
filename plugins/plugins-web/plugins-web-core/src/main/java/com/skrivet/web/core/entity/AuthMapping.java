package com.skrivet.web.core.entity;

import com.skrivet.core.common.entity.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "权限列表")
public class AuthMapping extends BasicEntity {
    @ApiModelProperty(value = "方法名称", example = "insert")
    private String name;
    @ApiModelProperty(value = "此方法需要的权限")
    private String[] permissions;
    @ApiModelProperty(value = "此方法需要的角色")
    private String[] roles;
    @ApiModelProperty(value = "是否需要用户登录")
    private boolean requireLogin;
    @ApiModelProperty(value = "角色关联方式，and 或者 or")
    private String roleLogical;
    @ApiModelProperty(value = "权限关联方式，and 或者 or")
    private String permissionLogical;

    public String getRoleLogical() {
        return roleLogical;
    }

    public void setRoleLogical(String roleLogical) {
        this.roleLogical = roleLogical;
    }

    public String getPermissionLogical() {
        return permissionLogical;
    }

    public void setPermissionLogical(String permissionLogical) {
        this.permissionLogical = permissionLogical;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getPermissions() {
        return permissions;
    }

    public void setPermissions(String[] permissions) {
        this.permissions = permissions;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public boolean isRequireLogin() {
        return requireLogin;
    }

    public void setRequireLogin(boolean requireLogin) {
        this.requireLogin = requireLogin;
    }
}
