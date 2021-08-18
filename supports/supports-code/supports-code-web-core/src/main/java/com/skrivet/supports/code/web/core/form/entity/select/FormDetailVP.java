package com.skrivet.supports.code.web.core.form.entity.select;

import com.skrivet.core.common.entity.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel(description = "表单详情")
public class FormDetailVP extends BasicEntity {
    @ApiModelProperty(value = "表单编号")
    private String id;
    @ApiModelProperty(value = "表单名称")
    private String formName;
    //表名
    @ApiModelProperty(value = "表名")
    private String dbTableName;
    //包名
    @ApiModelProperty(value = "包名")
    private String packageName;
    @ApiModelProperty(value = "访问路径")
    private String accessPath;
    //1:普通表单,2:树结构表单,3:父单,4:子表
    @ApiModelProperty(value = "表单类型")
    private Integer formType;
    // 生成器模板
    @ApiModelProperty(value = "生成器模板")
    private String template;
    //上级模板,当type为4时有效
    @ApiModelProperty(value = "上级模板")
    private String parentCode;
    @ApiModelProperty(value = "项目编号")
    private String projectId;
    @ApiModelProperty(value = "groupId")
    private String groupId;
    @ApiModelProperty(value = "模块名称")
    private String moduleName;
    @ApiModelProperty(value = "是否添加操作人信息")
    private boolean appendOperator;
    @ApiModelProperty(value = "是否添加备用字段")
    private boolean appendRemark;
    @ApiModelProperty(value = "是否启用缓存")
    private boolean cacheEnable;
    @ApiModelProperty(value = "缓存key")
    private String cacheKey;
    @ApiModelProperty(value = "备注字段个数")
    private Integer appendRemarkCount;
    @NotNull(message = "版本号不能为空")
    @ApiModelProperty(value = "版本号")
    private String version;
    @ApiModelProperty(value = "框架版本号")
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
