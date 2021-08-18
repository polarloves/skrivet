package com.skrivet.supports.data.service.impl.dict;

import com.skrivet.core.common.annotations.DynamicDataBase;
import com.skrivet.core.common.annotations.PublishEvent;
import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.entity.PageList;
import com.skrivet.plugins.queue.core.Channels;
import com.skrivet.plugins.queue.core.annotations.Publish;
import com.skrivet.plugins.security.core.annotations.RequiresPermissions;
import com.skrivet.plugins.service.core.BasicService;

import com.skrivet.supports.data.dao.core.dict.DictDao;
import com.skrivet.supports.data.dao.core.dict.entity.add.DictAddDQ;
import com.skrivet.supports.data.dao.core.dict.entity.select.*;
import com.skrivet.supports.data.dao.core.dict.entity.update.DictUpdateDQ;
import com.skrivet.supports.data.service.core.dict.DictService;
import com.skrivet.supports.data.service.core.dict.entity.add.DictAddSQ;
import com.skrivet.supports.data.service.core.dict.entity.select.*;
import com.skrivet.supports.data.service.core.dict.entity.update.DictUpdateSQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("dictService")
@DynamicDataBase
@CacheConfig(cacheNames = "dictCache")
public class DictServiceImpl extends BasicService implements DictService {
    @Autowired
    private DictDao dictDao;
    @Autowired(required = false)
    private CacheManager cacheManager;

    @Caching(evict = {
            @CacheEvict(cacheNames = "dictGroupCache", key = "#entity.groupId"),
            @CacheEvict(key = "#result")
    })
    @PublishEvent(id = "#result", name = "dict", action = "add")
    @Transactional(transactionManager = "dataTM")
    @RequiresPermissions(value = "skrivet:dict:add")
    @Override
    public String insert(DictAddSQ entity, LoginUser loginUser) {
        String id = idGenerator.generate();
        DictAddDQ data = entityConvert.convert(entity, DictAddDQ.class);
        data.setId(id);
        dictDao.insert(data);
        return id;
    }

    @PublishEvent(id = "#id", name = "dict", action = "delete", opportunity = PublishEvent.Opportunity.BEFORE)
    @CacheEvict(key = "#id")
    @RequiresPermissions(value = "skrivet:dict:delete")
    @Transactional(transactionManager = "dataTM")
    @Override
    public Long deleteById(String id, LoginUser loginUser) {
        if (cacheManager != null) {
            DictService current = current(DictService.class);
            DictDetailSP dictDetailSP = current.selectOneById(id, LoginUser.IGNORED);
            if (dictDetailSP != null) {
                //删除缓存
                cacheManager.getCache("dictGroupCache").evict(dictDetailSP.getGroupId());
            }
        }
        return dictDao.deleteById(id);
    }

    @RequiresPermissions(value = "skrivet:dict:delete")
    @Transactional(transactionManager = "dataTM")
    @Override
    public Long deleteMultiById(String[] ids, LoginUser loginUser) {
        DictService current = current(DictService.class);
        long result = 0;
        for (String id : ids) {
            result = result + current.deleteById(id, loginUser);
        }
        return result;
    }


    @Caching(evict = {
            @CacheEvict(cacheNames = "dictGroupCache", key = "#entity.groupId"),
            @CacheEvict(key = "#entity.id")
    })
    @PublishEvent(id = "#entity.id", name = "dict", action = "update")
    @RequiresPermissions(value = "skrivet:dict:update")
    @Transactional(transactionManager = "dataTM")
    @Override
    public Long update(DictUpdateSQ entity, LoginUser loginUser) {
        DictUpdateDQ data = entityConvert.convert(entity, DictUpdateDQ.class);
        return dictDao.update(data);
    }


    @RequiresPermissions(value = "skrivet:dict:detail")
    @Cacheable(key = "#id")
    @Override
    public DictDetailSP selectOneById(String id, LoginUser loginUser) {
        DictDetailDP entity = dictDao.selectOneById(id);
        return entityConvert.convert(entity, DictDetailSP.class);
    }


    @Cacheable(key = "#groupId", cacheNames = "dictGroupCache")
    @Override
    public List<DictListSP> selectListByGroupId(String groupId, LoginUser loginUser) {
        List<DictListDP> response = dictDao.selectListByGroupId(groupId);
        return entityConvert.convertList(response, DictListSP.class);
    }

    @RequiresPermissions(value = "skrivet:dict:list")
    @Override
    public PageList<DictListSP> selectPageList(DictSelectPageSQ condition, LoginUser loginUser) {
        PageList<DictListSP> page = new PageList<DictListSP>();
        DictSelectPageDQ request = entityConvert.convert(condition, DictSelectPageDQ.class);
        page.setCount(dictDao.selectPageCount(request));
        page.setData(entityConvert.convertList(dictDao.selectPageList(request), DictListSP.class));
        return page;
    }

    @RequiresPermissions(value = "skrivet:dict:groups")
    @Override
    public List<DictGroupSP> groups(LoginUser loginUser) {
        return entityConvert.convertList(dictDao.groups(), DictGroupSP.class);
    }
}
