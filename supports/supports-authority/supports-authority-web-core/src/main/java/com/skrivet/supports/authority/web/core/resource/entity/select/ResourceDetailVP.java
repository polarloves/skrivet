package com.skrivet.supports.authority.web.core.resource.entity.select;

import com.skrivet.core.common.entity.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "资源详情")
public class ResourceDetailVP extends BasicEntity {
    @ApiModelProperty(value = "资源编号", example = "1")
    private String id;
    @ApiModelProperty(value = "资源名称", example = "测试接口")
    private String text;
    @ApiModelProperty(value = "资源路径", example = "/test")
    private String value;
    @ApiModelProperty(value = "备注", example = "后台管理资源")
    private String remark;
    @ApiModelProperty(value = "排序号", example = "1")
    private Long orderNum;

    @ApiModelProperty(value = "类型,1:接口,2:页面", example = "1")
    private Integer type;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
