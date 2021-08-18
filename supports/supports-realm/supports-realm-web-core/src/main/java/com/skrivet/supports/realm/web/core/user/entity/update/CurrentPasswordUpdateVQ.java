package com.skrivet.supports.realm.web.core.user.entity.update;

import com.skrivet.core.common.config.Regexps;
import com.skrivet.core.common.entity.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@ApiModel(description = "修改密码的参数")
public class CurrentPasswordUpdateVQ extends BasicEntity {

    /**
     * 密码
     **/
    @ApiModelProperty(value = "原始密码", example = "admin")
    @NotNull(message = "原始密码不能为空")
    @Length(min = 4, max = 20, message = "原始密码最少为4位,最多为20位")
    private String password;
    /**
     * 密码
     **/
    @ApiModelProperty(value = "新密码", example = "admin")
    @NotNull(message = "新密码不能为空")
    @Length(min = 4, max = 20, message = "新密码最少为4位,最多为20位")
    private String currentPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }
}
