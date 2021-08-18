package com.skrivet.supports.realm.web.core.user.entity.update;

import com.skrivet.core.common.config.Regexps;
import com.skrivet.core.common.entity.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@ApiModel(description = "修改的参数")
public class UserUpdateVQ extends BasicEntity {
    @ApiModelProperty(value = "编号", example = "da2c3e38052548a49049c9ded123d768")
    @NotNull(message = "编号不能为空")
    private String id;
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
    @NotNull(message = "昵称不能为空")
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
    @Pattern(regexp =  Regexps.REG_EMAIL,message = "邮箱格式不正确")
    @ApiModelProperty(value = "邮箱", example = "1107061838@qq.com")
    private String email;
    /**
     * 所属机构编号
     **/
    @Length(max = 50,message = "机构编号最多50位")
    @ApiModelProperty(value = "所属机构编号")
    private String orgId;

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

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}
