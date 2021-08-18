package com.skrivet.supports.menu.dao.core.template;

import com.skrivet.supports.menu.dao.core.template.entity.add.MenuTemplateAddDQ;
import com.skrivet.supports.menu.dao.core.template.entity.select.*;
import com.skrivet.supports.menu.dao.core.template.entity.update.MenuTemplateUpdateDQ;

import java.util.List;

public interface MenuTemplateDao {
    public void insert(MenuTemplateAddDQ entity);

    public Long deleteById(String id);

    public Long update(MenuTemplateUpdateDQ entity);

    public MenuTemplateDetailDP selectOneById(String id);

    public List<MenuTemplateListDP> selectPageList(MenuTemplateSelectPageDQ condition);

    public Long selectPageCount(MenuTemplateSelectPageDQ condition);

    public Long clearDefault();

    public Long setDefault(String id);

    public String defaultMenuTemplateId();

    public String userTemplate(String userId);

    public Long deleteUserTemplateByUserId(String userId);

    public void insertUserTemplate(String id, String userId, String templateId);


    public List<MenuTemplateListDP> selectList();
}