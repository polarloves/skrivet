package com.skrivet.supports.realm.web.core.user.entity.update;

import com.skrivet.core.common.entity.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@ApiModel(description = "修改密码的参数")
public class PasswordUpdateVQ extends BasicEntity {
    @ApiModelProperty(value = "编号", example = "da2c3e38052548a49049c9ded123d768")
    @NotNull(message = "编号不能为空")
    private String id;
    /**
     * 密码
     **/
    @ApiModelProperty(value = "新密码", example = "admin")
    @NotNull(message = "新密码不能为空")
    @Length(min = 4, max = 20, message = "新密码最少为4位,最多为20位")
    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
