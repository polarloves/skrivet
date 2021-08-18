package com.skrivet.supports.realm.service.core.user.entity.update;

import com.skrivet.core.common.entity.BasicEntity;

import javax.validation.constraints.NotNull;

public class PasswordUpdateSQ extends BasicEntity {
    @NotNull(message = "编号不能为空")
    private String id;
    /**
     * 密码
     **/
    @NotNull(message = "原始密码不能为空")
    private String password;

    @NotNull(message = "密码盐值不能为空")
    private String salt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
