package com.skrivet.supports.menu.web.core.entity.template.add;

import com.skrivet.core.common.entity.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@ApiModel(description = "菜单模板添加的参数")
public class MenuTemplateAddVQ extends BasicEntity {
    @NotNull(message = "模板名称不能为空")
    @Length(max = 255, min = 0, message = "模板名称长度为0-255")
    @ApiModelProperty(value = "模板名称", required = true)
    //模板名称
    private String templateName;
    @Length(max = 255, min = 0, message = "备注长度为0-255")
    @ApiModelProperty(value = "备注")
    //备注
    private String templateRemark;

    public String getTemplateName() {
        return this.templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }


    public String getTemplateRemark() {
        return this.templateRemark;
    }

    public void setTemplateRemark(String templateRemark) {
        this.templateRemark = templateRemark;
    }
}