package com.skrivet.supports.realm.web.core.user.entity.add;

import com.skrivet.core.common.config.Regexps;
import com.skrivet.core.common.entity.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@ApiModel(description = "添加的参数")
public class UserAddVQ extends BasicEntity {
    /**
     * 用户名
     **/
    @ApiModelProperty(value = "用户名", example = "admin")
    @NotNull(message = "用户名不能为空")
    @Pattern(regexp =  Regexps.REG_LETTERS,message = "用户名只能以英文字母,数字组成")
    @Length(min = 4,max = 20,message = "用户名最少为4位,最多为20位")
    private String userName;
    /**
     * 密码
     **/
    @ApiModelProperty(value = "密码", example = "admin")
    @NotNull(message = "密码不能为空")
    @Length(min = 4,max = 20,message = "密码最少为4位,最多为20位")
    private String password;
    /**
     * 头像
     **/
    @ApiModelProperty(value = "用户头像地址")
    @Length(max = 500,message = "头像最多为500位")
    private String headPortrait;
    /**
     * 昵称
     **/
    @ApiModelProperty(value = "昵称", example = "恩哥")
    @Length(max = 20,message = "昵称最多为20位")
    private String nickName;

    /**
     * 手机号
     **/
    @ApiModelProperty(value = "手机号", example = "18615625210")
    @Pattern(regexp =  Regexps.REG_PHONE,message = "手机号格式不正确")
    private String phone;
    /**
     * 邮箱
     **/
    @ApiModelProperty(value = "邮箱", example = "1107061838@qq.com")
    @Pattern(regexp =  Regexps.REG_EMAIL,message = "邮箱格式不正确")
    private String email;
    /**
     * 所属机构编号
     **/
    @ApiModelProperty(value = "所属机构编号")
    @Length(max = 50,message = "机构编号最多50位")
    private String orgId;

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


}
