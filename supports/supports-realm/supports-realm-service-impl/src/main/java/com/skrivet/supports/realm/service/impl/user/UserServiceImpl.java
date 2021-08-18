package com.skrivet.supports.realm.service.impl.user;

import com.skrivet.core.common.annotations.DynamicDataBase;
import com.skrivet.core.common.annotations.PublishEvent;
import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.entity.PageList;
import com.skrivet.core.common.exception.DataExistExp;
import com.skrivet.core.toolkit.DateUtils;
import com.skrivet.plugins.queue.core.Channels;
import com.skrivet.plugins.queue.core.annotations.Publish;
import com.skrivet.plugins.security.core.annotations.RequiresPermissions;
import com.skrivet.plugins.service.core.BasicService;
import com.skrivet.supports.realm.dao.core.user.UserDao;
import com.skrivet.supports.realm.dao.core.user.entity.add.UserAddDQ;
import com.skrivet.supports.realm.dao.core.user.entity.select.UserDetailDP;
import com.skrivet.supports.realm.dao.core.user.entity.select.UserSelectPageDQ;
import com.skrivet.supports.realm.dao.core.user.entity.update.PasswordUpdateDQ;
import com.skrivet.supports.realm.dao.core.user.entity.update.UserUpdateDQ;
import com.skrivet.supports.realm.dao.core.user.entity.update.UserUpdateMineDQ;
import com.skrivet.supports.realm.service.core.user.UserService;

import com.skrivet.supports.realm.service.core.user.entity.add.UserAddSQ;
import com.skrivet.supports.realm.service.core.user.entity.select.UserDetailSP;
import com.skrivet.supports.realm.service.core.user.entity.select.UserListSP;
import com.skrivet.supports.realm.service.core.user.entity.select.UserSelectPageSQ;
import com.skrivet.supports.realm.service.core.user.entity.update.PasswordUpdateSQ;
import com.skrivet.supports.realm.service.core.user.entity.update.UserUpdateSQ;
import com.skrivet.supports.realm.service.core.user.entity.update.UserUpdateMineSQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;

@Service("userService")
@DynamicDataBase
@CacheConfig(cacheNames = "userCache")
public class UserServiceImpl extends BasicService implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired(required = false)
    private CacheManager cacheManager;
    @Autowired
    private com.skrivet.plugins.security.core.service.UserService userService;

    @Caching(evict = {
            @CacheEvict(cacheNames = "securityUserNameCache", key = "#entity.userName"),
            @CacheEvict(key = "#result")
    })
    @Transactional(transactionManager = "realmTM")
    @PublishEvent(id = "#result", name = "user", action = "add")
    @RequiresPermissions(value = "skrivet:user:add")
    @Override
    public String insert(@Valid UserAddSQ entity, LoginUser loginUser) {
        String id = idGenerator.generate();
        UserAddDQ data = entityConvert.convert(entity, UserAddDQ.class);
        if (userDao.selectSecurityUserByUserName(data.getUserName()) != null) {
            throw new DataExistExp().variable(data.getUserName());
        }
        data.setId(id);
        userDao.insert(data);
        return id;
    }

    @CacheEvict(key = "#id")
    @RequiresPermissions(value = "skrivet:user:delete")
    @Transactional(transactionManager = "realmTM")
    @PublishEvent(id = "#id", name = "user", action = "delete", opportunity = PublishEvent.Opportunity.BEFORE)
    @Override
    public Long deleteById(String id, LoginUser loginUser) {
        removeUserNameCacheByUserId(id);
        return userDao.deleteById(id);
    }

    @RequiresPermissions(value = "skrivet:user:delete")
    @Transactional(transactionManager = "realmTM")
    @Override
    public Long deleteMultiById(String[] ids, LoginUser loginUser) {
        UserService current = current(UserService.class);
        long result = 0;
        for (String id : ids) {
            result = result + current.deleteById(id, loginUser);
        }
        return result;
    }

    @CacheEvict(key = "#entity.id")
    @PublishEvent(id = "#entity.id", name = "user", action = "update")
    @RequiresPermissions(value = "skrivet:user:update")
    @Transactional(transactionManager = "realmTM")
    @Override
    public Long update(@Valid UserUpdateSQ entity, LoginUser loginUser) {
        UserUpdateDQ data = entityConvert.convert(entity, UserUpdateDQ.class);
        return userDao.update(data);
    }

    @CacheEvict(key = "#entity.id")
    @PublishEvent(id = "#entity.id", name = "user", action = "update")
    @RequiresPermissions(value = "skrivet:user:update")
    @Transactional(transactionManager = "realmTM")
    @Override
    public Long updateMine(@Valid UserUpdateMineSQ entity, LoginUser loginUser) {
        UserUpdateMineDQ data = entityConvert.convert(entity, UserUpdateMineDQ.class);
        return userDao.updateMine(data);
    }


    @Cacheable(key = "#id")
    @RequiresPermissions(value = "skrivet:user:detail")
    @Override
    public UserDetailSP selectOneById(String id, LoginUser loginUser) {
        UserDetailDP entity = userDao.selectOneById(id);
        return entityConvert.convert(entity, UserDetailSP.class);
    }

    @RequiresPermissions(value = "skrivet:user:list")
    @Override
    public PageList<UserListSP> selectPageList(@Valid UserSelectPageSQ condition, LoginUser loginUser) {
        PageList<UserListSP> page = new PageList<UserListSP>();
        UserSelectPageDQ request = entityConvert.convert(condition, UserSelectPageDQ.class);
        page.setCount(userDao.selectPageCount(request));
        page.setData(entityConvert.convertList(userDao.selectPageList(request), UserListSP.class));
        return page;
    }

    @Override
    public Long login(String userName, String ip) {
        return userDao.login(userName, ip, DateUtils.getDateTime());
    }

    @PublishEvent(id = "#condition.id", name = "user", action = "update")
    @CacheEvict(key = "#condition.id")
    @RequiresPermissions(value = "skrivet:user:password:update")
    @Transactional(transactionManager = "realmTM")
    @Override
    public Long updatePassword(@Valid PasswordUpdateSQ condition, LoginUser loginUser) {
        removeUserNameCacheByUserId(condition.getId());
        return userDao.updatePassword(entityConvert.convert(condition, PasswordUpdateDQ.class));
    }

    private void removeUserNameCacheByUserId(String id) {
        if (cacheManager != null) {
            UserService current = current(UserService.class);
            UserDetailSP user = current.selectOneById(id, LoginUser.IGNORED);
            if (user != null) {
                //删除缓存
                cacheManager.getCache("securityUserNameCache").evict(user.getUserName());
            }
        }
    }

    @PublishEvent(id = "#id", name = "user", action = "disable")
    @CacheEvict(key = "#id")
    @RequiresPermissions(value = "skrivet:user:disable")
    @Transactional(transactionManager = "realmTM")
    @Override
    public Long disable(String id, String reason, LoginUser loginUser) {
        removeUserNameCacheByUserId(id);
        return userDao.disable(id, reason);
    }

    @PublishEvent(id = "#id", name = "user", action = "enable")
    @Override
    public Long enable(String id, LoginUser loginUser) {
        removeUserNameCacheByUserId(id);
        return userDao.enable(id);
    }
}
