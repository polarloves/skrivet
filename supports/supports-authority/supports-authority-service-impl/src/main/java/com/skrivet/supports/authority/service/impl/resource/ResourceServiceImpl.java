package com.skrivet.supports.authority.service.impl.resource;

import com.skrivet.core.common.annotations.DynamicDataBase;
import com.skrivet.core.common.annotations.PublishEvent;
import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.entity.PageList;
import com.skrivet.core.common.exception.DataExistExp;
import com.skrivet.plugins.security.core.annotations.RequiresPermissions;
import com.skrivet.plugins.service.core.BasicService;
import com.skrivet.supports.authority.dao.core.resource.ResourceDao;
import com.skrivet.supports.authority.dao.core.resource.entity.add.ResourceAddDQ;
import com.skrivet.supports.authority.dao.core.resource.entity.select.ResourceDetailDP;
import com.skrivet.supports.authority.dao.core.resource.entity.select.ResourceSelectPageDQ;
import com.skrivet.supports.authority.dao.core.resource.entity.update.ResourceUpdateDQ;
import com.skrivet.supports.authority.service.core.resource.ResourceService;
import com.skrivet.supports.authority.service.core.resource.entity.add.ResourceAddSQ;
import com.skrivet.supports.authority.service.core.resource.entity.select.ResourceDetailSP;
import com.skrivet.supports.authority.service.core.resource.entity.select.ResourceListSP;
import com.skrivet.supports.authority.service.core.resource.entity.select.ResourceSelectPageSQ;
import com.skrivet.supports.authority.service.core.resource.entity.update.ResourceUpdateSQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service("resourceService")
@DynamicDataBase
@CacheConfig(cacheNames = "resourceCache")
public class ResourceServiceImpl extends BasicService implements ResourceService {
    @Autowired
    private ResourceDao resourceDao;


    @Caching(
            evict = {
                    @CacheEvict(key = "'allList'"),
                    @CacheEvict(cacheNames = "resourcePermission", key = "#result"),
                    @CacheEvict(key = "#result")
            }
    )
    @PublishEvent(id = "#result", name = "resource", action = "add")
    @Transactional(transactionManager = "authorityTM")
    @RequiresPermissions(value = "skrivet:resource:add")
    @Override
    public String insert(ResourceAddSQ entity, LoginUser loginUser) {
        if (resourceDao.selectOneByValue(entity.getValue()) != null) {
            throw new DataExistExp().variable(entity.getValue());
        }
        ResourceAddDQ data = entityConvert.convert(entity, ResourceAddDQ.class);
        data.setId(idGenerator.generate());
        resourceDao.insert(data);
        return data.getId();
    }

    @Caching(
            evict = {
                    @CacheEvict(key = "'allList'"),
                    @CacheEvict(cacheNames = "resourcePermission", key = "#id"),
                    @CacheEvict(key = "#id")
            }
    )
    @PublishEvent(id = "#id", name = "resource", action = "delete",opportunity = PublishEvent.Opportunity.BEFORE)
    @RequiresPermissions(value = "skrivet:resource:delete")
    @Transactional(transactionManager = "authorityTM")
    @Override
    public Long deleteById(String id, LoginUser loginUser) {
        return resourceDao.deleteById(id);
    }

    @RequiresPermissions(value = "skrivet:resource:delete")
    @Transactional(transactionManager = "authorityTM")
    @Override
    public Long deleteMultiById(String[] ids, LoginUser loginUser) {
        ResourceService current = current(ResourceService.class);
        long result = 0;
        for (String id : ids) {
            result = result + current.deleteById(id, loginUser);
        }
        return result;
    }

    @Caching(
            evict = {
                    @CacheEvict(key = "'allList'"),
                    @CacheEvict(key = "#entity.id")
            }
    )
    @PublishEvent(id = "#entity.id", name = "resource", action = "update")
    @RequiresPermissions(value = "skrivet:resource:update")
    @Transactional(transactionManager = "authorityTM")
    @Override
    public Long update(ResourceUpdateSQ entity, LoginUser loginUser) {
        ResourceDetailDP old = resourceDao.selectOneByValue(entity.getValue());
        if (old != null && !old.getId().equals(entity.getId())) {
            throw new DataExistExp().variable(entity.getValue());
        }
        return resourceDao.update(entityConvert.convert(entity, ResourceUpdateDQ.class));
    }

    @Cacheable(key = "#id")
    @RequiresPermissions(value = "skrivet:resource:detail")
    @Override
    public ResourceDetailSP selectOneById(String id, LoginUser loginUser) {
        return entityConvert.convert(resourceDao.selectOneById(id), ResourceDetailSP.class);
    }

    @Cacheable(key = "'allList'")
    @Override
    public List<ResourceListSP> selectList(LoginUser loginUser) {
        List<ResourceListSP> result = entityConvert.convertList(resourceDao.selectList(), ResourceListSP.class);
        if (!CollectionUtils.isEmpty(result)) {
            com.skrivet.core.toolkit.CollectionUtils.sort(result, true, data -> new long[]{data.getOrderNum()});
        }
        return result;
    }

    @RequiresPermissions(value = "skrivet:resource:list")
    @Override
    public PageList<ResourceListSP> selectPageList(ResourceSelectPageSQ condition, LoginUser loginUser) {
        PageList<ResourceListSP> page = new PageList<ResourceListSP>();
        ResourceSelectPageDQ request = entityConvert.convert(condition, ResourceSelectPageDQ.class);
        page.setCount(resourceDao.selectPageCount(request));
        page.setData(entityConvert.convertList(resourceDao.selectPageList(request), ResourceListSP.class));
        return page;
    }


}
