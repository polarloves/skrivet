package com.skrivet.supports.realm.dao.core.user.entity.add;

import com.skrivet.core.common.entity.BasicEntity;

public class UserAddDQ extends BasicEntity {
    private String id;
    /**
     * 用户名
     **/
    private String userName;
    /**
     * 密码
     **/
    private String password;
    /**
     * 头像
     **/
    private String headPortrait;
    /**
     * 昵称
     **/
    private String nickName;
    /**
     * 创建日期
     **/
    private String createDate;
    /**
     * 手机号
     **/
    private String phone;
    /**
     * 邮箱
     **/
    private String email;
    /**
     * 所属机构编号
     **/
    private String orgId;
    /**
     * 密码盐值
     **/
    private String salt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
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

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
