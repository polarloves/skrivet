package com.skrivet.supports.code.service.core.form.entity.add;

import com.skrivet.core.common.annotations.Within;
import com.skrivet.core.common.entity.BasicEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class FormAddSQ extends BasicEntity {
    //表单名称
    @NotNull(message = "表单名称不能为空")
    @Length(max = 50, message = "表单名称最长为50")
    private String formName;
    //表名
    @NotNull(message = "表名不能为空")
    @Length(max = 50, message = "表名最长为50")
    private String dbTableName;
    //包名
    @NotNull(message = "包名不能为空")
    @Length(max = 255, message = "包名最长为255")
    private String packageName;
    @Length(max = 200, message = "访问路径最长为200")
    private String accessPath;
    //1:普通表单,2:树结构表单,3:父单,4:子表
    @NotNull(message = "表单类型不能为空")
    @Within(value = {"1", "2", "3", "4"}, message = "表单类型不正确")
    private Integer formType;
    // 生成器模板
    private String template;
    //上级模板,当type为4时有效
    private String parentCode;
    @Length(max = 255, message = "项目编号最长为255")
    private String projectId;
    @Length(max = 255, message = "groupId最长为255")
    private String groupId;
    @NotNull(message = "模块名称不能为空")
    @Length(max = 255, message = "模块名称最长为255")
    private String moduleName;
    private boolean appendOperator;
    private boolean appendRemark;
    private boolean cacheEnable;
    @Length(max = 50, message = "缓存KEY最长为50")
    private String cacheKey;
    private Integer appendRemarkCount;
    @Length(max = 255, message = "版本号最长为255")
    private String version;
    @Length(max = 255, message = "框架版本号最长为255")
    private String skrivetVersion;

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

    public Integer getFormType() {
        return formType;
    }

    public void setFormType(Integer formType) {
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
