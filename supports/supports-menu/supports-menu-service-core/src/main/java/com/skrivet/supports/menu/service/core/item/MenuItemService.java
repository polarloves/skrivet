package com.skrivet.supports.menu.service.core.item;


import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.supports.menu.service.core.item.entity.add.MenuItemAddSQ;
import com.skrivet.supports.menu.service.core.item.entity.select.*;
import com.skrivet.supports.menu.service.core.item.entity.update.MenuItemUpdateSQ;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface MenuItemService {
    public String insert(@Valid MenuItemAddSQ entity, LoginUser loginUser);

    public Long deleteById(@NotNull(message = "编号不能为空") String id, LoginUser loginUser);

    public Long update(@Valid MenuItemUpdateSQ entity, LoginUser loginUser);

    public MenuItemDetailSP selectOneById(@NotNull(message = "编号不能为空") String id, LoginUser loginUser);

    public List<MenuItemListSP> selectList(LoginUser loginUser);

    public List<String> templateItemIds(@NotNull(message = "模板编号不能为空") String templateId, LoginUser loginUser);

    public void updateTemplateItems(@NotNull(message = "模板编号不能为空") String templateId, String[] itemIds, LoginUser loginUser);
}