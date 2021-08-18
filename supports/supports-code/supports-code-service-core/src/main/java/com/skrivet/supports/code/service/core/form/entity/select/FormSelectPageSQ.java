package com.skrivet.supports.code.service.core.form.entity.select;


import com.skrivet.core.common.annotations.Within;
import com.skrivet.plugins.service.core.entity.BasicPageSQ;

public class FormSelectPageSQ extends BasicPageSQ {
    //表单名称
    private String formName;
    //表名
    private String dbTableName;
    private String projectId;
    private String groupId;
    private String moduleName;
    @Within(value = {"1", "2", "3", "4"}, message = "表单类型不正确")
    private Integer formType;

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getDbTableName() {
        return dbTableName;
    }

    public void setDbTableName(String dbTableName) {
        this.dbTableName = dbTableName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Integer getFormType() {
        return formType;
    }

    public void setFormType(Integer formType) {
        this.formType = formType;
    }
}
