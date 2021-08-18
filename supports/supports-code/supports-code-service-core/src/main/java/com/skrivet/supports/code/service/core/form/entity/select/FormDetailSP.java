package com.skrivet.supports.code.service.core.form.entity.select;

import com.skrivet.core.common.entity.BasicEntity;


public class FormDetailSP extends BasicEntity {
    private String id;
    //表单名称
    private String formName;
    //表名
    private String dbTableName;
    //包名
    private String packageName;
    private String accessPath;
    //1:普通表单,2:树结构表单,3:父单,4:子表
    private String formType;
    // 生成器模板
    private String template;
    //上级模板,当type为4时有效
    private String parentCode;
    private String projectId;
    private String groupId;
    private String moduleName;
    private boolean appendOperator;
    private boolean appendRemark;
    private boolean cacheEnable;
    private String cacheKey;
    private Integer appendRemarkCount;
    private String version;
    private String skrivetVersion;

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

    public String getDbTableName() {
        return dbTableName;
    }

    public void setDbTableName(String dbTableName) {
        this.dbTableName = dbTableName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAccessPath() {
        return accessPath;
    }

    public void setAccessPath(String accessPath) {
        this.accessPath = accessPath;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
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

    public boolean isAppendOperator() {
        return appendOperator;
    }

    public void setAppendOperator(boolean appendOperator) {
        this.appendOperator = appendOperator;
    }

    public boolean isAppendRemark() {
        return appendRemark;
    }

    public void setAppendRemark(boolean appendRemark) {
        this.appendRemark = appendRemark;
    }

    public boolean isCacheEnable() {
        return cacheEnable;
    }

    public void setCacheEnable(boolean cacheEnable) {
        this.cacheEnable = cacheEnable;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    public Integer getAppendRemarkCount() {
        return appendRemarkCount;
    }

    public void setAppendRemarkCount(Integer appendRemarkCount) {
        this.appendRemarkCount = appendRemarkCount;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSkrivetVersion() {
        return skrivetVersion;
    }

    public void setSkrivetVersion(String skrivetVersion) {
        this.skrivetVersion = skrivetVersion;
    }
}
