package com.skrivet.supports.code.dao.mybatis.form;

import com.skrivet.plugins.database.core.annotations.Dao;
import com.skrivet.plugins.database.mybatis.annotations.Page;
import com.skrivet.supports.code.dao.core.form.entity.select.ColumnDP;
import com.skrivet.supports.code.dao.core.form.entity.select.FormDetailDP;
import com.skrivet.supports.code.dao.core.form.entity.select.FormListDP;
import com.skrivet.supports.code.dao.core.form.entity.select.FormSelectPageDQ;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Dao
public interface FormDao extends com.skrivet.supports.code.dao.core.form.FormDao {
    @Override
    public Long deleteById(@Param("id") String id);

    @Override
    public Long deleteColumnByFormId(@Param("formId") String formId);

    @Override
    public FormDetailDP selectOneById(@Param("id") String id);

    @Override
    List<FormListDP> children(@Param("parentId") String parentId);

    @Override
    public List<ColumnDP> selectColumnsByFormId(@Param("formId") String formId);

    @Page
    @Override
    List<FormListDP> selectPageList(FormSelectPageDQ condition);
}
