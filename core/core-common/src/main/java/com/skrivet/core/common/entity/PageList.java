package com.skrivet.core.common.entity;

import java.util.List;

public class PageList<T> extends BasicEntity {
    private Long count;
    private List<T> data;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
