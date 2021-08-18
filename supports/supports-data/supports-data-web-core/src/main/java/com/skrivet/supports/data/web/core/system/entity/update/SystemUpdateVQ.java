package com.skrivet.supports.data.web.core.system.entity.update;

import com.skrivet.core.common.entity.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@ApiModel(description = "更新系统配置的数据")
public class SystemUpdateVQ extends BasicEntity {
    @NotNull(message = "数据编号不能为空")
    @ApiModelProperty(value = "数据编号", example = "1")
    private String id;
    @NotNull(message = "内容不能为空")
    @ApiModelProperty(value = "显示内容", example = "男")
    @Length(max = 50,message = "内容最长为50")
    private String text;
    @ApiModelProperty(value = "键", example = "1")
    @NotNull(message = "键不能为空")
    @Length(max = 50,message = "值最长为50")
    private String sysKey;
    @ApiModelProperty(value = "值", example = "1")
    @NotNull(message = "值不能为空")
    @Length(max = 50,message = "值最长为50")
    private String sysValue;
    @ApiModelProperty(value = "备注信息", example = "这个字典干XX用的")
    @Length(max = 500,message = "备注最长为500")
    private String remark;
    @ApiModelProperty(value = "排序号", example = "1")
    @NotNull(message = "排序号不能为空")
    private Long orderNum;
    @ApiModelProperty(value = "组编号", example = "CHECKSTATE")
    @NotNull(message = "组编号不能为空")
    @Length(max = 50,message = "组编号最长为50")
    private String groupId;
    @ApiModelProperty(value = "组名", example = "请假审核状态")
    @Length(max = 50,message = "组名最长为50")
    @NotNull(message = "组名不能为空")
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
