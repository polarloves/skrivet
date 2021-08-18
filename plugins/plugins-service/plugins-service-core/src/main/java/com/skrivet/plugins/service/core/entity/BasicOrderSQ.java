package com.skrivet.plugins.service.core.entity;


import com.skrivet.core.common.entity.BasicEntity;

/**
 * 基础实体类
 *
 * @author n
 * @version 1.0
 */
public class BasicOrderSQ extends BasicEntity {

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

