package com.skrivet.supports.authority.web.core.role.entity.add;

import com.skrivet.core.common.entity.BasicEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class RoleAddVQ extends BasicEntity {
    @NotNull(message = "角色名称不能为空")
    @Length(max = 50, message = "角色名称最长为50")
    private String text;
    @Length(max = 50, message = "角色标识最长为50")
    @NotNull(message = "角色标识不能为空")
    private String value;
    @Length(max = 2000, message = "备注最长为2000")
    private String remark;
    @NotNull(message = "排序号不能为空")
    private Long orderNum;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }



}
