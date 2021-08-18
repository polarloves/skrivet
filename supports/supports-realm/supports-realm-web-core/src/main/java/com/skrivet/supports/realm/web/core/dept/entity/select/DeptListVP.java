package com.skrivet.supports.realm.web.core.dept.entity.select;

import com.skrivet.core.common.entity.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "部门列表")
public class DeptListVP extends BasicEntity {
    @ApiModelProperty(value = "部门编号", example = "1")
    private String id;
    @ApiModelProperty(value = "父级编号", example = "1")
    private String parentId;
    @ApiModelProperty(value = "名称", example = "技术部")
    private String text;
    @ApiModelProperty(value = "联系人姓名", example = "恩哥")
    private String contactPeople;
    @ApiModelProperty(value = "联系人手机号", example = "18615625210")
    private String contactPhone;
    @ApiModelProperty(value = "联系人邮箱", example = "1107061838@qq.com")
    private String contactEmail;
    @ApiModelProperty(value = "部门简介", example = "这个是测试部门")
    private String deptDescribe;
    @ApiModelProperty(value = "排序号", example = "1")
    private Long orderNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getContactPeople() {
        return contactPeople;
    }

    public void setContactPeople(String contactPeople) {
        this.contactPeople = contactPeople;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getDeptDescribe() {
        return deptDescribe;
    }

    public void setDeptDescribe(String deptDescribe) {
        this.deptDescribe = deptDescribe;
    }

    public Long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }
}
