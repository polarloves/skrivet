package com.skrivet.supports.data.web.core.system.entity.select;

import com.skrivet.web.core.entity.BasicPageVQ;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "查询分页的参数")
public class SystemSelectPageVQ extends BasicPageVQ {
    @ApiModelProperty(value = "显示内容", example = "男")
    private String text;
    @ApiModelProperty(value = "键", example = "1")
    private String sysKey;
    @ApiModelProperty(value = "值", example = "1")
    private String sysValue;
    @ApiModelProperty(value = "备注信息", example = "这个干XX用的")
    private String remark;
    @ApiModelProperty(value = "排序号", example = "1")
    private Long orderNum;
    @ApiModelProperty(value = "组编号", example = "CHECKSTATE")
    private String groupId;
    @ApiModelProperty(value = "组名", example = "请假审核状态")
    private String groupName;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSysKey() {
        return sysKey;
    }

    public void setSysKey(String sysKey) {
        this.sysKey = sysKey;
    }

    public String getSysValue() {
        return sysValue;
    }

    public void setSysValue(String sysValue) {
        this.sysValue = sysValue;
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
