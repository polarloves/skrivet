package com.skrivet.supports.code.dao.core.form;

import com.skrivet.supports.code.dao.core.form.entity.add.ColumnAddDQ;
import com.skrivet.supports.code.dao.core.form.entity.add.FormAddDQ;
import com.skrivet.supports.code.dao.core.form.entity.select.*;
import com.skrivet.supports.code.dao.core.form.entity.update.FormUpdateDQ;

import java.util.List;

public interface FormDao {
    public void insert(FormAddDQ entity);

    public void insertColumn(ColumnAddDQ entity);

    public Long deleteById(String id);

    public Long deleteColumnByFormId(String formId);

    public Long update(FormUpdateDQ entity);

    public FormDetailDP selectOneById(String id);

    public List<FormListDP> selectPageList(FormSelectPageDQ condition);

    public List<FormListDP> selectList();

    public List<FormListDP> children(String parentId);

    public List<ColumnDP> selectColumnsByFormId(String formId);

    public List<FormDictDP> selectFormDict();

    public Long selectPageCount(FormSelectPageDQ condition);


}
