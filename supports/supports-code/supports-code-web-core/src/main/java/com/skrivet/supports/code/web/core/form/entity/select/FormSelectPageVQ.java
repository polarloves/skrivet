package com.skrivet.supports.code.web.core.form.entity.select;


import com.skrivet.web.core.entity.BasicPageVQ;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "表单分页查询参数")
public class FormSelectPageVQ extends BasicPageVQ {
    @ApiModelProperty(value = "表单名称")
    private String formName;
    //表名
    @ApiModelProperty(value = "表名")
    private String dbTableName;
    @ApiModelProperty(value = "项目编号")
    private String projectId;
    @ApiModelProperty(value = "groupId")
    private String groupId;
    @ApiModelProperty(value = "模块名称")
    private String moduleName;
    @ApiModelProperty(value = "表单类型")
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
