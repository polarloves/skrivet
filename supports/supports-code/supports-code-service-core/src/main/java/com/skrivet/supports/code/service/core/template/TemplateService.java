package com.skrivet.supports.code.service.core.template;

import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.entity.PageList;
import com.skrivet.supports.code.service.core.template.entity.add.TemplateAddSQ;
import com.skrivet.supports.code.service.core.template.entity.select.TemplateDetailSP;
import com.skrivet.supports.code.service.core.template.entity.select.TemplateGroupSP;
import com.skrivet.supports.code.service.core.template.entity.select.TemplateListSP;
import com.skrivet.supports.code.service.core.template.entity.select.TemplateSelectPageSQ;
import com.skrivet.supports.code.service.core.template.entity.update.TemplateUpdateSQ;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

public interface TemplateService {
    public String insert(@Valid TemplateAddSQ entity, LoginUser loginUser);

    public Long deleteById(@NotNull(message = "编号不能为空") String id, LoginUser loginUser);

    public Long deleteMultiById(@NotNull(message = "编号不能为空") String[] id, LoginUser loginUser);

    public Long update(@Valid TemplateUpdateSQ entity, LoginUser loginUser);

    public Long updateTemplateData(String id, String data);

    public PageList<TemplateListSP> selectPageList(@Valid TemplateSelectPageSQ condition, LoginUser loginUser);

    public List<TemplateListSP> selectByGroupId(@NotNull(message = "编号不能为空") String groupId, LoginUser loginUser);

    public TemplateDetailSP selectOneById(@NotNull(message = "编号不能为空") String id, LoginUser loginUser);

    public List<TemplateGroupSP> groups(LoginUser loginUser);

    public String renderTemplate(Map<String, Object> map, @NotNull(message = "模板数据不能为空") String templateData, LoginUser loginUser);
}
