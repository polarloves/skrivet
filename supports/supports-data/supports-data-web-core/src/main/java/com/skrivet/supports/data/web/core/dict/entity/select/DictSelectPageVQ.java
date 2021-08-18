package com.skrivet.supports.data.web.core.dict.entity.select;

import com.skrivet.web.core.entity.BasicPageVQ;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "查询分页的参数")
public class DictSelectPageVQ extends BasicPageVQ {
    @ApiModelProperty(value = "字典显示内容", example = "男")
    private String text;
    @ApiModelProperty(value = "字典值", example = "1")
    private String value;
    @ApiModelProperty(value = "字典备注信息", example = "这个字典干XX用的")
    private String remark;
    @ApiModelProperty(value = "字典组编号", example = "CHECKSTATE")
    private String groupId;
    @ApiModelProperty(value = "字典组名", example = "请假审核状态")
    private String groupName;
    @ApiModelProperty(value = "字典排序号", example = "1")
    private Long orderNum;

    public String getText() {
        return text;
    }

    public Long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
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
