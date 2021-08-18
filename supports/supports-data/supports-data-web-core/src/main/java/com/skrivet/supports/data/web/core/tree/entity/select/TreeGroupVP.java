package com.skrivet.supports.data.web.core.tree.entity.select;

import com.skrivet.core.common.entity.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "树分组信息")
public class TreeGroupVP extends BasicEntity {
    @ApiModelProperty(value = "组名",example = "请假审核状态")
    private String text;
    @ApiModelProperty(value = "组编号",example = "CHECKSTATE")
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
