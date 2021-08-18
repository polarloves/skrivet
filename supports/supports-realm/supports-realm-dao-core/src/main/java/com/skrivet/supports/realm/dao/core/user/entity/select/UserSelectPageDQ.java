package com.skrivet.supports.realm.dao.core.user.entity.select;


import com.skrivet.plugins.database.core.entity.BasicPageDQ;

public class UserSelectPageDQ extends BasicPageDQ {
    /**
     * 用户名
     **/
    private String userName;

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
    /**
     * 所属机构编号
     **/
    private String orgId;
    /**
     * 创建日期
     **/
    private String createDate;
    /**
     * 登录次数
     */
    private Long loginCount;
    /**
     * 最后一次登录ip
     */
    private String lastLoginIp;
    /**
     * 最后一次登录时间
     */
    private String lastLoginDate;
    /**
     * 状态,0-禁用，1-正常
     */
    private Integer state;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Long getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Long loginCount) {
        this.loginCount = loginCount;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
