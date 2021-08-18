package com.skrivet.supports.data.service.core.system.entity.add;

import com.skrivet.core.common.entity.BasicEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;


public class SystemAddSQ extends BasicEntity {
    @NotNull(message = "内容不能为空")
    @Length(max = 50,message = "内容最长为50")
    private String text;
    @NotNull(message = "键不能为空")
    @Length(max = 50,message = "键最长为50")
    private String sysKey;
    @NotNull(message = "值不能为空")
    @Length(max = 50,message = "字典值最长为50")
    private String sysValue;
    @Length(max = 500,message = "字典备注最长为500")
    private String remark;
    @NotNull(message = "排序号不能为空")
    private Long orderNum;
    @NotNull(message = "组编号不能为空")
    @Length(max = 50,message = "组编号最长为50")
    private String groupId;
    @NotNull(message = "组名不能为空")
    @Length(max = 50,message = "组名最长为50")
    private String groupName;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSysKey() {
        return sysKey;
    }

    public void setSysKey(String sysKey) {
        this.sysKey = sysKey;
    }

    public String getSysValue() {
        return sysValue;
    }

    public void setSysValue(String sysValue) {
        this.sysValue = sysValue;
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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
