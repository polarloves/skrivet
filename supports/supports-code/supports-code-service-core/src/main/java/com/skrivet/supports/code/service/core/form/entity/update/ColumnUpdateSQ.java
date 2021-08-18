package com.skrivet.supports.code.service.core.form.entity.update;

import com.skrivet.core.common.entity.BasicEntity;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public class ColumnUpdateSQ extends BasicEntity {
    @NotNull(message = "表单编号不能为空")
    private String formId;
    @Valid
    private List<ColumnSQ> columns;

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public List<ColumnSQ> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnSQ> columns) {
        this.columns = columns;
    }
}
