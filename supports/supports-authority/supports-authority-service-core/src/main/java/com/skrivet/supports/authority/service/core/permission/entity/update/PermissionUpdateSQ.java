package com.skrivet.supports.authority.service.core.permission.entity.update;

import com.skrivet.core.common.entity.BasicEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class PermissionUpdateSQ extends BasicEntity {
    private String id;
    @NotNull(message = "权限名称不能为空")
    @Length(max = 50, message = "权限名称最长为50")
    private String text;
    @Length(max = 50, message = "权限标识最长为50")
    @NotNull(message = "权限标识不能为空")
    private String value;
    @Length(max = 2000, message = "备注最长为2000")
    private String remark;
    @NotNull(message = "排序号不能为空")
    private Long orderNum;
    private String parentId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
