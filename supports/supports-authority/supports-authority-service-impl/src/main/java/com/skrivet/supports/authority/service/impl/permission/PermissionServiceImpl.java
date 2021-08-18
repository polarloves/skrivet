package com.skrivet.supports.authority.service.impl.permission;

import com.skrivet.core.common.annotations.DynamicDataBase;
import com.skrivet.core.common.annotations.PublishEvent;
import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.exception.DataExistExp;
import com.skrivet.core.common.exception.ValidationExp;
import com.skrivet.plugins.queue.core.Channels;
import com.skrivet.plugins.queue.core.annotations.Publish;
import com.skrivet.plugins.security.core.annotations.RequiresPermissions;
import com.skrivet.plugins.service.core.BasicService;
import com.skrivet.supports.authority.dao.core.permission.PermissionDao;
import com.skrivet.supports.authority.dao.core.permission.entity.add.PermissionAddDQ;
import com.skrivet.supports.authority.dao.core.permission.entity.add.ResourcePermissionAddDQ;
import com.skrivet.supports.authority.dao.core.permission.entity.add.RolePermissionAddDQ;
import com.skrivet.supports.authority.dao.core.permission.entity.select.PermissionDetailDP;
import com.skrivet.supports.authority.dao.core.permission.entity.update.PermissionUpdateDQ;
import com.skrivet.supports.authority.service.core.permission.PermissionService;
import com.skrivet.supports.authority.service.core.permission.entity.add.PermissionAddSQ;
import com.skrivet.supports.authority.service.core.permission.entity.select.PermissionDetailSP;
import com.skrivet.supports.authority.service.core.permission.entity.select.PermissionListSP;
import com.skrivet.supports.authority.service.core.permission.entity.update.PermissionUpdateSQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Service("permissionService")
@DynamicDataBase
@CacheConfig(cacheNames = "permissionCache")
public class PermissionServiceImpl extends BasicService implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;

    @CacheEvict(key = "'allList'")
    @Caching(
            evict = {
                    @CacheEvict(key = "'allList'"),
                    @CacheEvict(key = "#result")
            }
    )
    @PublishEvent(id = "#result", name = "permission", action = "add")
    @Transactional(transactionManager = "authorityTM")
    @RequiresPermissions(value = "skrivet:permission:add")
    @Override
    public String insert(PermissionAddSQ entity, LoginUser loginUser) {
        if (permissionDao.selectOneByValue(entity.getValue()) != null) {
            throw new DataExistExp().variable(entity.getValue());
        }
        PermissionAddDQ data = entityConvert.convert(entity, PermissionAddDQ.class);
        data.setId(idGenerator.generate());
        permissionDao.insert(data);
        return data.getId();
    }

    @Caching(
            evict = {
                    @CacheEvict(key = "'allList'"),
                    @CacheEvict(key = "#id")
            }
    )
    @PublishEvent(id = "#id", name = "permission", action = "delete",opportunity = PublishEvent.Opportunity.BEFORE)
    @RequiresPermissions(value = "skrivet:permission:delete")
    @Transactional(transactionManager = "authorityTM")
    @Override
    public Long deleteById(String id, LoginUser loginUser) {
        PermissionService current = current(PermissionService.class);
        //判断是否有子节点
        List<PermissionListSP> all = current.selectList(LoginUser.IGNORED);
        if (!CollectionUtils.isEmpty(all)) {
            for (PermissionListSP detail : all) {
                if (id.equals(detail.getParentId())) {
                    throw new ValidationExp("含有子节点，无法删除!");
                }
            }
        }

        return permissionDao.deleteById(id);
    }

    @RequiresPermissions(value = "skrivet:permission:delete")
    @Transactional(transactionManager = "authorityTM")
    @Override
    public Long deleteMultiById(String[] ids, LoginUser loginUser) {
        PermissionService current = current(PermissionService.class);
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
    @PublishEvent(id = "#entity.id", name = "permission", action = "update")
    @RequiresPermissions(value = "skrivet:permission:update")
    @Transactional(transactionManager = "authorityTM")
    @Override
    public Long update(PermissionUpdateSQ entity, LoginUser loginUser) {
        PermissionDetailDP old = permissionDao.selectOneByValue(entity.getValue());
        if (old != null && !old.getId().equals(entity.getId())) {
            throw new DataExistExp().variable(entity.getValue());
        }
        if (entity.getId().equals(entity.getParentId())) {
            throw new ValidationExp("不能选择自身作为父级");
        }
        return permissionDao.update(entityConvert.convert(entity, PermissionUpdateDQ.class));
    }

    @Cacheable(key = "#id")
    @RequiresPermissions(value = "skrivet:permission:detail")
    @Override
    public PermissionDetailSP selectOneById(String id, LoginUser loginUser) {
        return entityConvert.convert(permissionDao.selectOneById(id), PermissionDetailSP.class);
    }

    @Cacheable(key = "'allList'")
    @Override
    public List<PermissionListSP> selectList(LoginUser loginUser) {
        List<PermissionListSP> result = entityConvert.convertList(permissionDao.selectList(), PermissionListSP.class);
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

    @RequiresPermissions(value = "skrivet:permission:role:list")
    @Cacheable(cacheNames = "rolePermission", key = "#roleId")
    @Override
    public List<String> selectRolePermissionIds(String roleId, LoginUser loginUser) {
        return permissionDao.selectRolePermissionIds(roleId);
    }

    @RequiresPermissions(value = "skrivet:permission:role:update")
    @Transactional(transactionManager = "authorityTM")
    @CacheEvict(cacheNames = "rolePermission", key = "#roleId")
    @Override
    public void updateRolePermissions(String roleId, String[] permissionIds, LoginUser loginUser) {
        permissionDao.deleteRolePermissionsByRoleId(roleId);
        if (permissionIds != null && permissionIds.length > 0) {
            RolePermissionAddDQ rolePermissionAddDQ = new RolePermissionAddDQ();
            rolePermissionAddDQ.setRoleId(roleId);
            List<RolePermissionAddDQ.RolePermission> permissions = new ArrayList<>(permissionIds.length);
            for (String permissionId : permissionIds) {
                permissions.add(new RolePermissionAddDQ.RolePermission(idGenerator.generate(), permissionId));
            }
            rolePermissionAddDQ.setPermissions(permissions);
            permissionDao.insertRolePermissions(rolePermissionAddDQ);
        }

    }

    @RequiresPermissions(value = "skrivet:resource:permission:list")
    @Cacheable(cacheNames = "resourcePermission", key = "#resourceId")
    @Override
    public List<String> selectResourcePermissionIds(String resourceId, LoginUser loginUser) {
        return permissionDao.selectResourcePermissionIds(resourceId);
    }

    @RequiresPermissions(value = "skrivet:resource:permission:update")
    @Transactional(transactionManager = "authorityTM")
    @CacheEvict(cacheNames = "resourcePermission", key = "#resourceId")
    @Override
    public void updateResourcePermissions(String resourceId, String[] permissionIds, LoginUser loginUser) {
        permissionDao.deleteResourcePermissionByResourceId(resourceId);
        if (permissionIds != null && permissionIds.length > 0) {
            ResourcePermissionAddDQ resourcePermissionAddDQ = new ResourcePermissionAddDQ();
            resourcePermissionAddDQ.setResourceId(resourceId);
            List<ResourcePermissionAddDQ.ResourcePermission> permissions = new ArrayList<>(permissionIds.length);
            for (String permissionId : permissionIds) {
                permissions.add(new ResourcePermissionAddDQ.ResourcePermission(idGenerator.generate(), permissionId));
            }
            resourcePermissionAddDQ.setPermissions(permissions);
            permissionDao.insertResourcePermissions(resourcePermissionAddDQ);
        }
    }

    @RequiresPermissions(value = "skrivet:resource:permission:reload")
    @Publish(channel = Channels.RELOAD_RESOURCE, value = "'reload'")
    @Override
    public void reloadResourcePermissions() {

    }

}
