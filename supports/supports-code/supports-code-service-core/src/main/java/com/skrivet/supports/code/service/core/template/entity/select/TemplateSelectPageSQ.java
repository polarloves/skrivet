package com.skrivet.supports.code.service.core.template.entity.select;


import com.skrivet.plugins.service.core.entity.BasicPageSQ;

public class TemplateSelectPageSQ extends BasicPageSQ {
    private String templateName;
    private Integer templateType;
    private String scope;
    private String groupId;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Integer getTemplateType() {
        return templateType;
    }

    public void setTemplateType(Integer templateType) {
        this.templateType = templateType;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

}
