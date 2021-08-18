package com.skrivet.supports.realm.service.core.user.entity.add;

import com.skrivet.core.common.config.Regexps;
import com.skrivet.core.common.entity.BasicEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserAddSQ extends BasicEntity {
    private String id;
    /**
     * 用户名
     **/
    @NotNull(message = "用户名不能为空")
    @Pattern(regexp =  Regexps.REG_LETTERS,message = "用户名只能以英文字母,数字组成")
    @Length(min = 4,max = 20,message = "用户名最少为4位,最多为20位")
    private String userName;
    /**
     * 加密后的密码
     **/
    @NotNull(message = "密码不能为空")
    private String password;
    /**
     * 头像
     **/
    @Length(max = 500,message = "头像最多为500位")
    private String headPortrait;
    /**
     * 昵称
     **/
    @Length(max = 20,message = "昵称最多为20位")
    private String nickName;
    /**
     * 创建日期
     **/
    @NotNull(message = "创建日期不能为空")
    private String createDate;
    /**
     * 手机号
     **/
    @Pattern(regexp =  Regexps.REG_PHONE,message = "手机号格式不正确")
    private String phone;
    /**
     * 邮箱
     **/
    @Pattern(regexp =  Regexps.REG_EMAIL,message = "邮箱格式不正确")
    private String email;
    /**
     * 所属机构编号
     **/
    @Length(max = 50,message = "机构编号最多50位")
    private String orgId;
    /**
     * 密码盐值
     **/
    @NotNull(message = "密码盐值不能为空")
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
