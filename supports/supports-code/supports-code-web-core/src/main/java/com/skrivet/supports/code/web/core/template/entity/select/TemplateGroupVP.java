package com.skrivet.supports.code.web.core.template.entity.select;

import com.skrivet.core.common.entity.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "模板分组")
public class TemplateGroupVP extends BasicEntity {
    @ApiModelProperty(value = "组名", example = "普通模板")
    private String text;
    @ApiModelProperty(value = "模板编号", example = "test")
    private String value;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
