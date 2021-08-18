package com.skrivet.supports.authority.service.impl.role;

import com.skrivet.core.common.annotations.DynamicDataBase;
import com.skrivet.core.common.annotations.PublishEvent;
import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.exception.DataExistExp;

import com.skrivet.plugins.security.core.annotations.RequiresPermissions;
import com.skrivet.plugins.service.core.BasicService;
import com.skrivet.supports.authority.dao.core.role.RoleDao;
import com.skrivet.supports.authority.dao.core.role.entity.add.RoleAddDQ;
import com.skrivet.supports.authority.dao.core.role.entity.add.UserRoleAddDQ;
import com.skrivet.supports.authority.dao.core.role.entity.select.RoleDetailDP;
import com.skrivet.supports.authority.dao.core.role.entity.update.RoleUpdateDQ;
import com.skrivet.supports.authority.service.core.role.RoleService;
import com.skrivet.supports.authority.service.core.role.entity.add.RoleAddSQ;
import com.skrivet.supports.authority.service.core.role.entity.select.RoleDetailSP;
import com.skrivet.supports.authority.service.core.role.entity.select.RoleListSP;
import com.skrivet.supports.authority.service.core.role.entity.update.RoleUpdateSQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


import java.util.*;

@Service("roleService")
@DynamicDataBase
@CacheConfig(cacheNames = "roleCache")
public class RoleServiceImpl extends BasicService implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Caching(
            evict = {
                    @CacheEvict(key = "'allList'"),
                    @CacheEvict(cacheNames = "rolePermission", key = "#result"),
                    @CacheEvict(key = "#result")
            }
    )
    @PublishEvent(id = "#result", name = "role", action = "add")
    @Transactional(transactionManager = "authorityTM")
    @RequiresPermissions(value = "skrivet:role:add")
    @Override
    public String insert(RoleAddSQ entity, LoginUser loginUser) {
        if (roleDao.selectOneByValue(entity.getValue()) != null) {
            throw new DataExistExp().variable(entity.getValue());
        }
        RoleAddDQ data = entityConvert.convert(entity, RoleAddDQ.class);
        data.setId(idGenerator.generate());
        roleDao.insert(data);
        return data.getId();
    }

    @PublishEvent(id = "#id", name = "role", action = "delete",opportunity = PublishEvent.Opportunity.BEFORE)
    @Caching(
            evict = {
                    @CacheEvict(key = "'allList'"),
                    @CacheEvict(cacheNames = "rolePermission", key = "#id"),
                    @CacheEvict(key = "#id")
            }
    )
    @RequiresPermissions(value = "skrivet:role:delete")
    @Transactional(transactionManager = "authorityTM")
    @Override
    public Long deleteById(String id, LoginUser loginUser) {
        return roleDao.deleteById(id);
    }

    @RequiresPermissions(value = "skrivet:role:delete")
    @Transactional(transactionManager = "authorityTM")
    @Override
    public Long deleteMultiById(String[] ids, LoginUser loginUser) {
        RoleService current = current(RoleService.class);
        long result = 0;
        for (String id : ids) {
            result = result + current.deleteById(id, loginUser);
        }
        return result;
    }

    @PublishEvent(id = "#entity.id", name = "role", action = "update")
    @Caching(
            evict = {
                    @CacheEvict(key = "'allList'"),
                    @CacheEvict(cacheNames = "rolePermission", key = "#entity.id"),
                    @CacheEvict(key = "#entity.id")
            }
    )
    @RequiresPermissions(value = "skrivet:role:update")
    @Transactional(transactionManager = "authorityTM")
    @Override
    public Long update(RoleUpdateSQ entity, LoginUser loginUser) {
        RoleDetailDP old = roleDao.selectOneByValue(entity.getValue());
        if (old != null && !old.getId().equals(entity.getId())) {
            throw new DataExistExp().variable(entity.getValue());
        }
        return roleDao.update(entityConvert.convert(entity, RoleUpdateDQ.class));
    }
    @Cacheable(key = "#id")
    @RequiresPermissions(value = "skrivet:role:detail")
    @Override
    public RoleDetailSP selectOneById(String id, LoginUser loginUser) {
        return entityConvert.convert(roleDao.selectOneById(id), RoleDetailSP.class);
    }

    @Cacheable(key = "'allList'")
    @RequiresPermissions(value = "skrivet:role:list")
    @Override
    public List<RoleListSP> selectList(LoginUser loginUser) {
        List<RoleListSP> result = entityConvert.convertList(roleDao.selectList(), RoleListSP.class);
        if (!CollectionUtils.isEmpty(result)) {
            com.skrivet.core.toolkit.CollectionUtils.sort(result, true, data -> new long[]{data.getOrderNum()});
        }
        return result;
    }

    @RequiresPermissions(value = "skrivet:role:user:list")
    @Cacheable(cacheNames = "userRole", key = "#userId")
    @Override
    public List<String> selectUserRoleIds(String userId, LoginUser loginUser) {
        return roleDao.selectUserRoleIds(userId);
    }

    @RequiresPermissions(value = "skrivet:role:user:update")
    @Transactional(transactionManager = "authorityTM")
    @CacheEvict(cacheNames = "userRole", key = "#userId")
    @Override
    public void updateUserRoles(String userId, String[] roleIds, LoginUser loginUser) {
        roleDao.deleteUserRolesByUserId(userId);
        if (roleIds != null && roleIds.length > 0) {
            UserRoleAddDQ userRoleAddDQ = new UserRoleAddDQ();
            userRoleAddDQ.setUserId(userId);
            List<UserRoleAddDQ.UserRole> roles = new ArrayList<>(roleIds.length);
            for (String roleId : roleIds) {
                roles.add(new UserRoleAddDQ.UserRole(idGenerator.generate(), roleId));
            }
            userRoleAddDQ.setRoles(roles);
            roleDao.insertUserRoles(userRoleAddDQ);
        }

    }
}
