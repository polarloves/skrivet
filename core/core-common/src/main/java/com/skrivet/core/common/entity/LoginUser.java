package com.skrivet.core.common.entity;

import java.io.Serializable;
import java.util.Set;

public class LoginUser  implements Serializable {
    public static LoginUser IGNORED=new LoginUser(true);
    public static LoginUser EMPTY=new LoginUser(false);
    /** 用户编号 **/
    private String id;
    /** 用户账户 **/
    private String account;
    /** 是否执行权限校验标识符 **/
    private boolean ignoreSecurityCheck=false;
    private Set<String> permissions;
    private Set<String> roles;
    public boolean isIgnoreSecurityCheck() {
        return ignoreSecurityCheck;
    }

    public void setIgnoreSecurityCheck(boolean ignoreSecurityCheck) {
        this.ignoreSecurityCheck = ignoreSecurityCheck;
    }

    public LoginUser() {
    }

    public LoginUser(boolean ignoreSecurityCheck) {
        this.ignoreSecurityCheck = ignoreSecurityCheck;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
