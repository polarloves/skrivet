package com.skrivet.supports.data.service.core.dict.entity.select;


import com.skrivet.plugins.service.core.entity.BasicPageSQ;


public class DictSelectPageSQ extends BasicPageSQ {
    private String text;
    private String value;
    private String remark;
    private String groupId;
    private String groupName;
    private Long orderNum;
    public String getText() {
        return text;
    }

    public Long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
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
