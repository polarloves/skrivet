package com.skrivet.supports.realm.web.core.dept.entity.add;

import com.skrivet.core.common.config.Regexps;
import com.skrivet.core.common.entity.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@ApiModel(description = "添加的参数")
public class DeptAddVQ extends BasicEntity {
    @ApiModelProperty(value = "父级编号", example = "1")
    private String parentId;
    @ApiModelProperty(value = "名称", example = "技术部")
    @Length(max = 50, message = "名称最长为50")
    @NotNull(message = "名称不能为空")
    private String text;
    @ApiModelProperty(value = "联系人姓名", example = "恩哥")
    @Length(max = 50, message = "联系人最长为50")
    private String contactPeople;
    @ApiModelProperty(value = "联系人手机号", example = "18615625210")
    @Pattern(regexp = Regexps.REG_PHONE, message = "手机号格式不正确")
    private String contactPhone;
    @ApiModelProperty(value = "联系人邮箱", example = "1107061838@qq.com")
    @Pattern(regexp = Regexps.REG_EMAIL, message = "邮箱格式不正确")
    private String contactEmail;
    @ApiModelProperty(value = "部门简介", example = "这个是测试部门")
    @Length(max = 2000, message = "简介最长为2000")
    private String deptDescribe;
    @ApiModelProperty(value = "排序号", example = "1")
    @NotNull(message = "排序号不能为空")
    private Long orderNum;

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
