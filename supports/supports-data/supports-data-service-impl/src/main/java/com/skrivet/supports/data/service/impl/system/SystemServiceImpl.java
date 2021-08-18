package com.skrivet.supports.data.service.impl.system;

import com.skrivet.core.common.annotations.DynamicDataBase;
import com.skrivet.core.common.annotations.PublishEvent;
import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.entity.PageList;
import com.skrivet.core.common.exception.DataExistExp;
import com.skrivet.plugins.queue.core.Channels;
import com.skrivet.plugins.queue.core.annotations.Publish;
import com.skrivet.plugins.security.core.annotations.RequiresPermissions;
import com.skrivet.plugins.service.core.BasicService;
import com.skrivet.supports.data.dao.core.system.SystemDao;
import com.skrivet.supports.data.dao.core.system.entity.add.SystemAddDQ;
import com.skrivet.supports.data.dao.core.system.entity.select.*;
import com.skrivet.supports.data.dao.core.system.entity.update.SystemUpdateDQ;
import com.skrivet.supports.data.service.core.system.SystemService;
import com.skrivet.supports.data.service.core.system.entity.add.SystemAddSQ;
import com.skrivet.supports.data.service.core.system.entity.select.*;
import com.skrivet.supports.data.service.core.system.entity.update.SystemUpdateSQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service("systemService")
@DynamicDataBase
@CacheConfig(cacheNames = "systemConfigCache")
public class SystemServiceImpl extends BasicService implements SystemService {
    @Autowired
    private SystemDao systemDao;
    @Autowired(required = false)
    private CacheManager cacheManager;

    @Caching(evict = {
            @CacheEvict(cacheNames = "systemConfigGroupCache", key = "#entity.groupId"),
            @CacheEvict(key = "#result")
    })
    @PublishEvent(id = "#result", name = "system", action = "add")
    @Transactional(transactionManager = "dataTM")
    @RequiresPermissions(value = "skrivet:system:add")
    @Override
    public String insert(SystemAddSQ entity, LoginUser loginUser) {
        if (systemDao.selectByGroupKey(entity.getGroupId(), entity.getSysKey()) != null) {
            throw new DataExistExp().variable(entity.getGroupId() + "-" + entity.getSysKey());
        }
        String id = idGenerator.generate();
        SystemAddDQ data = entityConvert.convert(entity, SystemAddDQ.class);
        data.setId(id);
        systemDao.insert(data);
        return id;
    }
    @PublishEvent(id = "#id", name = "system", action = "delete",opportunity = PublishEvent.Opportunity.BEFORE)
    @CacheEvict(key = "#id")
    @RequiresPermissions(value = "skrivet:system:delete")
    @Transactional(transactionManager = "dataTM")
    @Override
    public Long deleteById(String id, LoginUser loginUser) {
        if (cacheManager != null) {
            SystemService current = current(SystemService.class);
            SystemDetailSP systemDetailSP = current.selectOneById(id, LoginUser.IGNORED);
            if (systemDetailSP != null) {
                //删除缓存
                cacheManager.getCache("systemGroupCache").evict(systemDetailSP.getGroupId());
            }
        }
        return systemDao.deleteById(id);
    }

    @RequiresPermissions(value = "skrivet:system:delete")
    @Transactional(transactionManager = "dataTM")
    @Override
    public Long deleteMultiById(String[] ids, LoginUser loginUser) {
        SystemService current = current(SystemService.class);
        long result = 0;
        for (String id : ids) {
            result = result + current.deleteById(id, loginUser);
        }
        return result;
    }


    @Caching(evict = {
            @CacheEvict(cacheNames = "systemConfigGroupCache", key = "#entity.groupId"),
            @CacheEvict(key = "#entity.id")
    })
    @PublishEvent(id = "#entity.id", name = "system", action = "update")
    @RequiresPermissions(value = "skrivet:system:update")
    @Transactional(transactionManager = "dataTM")
    @Override
    public Long update(SystemUpdateSQ entity, LoginUser loginUser) {
        SystemDetailDP detailDP = systemDao.selectByGroupKey(entity.getGroupId(), entity.getSysKey());
        if (detailDP != null && !detailDP.getId().equals(entity.getId())) {
            throw new DataExistExp().variable(entity.getGroupId() + "-" + entity.getSysKey());
        }
        SystemUpdateDQ data = entityConvert.convert(entity, SystemUpdateDQ.class);
        return systemDao.update(data);
    }

    @RequiresPermissions(value = "skrivet:system:detail")
    @Cacheable(key = "#id")
    @Override
    public SystemDetailSP selectOneById(String id, LoginUser loginUser) {
        SystemDetailDP entity = systemDao.selectOneById(id);
        return entityConvert.convert(entity, SystemDetailSP.class);
    }


    @Cacheable(key = "#groupId", cacheNames = "systemConfigGroupCache")
    @Override
    public List<SystemListSP> selectListByGroupId(String groupId, LoginUser loginUser) {
        List<SystemListDP> response = systemDao.selectListByGroupId(groupId);
        return entityConvert.convertList(response, SystemListSP.class);
    }

    @Override
    public SystemDetailSP selectByKeyGroupId(String groupId, String key) {
        SystemService current = current(SystemService.class);
        List<SystemListSP> lists = current.selectListByGroupId(groupId, LoginUser.IGNORED);
        if (!CollectionUtils.isEmpty(lists)) {
            for (SystemListSP list : lists) {
                if (list.getSysKey().equals(key)) {
                    return entityConvert.convert(list, SystemDetailSP.class);
                }
            }
        }
        return null;
    }

    @RequiresPermissions(value = "skrivet:system:list")
    @Override
    public PageList<SystemListSP> selectPageList(SystemSelectPageSQ condition, LoginUser loginUser) {
        PageList<SystemListSP> page = new PageList<SystemListSP>();
        SystemSelectPageDQ request = entityConvert.convert(condition, SystemSelectPageDQ.class);
        page.setCount(systemDao.selectPageCount(request));
        page.setData(entityConvert.convertList(systemDao.selectPageList(request), SystemListSP.class));
        return page;
    }

    @RequiresPermissions(value = "skrivet:system:groups")
    @Override
    public List<SystemGroupSP> groups(LoginUser loginUser) {
        return entityConvert.convertList(systemDao.groups(), SystemGroupSP.class);
    }
}
