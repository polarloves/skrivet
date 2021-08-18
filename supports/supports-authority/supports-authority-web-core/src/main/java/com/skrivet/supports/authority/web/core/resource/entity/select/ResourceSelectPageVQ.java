package com.skrivet.supports.authority.web.core.resource.entity.select;


import com.skrivet.core.common.annotations.Within;
import com.skrivet.web.core.entity.BasicPageVQ;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(description = "资源分页查询参数")
public class ResourceSelectPageVQ extends BasicPageVQ {
    @ApiModelProperty(value = "资源名称", example = "测试接口")
    private String text;
    @ApiModelProperty(value = "资源路径", example = "/test")
    private String value;
    @ApiModelProperty(value = "备注", example = "后台管理资源")
    private String remark;
    @ApiModelProperty(value = "类型,1:接口,2:页面", example = "1")
    @Within(value = {"1","2"}, message = "类型只能是1或者2")
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
