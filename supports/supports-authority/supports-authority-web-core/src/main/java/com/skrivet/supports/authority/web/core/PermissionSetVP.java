package com.skrivet.supports.authority.web.core;

import com.skrivet.core.common.entity.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Set;
@ApiModel(description = "角色权限信息")
public class PermissionSetVP extends BasicEntity {
    @ApiModelProperty(value = "角色列表", example = "1")
    /** 角色列表 **/
    private Set<String> roles;
    /** 权限列表 **/
    @ApiModelProperty(value = "权限列表", example = "1")
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
