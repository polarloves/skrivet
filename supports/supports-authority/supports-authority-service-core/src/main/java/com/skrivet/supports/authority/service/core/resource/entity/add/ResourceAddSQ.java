package com.skrivet.supports.authority.service.core.resource.entity.add;

import com.skrivet.core.common.annotations.Within;
import com.skrivet.core.common.entity.BasicEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class ResourceAddSQ extends BasicEntity {
    @NotNull(message = "资源名称不能为空")
    @Length(max = 50, message = "资源名称最长为50")
    private String text;
    @Length(max = 200, message = "资源路径最长为200")
    @NotNull(message = "资源路径不能为空")
    private String value;
    @Length(max = 2000, message = "备注最长为2000")
    private String remark;
    @NotNull(message = "排序号不能为空")
    private Long orderNum;
    @NotNull(message = "类型不能为空")
    @Within(value = "1,2",message = "类型只能是1或者2")
    private Integer type;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
