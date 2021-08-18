package com.skrivet.supports.code.web.core.form.entity.update;

import com.skrivet.core.common.annotations.Within;
import com.skrivet.core.common.entity.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@ApiModel(description = "表单修改的参数")
public class FormUpdateVQ extends BasicEntity {
    @NotNull(message = "编号不能为空")
    @ApiModelProperty(value = "数据编号", example = "1", required = true)
    private String id;
    //表单名称
    @NotNull(message = "表单名称不能为空")
    @Length(max = 50, message = "表单名称最长为50")
    @ApiModelProperty(value = "表单名称", example = "test", required = true)
    private String formName;
    //表名
    @ApiModelProperty(value = "表名", example = "SKRIVET_USER", required = true)
    @NotNull(message = "表名不能为空")
    @Length(max = 50, message = "表名最长为50")
    private String dbTableName;
    //包名
    @ApiModelProperty(value = "包名", example = "com.skrivet.test", required = true)
    @NotNull(message = "包名不能为空")
    @Length(max = 255, message = "包名最长为255")
    private String packageName;
    @Length(max = 200, message = "访问路径最长为200")
    @ApiModelProperty(value = "访问路径", example = "/test", required = true)
    private String accessPath;
    //1:普通表单,2:树结构表单,3:父单,4:子表
    @NotNull(message = "表单类型不能为空")
    @Within(value = {"1", "2", "3", "4"})
    @ApiModelProperty(value = "表单类型", example = "1", required = true, allowableValues = "1,2,3,4")
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
    @ApiModelProperty(value = "是否添加操作人信息")
    private boolean appendOperator;
    @ApiModelProperty(value = "是否添加备用字段")
    private boolean appendRemark;
    @ApiModelProperty(value = "是否启用缓存")
    private boolean cacheEnable;
    @Length(max = 50, message = "缓存KEY最长为50")
    @ApiModelProperty(value = "缓存key")
    private String cacheKey;
    @ApiModelProperty(value = "备注字段个数")
    private Integer appendRemarkCount;
    @ApiModelProperty(value = "版本号")
    @Length(max = 255, message = "版本号最长为255")
    private String version;
    @ApiModelProperty(value = "框架版本号")
    @Length(max = 255, message = "框架版本号最长为255")
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
