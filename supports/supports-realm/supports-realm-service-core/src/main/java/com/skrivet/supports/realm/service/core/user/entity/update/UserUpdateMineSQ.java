package com.skrivet.supports.realm.service.core.user.entity.update;

import com.skrivet.core.common.entity.BasicEntity;

public class UserUpdateMineSQ extends BasicEntity {
    private String id;
    /**
     * 头像
     **/
    private String headPortrait;
    /**
     * 昵称
     **/
    private String nickName;

    /**
     * 手机号
     **/
    private String phone;
    /**
     * 邮箱
     **/
    private String email;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
