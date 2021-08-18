package com.skrivet.supports.authority.dao.core.resource.entity.select;

import com.skrivet.plugins.database.core.entity.BasicPageDQ;

public class ResourceSelectPageDQ extends BasicPageDQ {
    private String text;
    private String value;
    private String remark;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
