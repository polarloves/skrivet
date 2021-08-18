package com.skrivet.plugins.service.core.entity;



import com.skrivet.core.common.entity.BasicEntity;

import javax.validation.constraints.NotNull;

/**
 * 基础实体类
 *
 * @author n
 * @version 1.0
 */
public class BasicPageSQ extends BasicOrderSQ {
    /**
     * 当前页码
     **/
    @NotNull(message = "当前页码不能为空")
    private int page = 1;
    /**
     * 每页条目
     **/
    @NotNull(message = "每页条目数不能为空")
    private int rows = 10;


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }


}

