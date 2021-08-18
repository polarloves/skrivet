package com.skrivet.supports.authority.web.core.permission.entity.update;

import com.skrivet.core.common.entity.BasicEntity;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class PermissionUpdateVQ extends BasicEntity {
    @ApiModelProperty(value = "权限编号", example = "1",required = true)
    @NotNull(message = "权限编号不能为空")
    private String id;
    @ApiModelProperty(value = "权限名称", example = "后台管理权限",required = true)
    @NotNull(message = "权限名称不能为空")
    @Length(max = 50, message = "权限名称最长为50")
    private String text;
    @ApiModelProperty(value = "权限标识", example = "skrivet:permission:add",required = true)
    @Length(max = 50, message = "权限标识最长为50")
    @NotNull(message = "权限标识不能为空")
    private String value;
    @ApiModelProperty(value = "备注", example = "后台管理权限")
    @Length(max = 2000, message = "备注最长为2000")
    private String remark;
    @ApiModelProperty(value = "排序号", example = "1",required = true)
    @NotNull(message = "排序号不能为空")
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
