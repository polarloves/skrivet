package com.skrivet.web.core.entity;

import com.skrivet.core.common.entity.BasicEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * 基础实体类
 *
 * @author n
 * @version 1.0
 */
public class BasicOrderVQ extends BasicEntity{

    /**
     * 排序字段名称
     **/
    @ApiModelProperty(value = "排序字段名称",example = "id")
    private String sort;
    /**
     * 排序方式
     **/
    @ApiModelProperty(value = "排序方式",example = "ASC",allowableValues = "ASC,DESC")
    private String order = "ASC";


    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        if (null != order && order.equalsIgnoreCase("DESC")) {
            this.order = "DESC";
            return;
        }
        this.order = "ASC";
    }

}

