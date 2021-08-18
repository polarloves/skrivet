package com.skrivet.supports.data.dao.core.system.entity.select;

import com.skrivet.core.common.entity.BasicEntity;

public class SystemGroupDP extends BasicEntity {
    private String text;
    private String value;

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
}
