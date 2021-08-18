package com.skrivet.supports.menu.service.impl.template;

import com.skrivet.core.common.annotations.DynamicDataBase;
import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.entity.PageList;
import com.skrivet.plugins.security.core.annotations.RequiresPermissions;
import com.skrivet.plugins.service.core.BasicService;

import com.skrivet.supports.menu.dao.core.template.MenuTemplateDao;
import com.skrivet.supports.menu.dao.core.template.entity.add.MenuTemplateAddDQ;
import com.skrivet.supports.menu.dao.core.template.entity.select.*;
import com.skrivet.supports.menu.dao.core.template.entity.update.MenuTemplateUpdateDQ;
import com.skrivet.supports.menu.service.core.template.MenuTemplateService;
import com.skrivet.supports.menu.service.core.template.entity.add.MenuTemplateAddSQ;
import com.skrivet.supports.menu.service.core.template.entity.select.*;
import com.skrivet.supports.menu.service.core.template.entity.update.MenuTemplateUpdateSQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service("menuTemplateService")
@DynamicDataBase
public class MenuTemplateServiceImpl extends BasicService implements MenuTemplateService {
    @Autowired
    private MenuTemplateDao menuTemplateDao;

    @Transactional(transactionManager = "menuTM")
    @RequiresPermissions(value = "skrivet:menuTemplate:add")
    public String insert(MenuTemplateAddSQ entity, LoginUser loginUser) {
        String id = idGenerator.generate();
        MenuTemplateAddDQ data = entityConvert.convert(entity, MenuTemplateAddDQ.class);
        data.setId(id);
        data.setDefaultTemplate("0");
        menuTemplateDao.insert(data);
        return data.getId();
    }

    @RequiresPermissions(value = "skrivet:menuTemplate:delete")
    @Transactional(transactionManager = "menuTM")
    public Long deleteById(String id, LoginUser loginUser) {
        return menuTemplateDao.deleteById(id);
    }

    @RequiresPermissions(value = "skrivet:menuTemplate:delete")
    @Transactional(transactionManager = "menuTM")
    public Long deleteMultiById(String[] ids, LoginUser loginUser) {
        MenuTemplateService current = current(MenuTemplateService.class);
        long result = 0;
        for (String id : ids) {
            result = result + current.deleteById(id, loginUser);
        }
        return result;
    }

    @RequiresPermissions(value = "skrivet:menuTemplate:update")
    @Transactional(transactionManager = "menuTM")
    public Long update(MenuTemplateUpdateSQ entity, LoginUser loginUser) {
        MenuTemplateUpdateDQ data = entityConvert.convert(entity, MenuTemplateUpdateDQ.class);
        return menuTemplateDao.update(data);
    }


    @RequiresPermissions(value = "skrivet:menuTemplate:detail")
    public MenuTemplateDetailSP selectOneById(String id, LoginUser loginUser) {
        MenuTemplateDetailDP entity = menuTemplateDao.selectOneById(id);
        return entityConvert.convert(entity, MenuTemplateDetailSP.class);
    }

    @RequiresPermissions(value = "skrivet:menuTemplate:list")
    public PageList<MenuTemplateListSP> selectPageList(MenuTemplateSelectPageSQ condition, LoginUser loginUser) {
        PageList<MenuTemplateListSP> page = new PageList<MenuTemplateListSP>();
        MenuTemplateSelectPageDQ request = entityConvert.convert(condition, MenuTemplateSelectPageDQ.class);
        page.setCount(menuTemplateDao.selectPageCount(request));
        page.setData(entityConvert.convertList(menuTemplateDao.selectPageList(request), MenuTemplateListSP.class));
        return page;
    }

    @CacheEvict(cacheNames = "menuTemplate", key = "'default'")
    @Transactional(transactionManager = "menuTM")
    @Override
    public Long setDefault(String id, LoginUser loginUser) {
        menuTemplateDao.clearDefault();
        return menuTemplateDao.setDefault(id);
    }

    @Cacheable(cacheNames = "menuTemplate", key = "'default'")
    @Override
    public String defaultMenuTemplateId() {
        return menuTemplateDao.defaultMenuTemplateId();
    }

    @RequiresPermissions(value = {"skrivet:user:menu", "skrivet:backstage"})
    @Cacheable(cacheNames = "menuTemplate", key = "#userId")
    @Override
    public String userTemplate(String userId, LoginUser loginUser) {
        return menuTemplateDao.userTemplate(userId);
    }

    @CacheEvict(cacheNames = "menuTemplate", key = "#userId")
    @Transactional(transactionManager = "menuTM")
    @RequiresPermissions(value = {"skrivet:user:menu", "skrivet:backstage"})
    @Override
    public void updateUserTemplate(String userId, String templateId, LoginUser loginUser) {
        menuTemplateDao.deleteUserTemplateByUserId(userId);
        if (!StringUtils.isEmpty(templateId)) {
            menuTemplateDao.insertUserTemplate(idGenerator.generate(), userId, templateId);
        }

    }

    @Override
    public List<MenuTemplateListSP> selectList(LoginUser loginUser) {
        return entityConvert.convertList(menuTemplateDao.selectList(), MenuTemplateListSP.class);
    }
}