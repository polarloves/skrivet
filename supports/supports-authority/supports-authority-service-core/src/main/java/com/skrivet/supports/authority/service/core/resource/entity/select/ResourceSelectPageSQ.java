package com.skrivet.supports.authority.service.core.resource.entity.select;

import com.skrivet.plugins.service.core.entity.BasicPageSQ;

public class ResourceSelectPageSQ extends BasicPageSQ {
    private String text;
    private String value;
    private String remark;
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
}
