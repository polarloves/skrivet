package com.skrivet.supports.data.service.core.system.entity.select;

import com.skrivet.core.common.entity.BasicEntity;

public class SystemGroupSP extends BasicEntity {
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
