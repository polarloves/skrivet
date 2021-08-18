package com.skrivet.supports.menu.dao.mybatis.item;

import com.skrivet.plugins.database.core.annotations.Dao;
import com.skrivet.supports.menu.dao.core.item.entity.select.MenuItemDetailDP;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Dao
public interface MenuItemDao extends com.skrivet.supports.menu.dao.core.item.MenuItemDao {
    public Long deleteById(@Param("id") String id);

    public MenuItemDetailDP selectOneById(@Param("id") String id);

    public Long selectCountByParentKey(@Param("parentId") String parentKey);

    public List<String> templateItemIds(@Param("templateId") String templateId);

    public Long deleteTemplateItemByTemplateId(@Param("templateId") String templateId);
}