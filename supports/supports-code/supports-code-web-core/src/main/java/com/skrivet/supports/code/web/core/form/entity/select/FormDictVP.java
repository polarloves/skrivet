package com.skrivet.supports.code.web.core.form.entity.select;

import com.skrivet.core.common.entity.BasicEntity;

public class FormDictVP extends BasicEntity {
    private String id;
    private String formName;
    private String moduleName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
