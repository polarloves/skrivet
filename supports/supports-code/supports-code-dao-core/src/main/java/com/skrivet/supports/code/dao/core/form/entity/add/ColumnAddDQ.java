package com.skrivet.supports.code.dao.core.form.entity.add;

import com.skrivet.core.common.entity.BasicEntity;

import java.util.Map;

public class ColumnAddDQ extends BasicEntity {
    private String id;
    private String formId;
    private String javaName;
    private String javaType;
    private String dbColumnName;
    //校验信息
    private Map<String, Object> validate;
    //扩展信息
    private Map<String, Object> extInfo;
    //是否为主键
    private boolean primaryKey;
    private Map<String, Object> display;
    private Integer orderNum;
    private boolean parentKey;
    private String fieldName;
    private String viewType;
    private String matchType;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

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

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public boolean isParentKey() {
        return parentKey;
    }

    public void setParentKey(boolean parentKey) {
        this.parentKey = parentKey;
    }
}
