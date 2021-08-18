package com.skrivet.supports.code.web.core.form.entity.update;

import com.skrivet.core.common.entity.BasicEntity;
import io.swagger.annotations.ApiModel;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
@ApiModel(description = "表单列修改的参数")
public class ColumnUpdateVQ extends BasicEntity {
    @NotNull(message = "表单编号不能为空")
    private String formId;
    @Valid
    private List<ColumnVQ> columns;

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public List<ColumnVQ> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnVQ> columns) {
        this.columns = columns;
    }
}
