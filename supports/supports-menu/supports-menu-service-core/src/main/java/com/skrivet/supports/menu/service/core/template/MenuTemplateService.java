package com.skrivet.supports.menu.service.core.template;


import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.entity.PageList;
import com.skrivet.supports.menu.service.core.template.entity.add.MenuTemplateAddSQ;
import com.skrivet.supports.menu.service.core.template.entity.select.*;
import com.skrivet.supports.menu.service.core.template.entity.update.MenuTemplateUpdateSQ;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface MenuTemplateService {
    public String insert(@Valid MenuTemplateAddSQ entity, LoginUser loginUser);

    public Long deleteById(@NotNull(message = "主键编号不能为空") String id, LoginUser loginUser);

    public Long deleteMultiById(@NotNull(message = "主键编号不能为空") String[] ids, LoginUser loginUser);

    public Long update(@Valid MenuTemplateUpdateSQ entity, LoginUser loginUser);

    public MenuTemplateDetailSP selectOneById(@NotNull(message = "主键编号不能为空") String id, LoginUser loginUser);

    public PageList<MenuTemplateListSP> selectPageList(MenuTemplateSelectPageSQ condition, LoginUser loginUser);

    public Long setDefault(@NotNull(message = "主键编号不能为空") String id, LoginUser loginUser);

    public String defaultMenuTemplateId();

    public String userTemplate(@NotNull(message = "用户编号不能为空") String userId, LoginUser loginUser);

    public void updateUserTemplate(@NotNull(message = "用户编号不能为空") String userId, String templateId, LoginUser loginUser);

    public List<MenuTemplateListSP> selectList(LoginUser loginUser);

}