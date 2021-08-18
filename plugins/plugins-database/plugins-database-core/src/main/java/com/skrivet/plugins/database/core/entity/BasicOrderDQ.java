package com.skrivet.plugins.database.core.entity;

import com.skrivet.core.common.entity.BasicEntity;

public class BasicOrderDQ extends BasicEntity {
    /**
     * 排序字段名称
     **/
    private String sort;
    /**
     * 排序方式
     **/
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
