package com.skrivet.supports.realm.service.impl.dept;

import com.skrivet.core.common.annotations.DynamicDataBase;
import com.skrivet.core.common.annotations.PublishEvent;
import com.skrivet.core.common.annotations.ValidateIgnore;
import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.exception.ValidationExp;
import com.skrivet.plugins.queue.core.Channels;
import com.skrivet.plugins.queue.core.annotations.Publish;
import com.skrivet.plugins.security.core.annotations.RequiresPermissions;
import com.skrivet.plugins.service.core.BasicService;
import com.skrivet.supports.realm.dao.core.dept.DeptDao;
import com.skrivet.supports.realm.dao.core.dept.entity.add.DeptAddDQ;
import com.skrivet.supports.realm.dao.core.dept.entity.update.DeptUpdateDQ;
import com.skrivet.supports.realm.service.core.dept.DeptService;
import com.skrivet.supports.realm.service.core.dept.entity.add.DeptAddSQ;
import com.skrivet.supports.realm.service.core.dept.entity.select.DeptDetailSP;
import com.skrivet.supports.realm.service.core.dept.entity.select.DeptListSP;
import com.skrivet.supports.realm.service.core.dept.entity.update.DeptUpdateSQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("deptService")
@DynamicDataBase
@CacheConfig(cacheNames = "deptCache")
public class DeptServiceImpl extends BasicService implements DeptService {
    @Autowired
    private DeptDao deptDao;

    @ValidateIgnore
    @Caching(
            evict = {
                    @CacheEvict(key = "'allList'"),
                    @CacheEvict(key = "#result")
            }
    )
    @PublishEvent(id = "#result", name = "dept", action = "add")
    @Transactional(transactionManager = "realmTM")
    @RequiresPermissions(value = "skrivet:dept:add")
    @Override
    public String insert(@Valid DeptAddSQ entity, LoginUser loginUser) {
        DeptAddDQ data = entityConvert.convert(entity, DeptAddDQ.class);
        data.setId(idGenerator.generate());
        deptDao.insert(data);
        return data.getId();
    }

    @Caching(
            evict = {
                    @CacheEvict(key = "'allList'"),
                    @CacheEvict(key = "#id")
            }
    )
    @PublishEvent(id = "#id", name = "dept", action = "delete")
    @RequiresPermissions(value = "skrivet:dept:delete")
    @Transactional(transactionManager = "realmTM")
    @Override
    public Long deleteById(String id, LoginUser loginUser) {
        DeptService current = current(DeptService.class);
        //判断是否有子节点
        List<DeptListSP> all = current.selectList(LoginUser.IGNORED);
        if (!CollectionUtils.isEmpty(all)) {
            for (DeptListSP detail : all) {
                if (id.equals(detail.getParentId())) {
                    throw new ValidationExp("含有子节点，无法删除!");
                }
            }
        }
        return deptDao.deleteById(id);
    }


    @Caching(
            evict = {
                    @CacheEvict(key = "'allList'"),
                    @CacheEvict(key = "#entity.id")
            }
    )
    @PublishEvent(id = "#entity.id", name = "dept", action = "update")
    @RequiresPermissions(value = "skrivet:dept:update")
    @Transactional(transactionManager = "realmTM")
    @Override
    public Long update(@Valid DeptUpdateSQ entity, LoginUser loginUser) {
        if (entity.getId().equals(entity.getParentId())) {
            throw new ValidationExp("不能选择自身作为父级");
        }
        return deptDao.update(entityConvert.convert(entity, DeptUpdateDQ.class));
    }

    @Cacheable(key = "#id")
    @RequiresPermissions(value = "skrivet:dept:detail")
    @Override
    public DeptDetailSP selectOneById(String id, LoginUser loginUser) {
        return entityConvert.convert(deptDao.selectOneById(id), DeptDetailSP.class);
    }

    @Cacheable(key = "'allList'")
    @Override
    public List<DeptListSP> selectList(LoginUser loginUser) {
        List<DeptListSP> result = entityConvert.convertList(deptDao.selectList(), DeptListSP.class);
        if (!CollectionUtils.isEmpty(result)) {
            Map<String, Long> parentOrderNums = new HashMap<>();
            result.stream().forEach(tmp -> {
                if (StringUtils.isEmpty(tmp.getParentId())) {
                    parentOrderNums.put(tmp.getId(), tmp.getOrderNum());
                }
            });
            com.skrivet.core.toolkit.CollectionUtils.sort(result, true, data -> {
                return new long[]{StringUtils.isEmpty(data.getParentId()) ? -1 : (parentOrderNums.containsKey(data.getParentId()) ? parentOrderNums.get(data.getParentId()) : -1), data.getOrderNum()};
            });
        }

        return result;
    }
}
