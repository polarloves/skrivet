package com.skrivet.supports.code.service.core.form;

import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.entity.PageList;
import com.skrivet.supports.code.service.core.form.entity.add.FormAddSQ;
import com.skrivet.supports.code.service.core.form.entity.select.*;
import com.skrivet.supports.code.service.core.form.entity.update.ColumnUpdateSQ;
import com.skrivet.supports.code.service.core.form.entity.update.FormUpdateSQ;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

public interface FormService {
    public String insert(@Valid FormAddSQ entity, LoginUser loginUser);

    public Long deleteById(@NotNull(message = "编号不能为空") String id, LoginUser loginUser);

    public Long deleteMultiById(@NotNull(message = "编号不能为空") String[] id, LoginUser loginUser);

    public Long update(@Valid FormUpdateSQ entity, LoginUser loginUser);

    public Long updateColumn(@Valid ColumnUpdateSQ entity, LoginUser loginUser);

    public PageList<FormListSP> selectPageList(@Valid FormSelectPageSQ condition, LoginUser loginUser);

    public FormDetailSP selectOneById(@NotNull(message = "编号不能为空") String id, LoginUser loginUser);

    public List<FormDictSP> selectFormDict(LoginUser loginUser);

    public List<FormListSP> selectList(LoginUser loginUser);

    public List<ColumnSP> selectColumns(@NotNull(message = "编号不能为空") String id, LoginUser loginUser);

    public Map<String, Object> obtainFormData(@NotNull(message = "编号不能为空") String id, LoginUser loginUser);

    public boolean importTable(@NotNull(message = "表名不能为空") String name, LoginUser loginUser);

    public boolean initTable(String id);

}
