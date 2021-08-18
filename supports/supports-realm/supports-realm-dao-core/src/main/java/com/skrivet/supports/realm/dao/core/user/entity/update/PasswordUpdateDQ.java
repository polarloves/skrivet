package com.skrivet.supports.realm.dao.core.user.entity.update;

import com.skrivet.core.common.entity.BasicEntity;


public class PasswordUpdateDQ extends BasicEntity {
    private String id;
    /**
     * 密码
     **/
    private String password;

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
