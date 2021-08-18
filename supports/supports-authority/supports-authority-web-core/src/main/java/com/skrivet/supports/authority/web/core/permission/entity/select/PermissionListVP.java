package com.skrivet.supports.authority.web.core.permission.entity.select;

import com.skrivet.core.common.entity.BasicEntity;
import io.swagger.annotations.ApiModelProperty;

public class PermissionListVP extends BasicEntity {
    @ApiModelProperty(value = "权限编号", example = "1")
    private String id;
    @ApiModelProperty(value = "权限名称", example = "后台管理权限")
    private String text;
    @ApiModelProperty(value = "权限标识", example = "skrivet:permission:add")
    private String value;
    @ApiModelProperty(value = "备注", example = "后台管理权限")
    private String remark;
    @ApiModelProperty(value = "排序号", example = "1")
    private Long orderNum;
    @ApiModelProperty(value = "父级权限编号", example = "1")
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
