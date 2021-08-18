package com.skrivet.supports.data.service.impl.tree;

import com.skrivet.core.common.annotations.DynamicDataBase;
import com.skrivet.core.common.annotations.PublishEvent;
import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.entity.PageList;
import com.skrivet.core.common.exception.DataExistExp;
import com.skrivet.core.common.exception.ValidationExp;
import com.skrivet.plugins.queue.core.Channels;
import com.skrivet.plugins.queue.core.annotations.Publish;
import com.skrivet.plugins.security.core.annotations.RequiresPermissions;
import com.skrivet.plugins.service.core.BasicService;
import com.skrivet.supports.data.dao.core.tree.TreeDao;
import com.skrivet.supports.data.dao.core.tree.entity.add.TreeAddDQ;
import com.skrivet.supports.data.dao.core.tree.entity.select.*;
import com.skrivet.supports.data.dao.core.tree.entity.update.TreeUpdateDQ;
import com.skrivet.supports.data.service.core.tree.TreeService;
import com.skrivet.supports.data.service.core.tree.entity.add.TreeAddSQ;
import com.skrivet.supports.data.service.core.tree.entity.select.*;
import com.skrivet.supports.data.service.core.tree.entity.update.TreeUpdateSQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("treeService")
@DynamicDataBase
@CacheConfig(cacheNames = "treeCache")
public class TreeServiceImpl extends BasicService implements TreeService {
    @Autowired
    private TreeDao treeDao;
    @Autowired(required = false)
    private CacheManager cacheManager;

    @Caching(evict = {
            @CacheEvict(cacheNames = "treeGroupCache", key = "#entity.groupId"),
            @CacheEvict(key = "#result")
    })
    @PublishEvent(id = "#result", name = "tree", action = "add")
    @Transactional(transactionManager = "dataTM")
    @RequiresPermissions(value = "skrivet:tree:add")
    @Override
    public String insert(TreeAddSQ entity, LoginUser loginUser) {
        TreeDetailDP old = treeDao.selectOneByValue(entity.getGroupId(), entity.getValue());
        if (old != null) {
            throw new DataExistExp().variable(entity.getValue());
        }
        String id = idGenerator.generate();
        TreeAddDQ data = entityConvert.convert(entity, TreeAddDQ.class);
        data.setId(id);
        treeDao.insert(data);
        return id;
    }

    @PublishEvent(id = "#id", name = "tree", action = "delete",opportunity= PublishEvent.Opportunity.BEFORE)
    @CacheEvict(key = "#id")
    @RequiresPermissions(value = "skrivet:tree:delete")
    @Transactional(transactionManager = "dataTM")
    @Override
    public Long deleteById(String id, LoginUser loginUser) {
        if (cacheManager != null) {
            TreeService current = current(TreeService.class);
            TreeDetailSP treeDetailSP = current.selectOneById(id, LoginUser.IGNORED);
            if (treeDetailSP != null) {
                //删除缓存
                cacheManager.getCache("treeGroupCache").evict(treeDetailSP.getGroupId());
            }
        }
        return treeDao.deleteById(id);
    }

    @RequiresPermissions(value = "skrivet:tree:delete")
    @Transactional(transactionManager = "dataTM")
    @Override
    public Long deleteMultiById(String[] ids, LoginUser loginUser) {
        TreeService current = current(TreeService.class);
        long result = 0;
        for (String id : ids) {
            result = result + current.deleteById(id, loginUser);
        }
        return result;
    }


    @Caching(evict = {
            @CacheEvict(cacheNames = "treeGroupCache", key = "#entity.groupId"),
            @CacheEvict(key = "#entity.id")
    })
    @PublishEvent(id = "#entity.id", name = "tree", action = "update")
    @RequiresPermissions(value = "skrivet:tree:update")
    @Transactional(transactionManager = "dataTM")
    @Override
    public Long update(TreeUpdateSQ entity, LoginUser loginUser) {
        TreeDetailDP old = treeDao.selectOneByValue(entity.getGroupId(), entity.getValue());
        if (entity.getId().equals(entity.getParentId())) {
            throw new ValidationExp("不能选择自身作为父级");
        }
        if (old != null && !old.getId().equals(entity.getId())) {
            throw new DataExistExp().variable(entity.getValue());
        }
        TreeUpdateDQ data = entityConvert.convert(entity, TreeUpdateDQ.class);
        return treeDao.update(data);
    }

    @RequiresPermissions(value = "skrivet:tree:detail")
    @Cacheable(key = "#id")
    @Override
    public TreeDetailSP selectOneById(String id, LoginUser loginUser) {
        TreeDetailDP entity = treeDao.selectOneById(id);
        return entityConvert.convert(entity, TreeDetailSP.class);
    }


    @Cacheable(key = "#groupId", cacheNames = "treeGroupCache")
    @Override
    public List<TreeListSP> selectListByGroupId(String groupId, LoginUser loginUser) {
        List<TreeListDP> response = treeDao.selectListByGroupId(groupId);
        return entityConvert.convertList(response, TreeListSP.class);
    }

    @RequiresPermissions(value = "skrivet:tree:list")
    @Override
    public PageList<TreeListSP> selectPageList(TreeSelectPageSQ condition, LoginUser loginUser) {
        PageList<TreeListSP> page = new PageList<TreeListSP>();
        TreeSelectPageDQ request = entityConvert.convert(condition, TreeSelectPageDQ.class);
        page.setCount(treeDao.selectPageCount(request));
        page.setData(entityConvert.convertList(treeDao.selectPageList(request), TreeListSP.class));
        return page;
    }


    @RequiresPermissions(value = "skrivet:tree:groups")
    @Override
    public List<TreeGroupSP> groups(LoginUser loginUser) {
        return entityConvert.convertList(treeDao.groups(), TreeGroupSP.class);
    }
}
