package com.skrivet.supports.code.web.core.template.entity.update;

import com.skrivet.core.common.annotations.Within;
import com.skrivet.core.common.entity.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
@ApiModel(description = "模板修改的参数")
public class TemplateUpdateVQ extends BasicEntity {
    @NotNull(message = "数据编号不能为空")
    @ApiModelProperty(value = "数据编号", example = "test", required = true)
    private String id;
    @NotNull(message = "模板名称不能为空")
    @Length(max = 255, message = "模板名称最长为500")
    @ApiModelProperty(value = "模板名称", example = "test", required = true)
    private String templateName;
    @NotNull(message = "模板类型不能为空")
    @ApiModelProperty(value = "模板类型", example = "1", required = true,allowableValues = "1,2")
    private Integer templateType;
    @NotNull(message = "模板域不能为空")
    @ApiModelProperty(value = "模板域", example = "main", allowableValues = "main,children", required = true)
    @Within(value = {"main", "children"}, message = "模板域只能为main或者children")
    private String scope;
    @ApiModelProperty(value = "路径", example = "/#{name}", required = true)
    @NotNull(message = "路径不能为空")
    @Length(max = 2000, message = "路径最长为2000")
    private String path;
    @ApiModelProperty(value = "组编号", example = "test", required = true)
    @NotNull(message = "组编号不能为空")
    @Length(max = 255, message = "组编号最长为255")
    private String groupId;
    @ApiModelProperty(value = "组名", example = "普通模板", required = true)
    @NotNull(message = "组名不能为空")
    @Length(max = 255, message = "组名最长为255")
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
