package com.skrivet.supports.code.web.core.form.entity.update;

import com.skrivet.core.common.entity.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Map;
@ApiModel(description = "表单列修改的参数")
public class ColumnVQ extends BasicEntity {
    @ApiModelProperty(value = "类字段名", example = "test", required = true)
    @NotNull(message = "类字段名不能为空")
    @Length(max = 200, message = "类字段名最长为200")
    private String javaName;
    @ApiModelProperty(value = "字段类型", example = "test", required = true)
    @NotNull(message = "字段类型不能为空")
    @Length(max = 50, message = "字段类型最长为50")
    private String javaType;
    @ApiModelProperty(value = "数据库字段名", example = "test", required = true)
    @NotNull(message = "数据库字段名不能为空")
    @Length(max = 255, message = "数据库字段名最长为255")
    private String dbColumnName;
    //校验信息
    @ApiModelProperty(value = "校验信息")
    private Map<String, Object> validate;
    //扩展信息
    @ApiModelProperty(value = "扩展信息")
    private Map<String, Object> extInfo;
    //是否为主键
    @ApiModelProperty(value = "是否为主键")
    private boolean primaryKey;
    @ApiModelProperty(value = "展示信息")
    private Map<String, Object> display;
    @ApiModelProperty(value = "父级编号")
    private boolean parentKey;
    @ApiModelProperty(value = "字段名")
    @NotNull(message = "字段名不能为空")
    @Length(max = 255, message = "字段名最长为255")
    private String fieldName;
    @ApiModelProperty(value = "控件类型")
    @NotNull(message = "控件类型不能为空")
    @Length(max = 255, message = "控件类型最长为255")
    private String viewType;
    @NotNull(message = "匹配方式不能为空")
    @ApiModelProperty(value = "匹配方式")
    @Length(max = 255, message = "匹配方式最长为255")
    private String matchType;

    public String getJavaName() {
        return javaName;
    }

    public void setJavaName(String javaName) {
        this.javaName = javaName;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getDbColumnName() {
        return dbColumnName;
    }

    public void setDbColumnName(String dbColumnName) {
        this.dbColumnName = dbColumnName;
    }

    public Map<String, Object> getValidate() {
        return validate;
    }

    public void setValidate(Map<String, Object> validate) {
        this.validate = validate;
    }

    public Map<String, Object> getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(Map<String, Object> extInfo) {
        this.extInfo = extInfo;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Map<String, Object> getDisplay() {
        return display;
    }

    public void setDisplay(Map<String, Object> display) {
        this.display = display;
    }

    public boolean isParentKey() {
        return parentKey;
    }

    public void setParentKey(boolean parentKey) {
        this.parentKey = parentKey;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }
}
