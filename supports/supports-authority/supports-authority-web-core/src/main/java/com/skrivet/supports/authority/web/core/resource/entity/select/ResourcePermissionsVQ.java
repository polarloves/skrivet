package com.skrivet.supports.authority.web.core.resource.entity.select;

import com.skrivet.core.common.entity.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "资源权限列表")
public class ResourcePermissionsVQ extends BasicEntity {
    @ApiModelProperty(value = "资源路径", example = "/test")
    private String value;
    @ApiModelProperty(value = "类型,1:接口,2:页面", example = "1")
    private Integer type;
    @ApiModelProperty(value = "权限列表")
    private List<String> permissions;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
