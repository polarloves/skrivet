package com.skrivet.supports.authority.web.core.resource.entity.update;

import com.skrivet.core.common.annotations.Within;
import com.skrivet.core.common.entity.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@ApiModel(description = "资源更新")
public class ResourceUpdateVQ extends BasicEntity {
    @ApiModelProperty(value = "资源编号", example = "1", required = true)
    @NotNull(message = "资源编号不能为空")
    private String id;
    @ApiModelProperty(value = "资源名称", example = "测试接口", required = true)
    @NotNull(message = "资源名称不能为空")
    @Length(max = 50, message = "资源名称最长为50")
    private String text;
    @ApiModelProperty(value = "资源路径", example = "/test", required = true)
    @Length(max = 200, message = "资源路径最长为200")
    @NotNull(message = "资源路径不能为空")
    private String value;
    @ApiModelProperty(value = "备注", example = "后台管理资源")
    @Length(max = 2000, message = "备注最长为2000")
    private String remark;
    @ApiModelProperty(value = "排序号", example = "1", required = true)
    @NotNull(message = "排序号不能为空")
    private Long orderNum;
    @ApiModelProperty(value = "类型,1:接口,2:页面", example = "1")
    @NotNull(message = "类型不能为空")
    @Within(value = {"1", "2"}, message = "类型只能是1或者2")
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
