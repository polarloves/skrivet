package com.skrivet.supports.code.web.core.template.entity.select;

import com.skrivet.core.common.entity.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(description = "模板列表")
public class TemplateListVP extends BasicEntity {
    @ApiModelProperty(value = "模板编号", example = "test")
    private String id;
    @ApiModelProperty(value = "模板名称", example = "test")
    private String templateName;
    @ApiModelProperty(value = "模板类型", example = "1")
    private Integer templateType;
    @ApiModelProperty(value = "模板域", example = "main")
    private String scope;
    @ApiModelProperty(value = "路径", example = "/#{name}")
    private String path;
    @ApiModelProperty(value = "组编号", example = "test")
    private String groupId;
    @ApiModelProperty(value = "组名", example = "普通模板")
    private String groupName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
