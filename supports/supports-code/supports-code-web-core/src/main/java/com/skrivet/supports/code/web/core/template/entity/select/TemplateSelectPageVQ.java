package com.skrivet.supports.code.web.core.template.entity.select;


import com.skrivet.web.core.entity.BasicPageVQ;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "查询条件")
public class TemplateSelectPageVQ extends BasicPageVQ {
    @ApiModelProperty(value = "模板名称", example = "test")
    private String templateName;
    @ApiModelProperty(value = "模板类型", example = "1", allowableValues = "1,2")
    private Integer templateType;
    @ApiModelProperty(value = "模板域", example = "main", allowableValues = "main,children")
    private String scope;
    @ApiModelProperty(value = "组编号", example = "test")
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
