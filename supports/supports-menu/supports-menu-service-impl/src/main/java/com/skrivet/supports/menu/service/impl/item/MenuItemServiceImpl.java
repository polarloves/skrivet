package com.skrivet.supports.menu.service.impl.item;

import com.skrivet.core.common.annotations.DynamicDataBase;
import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.plugins.security.core.annotations.RequiresPermissions;
import com.skrivet.plugins.service.core.BasicService;
import com.skrivet.core.common.exception.ValidationExp;

import com.skrivet.supports.menu.dao.core.item.MenuItemDao;
import com.skrivet.supports.menu.dao.core.item.entity.add.MenuItemAddDQ;
import com.skrivet.supports.menu.dao.core.item.entity.add.TemplateItemAddDQ;
import com.skrivet.supports.menu.dao.core.item.entity.select.*;
import com.skrivet.supports.menu.dao.core.item.entity.update.MenuItemUpdateDQ;
import com.skrivet.supports.menu.service.core.item.MenuItemService;
import com.skrivet.supports.menu.service.core.item.entity.add.MenuItemAddSQ;
import com.skrivet.supports.menu.service.core.item.entity.select.*;
import com.skrivet.supports.menu.service.core.item.entity.update.MenuItemUpdateSQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service("menuItemService")
@DynamicDataBase
@CacheConfig(cacheNames = "menuItem")
public class MenuItemServiceImpl extends BasicService implements MenuItemService {
    @Autowired
    private MenuItemDao menuItemDao;

    @Caching(
            evict = {
                    @CacheEvict(key = "'allList'"),
                    @CacheEvict(key = "#result")
            }
    )
    @Transactional(transactionManager = "menuTM")
    @RequiresPermissions(value = "skrivet:menuItem:add")
    public String insert(MenuItemAddSQ entity, LoginUser loginUser) {
        String id = idGenerator.generate();
        MenuItemAddDQ data = entityConvert.convert(entity, MenuItemAddDQ.class);
        data.setId(id);
        menuItemDao.insert(data);
        return data.getId();
    }

    @Caching(
            evict = {
                    @CacheEvict(key = "'allList'"),
                    @CacheEvict(key = "#id")
            }
    )
    @RequiresPermissions(value = "skrivet:menuItem:delete")
    @Transactional(transactionManager = "menuTM")
    public Long deleteById(String id, LoginUser loginUser) {
        if (menuItemDao.selectCountByParentKey(id) > 0) {
            throw new ValidationExp("含有子节点，无法删除!");
        }
        return menuItemDao.deleteById(id);
    }


    @Caching(
            evict = {
                    @CacheEvict(key = "'allList'"),
                    @CacheEvict(key = "#entity.id")
            }
    )
    @RequiresPermissions(value = "skrivet:menuItem:update")
    @Transactional(transactionManager = "menuTM")
    public Long update(MenuItemUpdateSQ entity, LoginUser loginUser) {
        MenuItemUpdateDQ data = entityConvert.convert(entity, MenuItemUpdateDQ.class);
        return menuItemDao.update(data);
    }


    @RequiresPermissions(value = "skrivet:menuItem:detail")
    @Cacheable(key = "#id")
    public MenuItemDetailSP selectOneById(String id, LoginUser loginUser) {
        MenuItemDetailDP entity = menuItemDao.selectOneById(id);
        return entityConvert.convert(entity, MenuItemDetailSP.class);
    }

    @Cacheable(key = "'allList'")
    @RequiresPermissions(value = "skrivet:menuItem:list")
    public List<MenuItemListSP> selectList(LoginUser loginUser) {
        return entityConvert.convertList(menuItemDao.selectList(), MenuItemListSP.class);
    }

    @RequiresPermissions(value = "skrivet:menuItem:template:list")
    @Cacheable(cacheNames = "templateItems", key = "#templateId")
    @Override
    public List<String> templateItemIds(String templateId, LoginUser loginUser) {
        return menuItemDao.templateItemIds(templateId);
    }

    @RequiresPermissions(value = "skrivet:menuItem:template:update")
    @Transactional(transactionManager = "menuTM")
    @CacheEvict(cacheNames = "templateItems", key = "#templateId")
    @Override
    public void updateTemplateItems(String templateId, String[] itemIds, LoginUser loginUser) {
        menuItemDao.deleteTemplateItemByTemplateId(templateId);
        if (itemIds != null && itemIds.length > 0) {
            TemplateItemAddDQ entity = new TemplateItemAddDQ();
            entity.setTemplateId(templateId);
            List<TemplateItemAddDQ.TemplateItem> items = new ArrayList<>(itemIds.length);
            for (String itemId : itemIds) {
                items.add(new TemplateItemAddDQ.TemplateItem(idGenerator.generate(), itemId));
            }
            entity.setItems(items);
            menuItemDao.insertTemplateItems(entity);
        }
    }
}