package com.skrivet.supports.data.service.core.dict.entity.update;

import com.skrivet.core.common.entity.BasicEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class DictUpdateSQ extends BasicEntity {
    @NotNull(message = "编号不能为空")
    private String id;
    @NotNull(message = "内容不能为空")
    @Length(max = 50,message = "内容最长为50")
    private String text;
    @NotNull(message = "值不能为空")
    @Length(max = 50,message = "字典值最长为50")
    private String value;
    @Length(max = 500,message = "字典备注最长为500")
    private String remark;
    @NotNull(message = "排序号不能为空")
    private Long orderNum;
    @NotNull(message = "组编号不能为空")
    @Length(max = 50,message = "组编号最长为50")
    private String groupId;
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
