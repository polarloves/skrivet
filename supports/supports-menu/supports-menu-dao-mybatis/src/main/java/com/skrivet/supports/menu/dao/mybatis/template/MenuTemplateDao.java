package com.skrivet.supports.menu.dao.mybatis.template;

import com.skrivet.plugins.database.core.annotations.Dao;
import com.skrivet.plugins.database.mybatis.annotations.Page;
import com.skrivet.supports.menu.dao.core.template.entity.select.MenuTemplateDetailDP;
import com.skrivet.supports.menu.dao.core.template.entity.select.MenuTemplateListDP;
import com.skrivet.supports.menu.dao.core.template.entity.select.MenuTemplateSelectPageDQ;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Dao
public interface MenuTemplateDao extends com.skrivet.supports.menu.dao.core.template.MenuTemplateDao {
    public Long deleteById(@Param("id") String id);

    public MenuTemplateDetailDP selectOneById(@Param("id") String id);

    @Page
    public List<MenuTemplateListDP> selectPageList(MenuTemplateSelectPageDQ condition);

    public Long setDefault(@Param("id") String id);

    public String defaultMenuTemplateId();

    public String userTemplate(@Param("userId") String userId);

    public Long deleteUserTemplateByUserId(@Param("userId") String userId);

    public void insertUserTemplate(@Param("id") String id, @Param("userId") String userId, @Param("templateId") String templateId);
}