package com.skrivet.plugins.security.core.entity;

import java.io.Serializable;
import java.util.Set;

/**
 * 用户登录实体
 *
 * @author n
 * @version 1.0
 */
public class User implements Serializable {
    /** 用户编号 **/
    private String id;
    /** 用户账户 **/
    private String account;
    /** 加密后的密码 **/
    private String password;
    /** 加密盐值 **/
    private String salt;
    /**
     * 状态,0-禁用，1-正常
     */
    private Integer state;
    /**
     * 禁用原因
     */
    private String disableReason;
    private Set<String> permissions;
    private Set<String> roles;

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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getDisableReason() {
        return disableReason;
    }

    public void setDisableReason(String disableReason) {
        this.disableReason = disableReason;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

   }
