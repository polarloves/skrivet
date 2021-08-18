package com.skrivet.supports.data.web.core.dict.entity.update;

import com.skrivet.core.common.entity.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@ApiModel(description = "更新字典的数据")
public class DictUpdateVQ extends BasicEntity {
    @NotNull(message = "数据编号不能为空")
    @ApiModelProperty(value = "数据编号", example = "1")
    private String id;
    @NotNull(message = "内容不能为空")
    @ApiModelProperty(value = "字典显示内容", example = "男")
    @Length(max = 50,message = "内容最长为50")
    private String text;
    @ApiModelProperty(value = "字典值", example = "1")
    @NotNull(message = "值不能为空")
    @Length(max = 50,message = "字典值最长为50")
    private String value;
    @ApiModelProperty(value = "字典备注信息", example = "这个字典干XX用的")
    @Length(max = 500,message = "字典备注最长为500")
    private String remark;
    @ApiModelProperty(value = "字典排序号", example = "1")
    @NotNull(message = "排序号不能为空")
    private Long orderNum;
    @ApiModelProperty(value = "字典组编号", example = "CHECKSTATE")
    @NotNull(message = "组编号不能为空")
    @Length(max = 50,message = "组编号最长为50")
    private String groupId;
    @ApiModelProperty(value = "字典组名", example = "请假审核状态")
    @NotNull(message = "组名不能为空")
    @Length(max = 50,message = "组名最长为50")
    private String groupName;

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
