package com.skrivet.supports.realm.web.core.user.entity.select;


import com.skrivet.core.common.annotations.Within;
import com.skrivet.web.core.entity.BasicPageVQ;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(description = "查询用户列表参数")
public class UserSelectPageVQ extends BasicPageVQ {
    /**
     * 用户名
     **/
    @ApiModelProperty(value = "用户名", example = "admin")
    private String userName;

    /**
     * 昵称
     **/
    @ApiModelProperty(value = "昵称", example = "PolarLoves")
    private String nickName;

    /**
     * 手机号
     **/
    @ApiModelProperty(value = "手机号",example = "18615625210")
    private String phone;
    /**
     * 邮箱
     **/
    @ApiModelProperty(value = "邮箱",example = "1107061838@qq.com")
    private String email;
    /**
     * 所属机构编号
     **/
    @ApiModelProperty(value = "所属机构编号",example = "10")
    private String orgId;
    /**
     * 创建日期
     **/
    @ApiModelProperty(value = "创建日期",example = "2019-01-01 12:00:00")
    private String createDate;
    /**
     * 登录次数
     */
    @ApiModelProperty(value = "登录次数",example = "10")
    private Long loginCount;
    /**
     * 最后一次登录ip
     */
    @ApiModelProperty(value = "最后一次登录ip",example = "192.168.1.1")
    private String lastLoginIp;
    /**
     * 最后一次登录时间
     */
    @ApiModelProperty(value = "最后一次登录时间",example = "2019-01-01 12:00:00")
    private String lastLoginDate;
    /**
     * 状态,0-禁用，1-正常
     */
    @Within(value = {"0","1"},message = "状态只能为0或者1")
    @ApiModelProperty(value = "状态,0-禁用，1-正常",example = "0",allowableValues = "0,1")
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
