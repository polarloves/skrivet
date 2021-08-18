package com.skrivet.supports.code.dao.core.form;

import com.skrivet.supports.code.dao.core.form.entity.select.TableMetaDataDP;

public interface MetaDataDao {
    public TableMetaDataDP findMetaData(String name);

    public boolean addColumn(String tableName,String columnName,int type,String remark,int length);
}
