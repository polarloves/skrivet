package com.skrivet.supports.data.web.core.tree.entity.select;

import com.skrivet.web.core.entity.BasicPageVQ;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "查询分页的参数")
public class TreeSelectPageVQ extends BasicPageVQ {
    @ApiModelProperty(value = "显示内容", example = "男")
    private String text;
    @ApiModelProperty(value = "值", example = "1")
    private String value;
    @ApiModelProperty(value = "备注信息", example = "这个树干XX用的")
    private String remark;
    @ApiModelProperty(value = "排序号", example = "1")
    private Long orderNum;
    @ApiModelProperty(value = "组编号", example = "CHECKSTATE")
    private String groupId;
    @ApiModelProperty(value = "组名", example = "请假审核状态")
    private String groupName;
    @ApiModelProperty(value = "父编号", example = "1")
    private String parentId;
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

    public Long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
