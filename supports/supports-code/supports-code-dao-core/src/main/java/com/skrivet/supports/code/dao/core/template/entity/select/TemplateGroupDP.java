package com.skrivet.supports.code.dao.core.template.entity.select;

import com.skrivet.core.common.entity.BasicEntity;

public class TemplateGroupDP extends BasicEntity {
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
