package com.skrivet.supports.code.dao.core.form.entity.select;

import com.skrivet.core.common.entity.BasicEntity;

import java.util.List;

public class TableMetaDataDP extends BasicEntity {
    private String name;
    private String moduleName;
    private String comment;
    private List<TableColumnMetaDataDP> columns;
    public String getName() {
        return name;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<TableColumnMetaDataDP> getColumns() {
        return columns;
    }

    public void setColumns(List<TableColumnMetaDataDP> columns) {
        this.columns = columns;
    }
}
