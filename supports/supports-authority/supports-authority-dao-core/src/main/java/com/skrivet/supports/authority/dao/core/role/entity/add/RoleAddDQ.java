package com.skrivet.supports.authority.dao.core.role.entity.add;

import com.skrivet.core.common.entity.BasicEntity;

public class RoleAddDQ extends BasicEntity {
    private String id;
    private String text;
    private String value;
    private String remark;
    private Long orderNum;

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


}
