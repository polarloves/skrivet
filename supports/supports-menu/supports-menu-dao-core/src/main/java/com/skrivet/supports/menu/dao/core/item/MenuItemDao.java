package com.skrivet.supports.menu.dao.core.item;

import com.skrivet.supports.menu.dao.core.item.entity.add.MenuItemAddDQ;
import com.skrivet.supports.menu.dao.core.item.entity.add.TemplateItemAddDQ;
import com.skrivet.supports.menu.dao.core.item.entity.select.*;
import com.skrivet.supports.menu.dao.core.item.entity.update.MenuItemUpdateDQ;

import java.util.List;

public interface MenuItemDao {
    public void insert(MenuItemAddDQ entity);

    public Long deleteById(String id);

    public Long update(MenuItemUpdateDQ entity);

    public MenuItemDetailDP selectOneById(String id);

    public List<MenuItemListDP> selectList();

    public Long selectCountByParentKey(String parentKey);

    public List<String> templateItemIds(String templateId);

    public Long deleteTemplateItemByTemplateId(String templateId);

    public void insertTemplateItems(TemplateItemAddDQ condition);
}