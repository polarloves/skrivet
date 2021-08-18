package com.skrivet.plugins.database.core.entity;


public class BasicPageDQ extends BasicOrderDQ {
    /**
     * 当前页码
     **/
    private int page = 1;
    /**
     * 每页条目
     **/
    private int rows = 10;



    /**
     * 分页查询时，获取开始下标
     *
     * @return 分页查询开始下标
     */
    public int getPageStartNumber() {
        return (page - 1) * rows;
    }

    /**
     * 获取分页查询时偏移量
     *
     * @return 分页查询时的偏移量
     */
    public int getPageOffsetNumber() {
        return rows;
    }

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

