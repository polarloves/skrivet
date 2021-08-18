package com.skrivet.supports.realm.service.impl.user;

import com.skrivet.core.common.annotations.DynamicDataBase;
import com.skrivet.core.common.exception.BizExp;
import com.skrivet.plugins.security.core.entity.User;
import com.skrivet.plugins.security.core.service.UserService;
import com.skrivet.plugins.service.core.BasicService;
import com.skrivet.supports.realm.dao.core.user.UserDao;
import com.skrivet.supports.realm.dao.core.user.entity.select.SecurityUserDP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service("securityUserService")
@DynamicDataBase
@CacheConfig(cacheNames = "securityUserCache")
public class SecurityUserServiceImpl extends BasicService implements UserService {

    @Autowired
    private UserDao userDao;
    @Cacheable(key = "#userName",cacheNames = "securityUserNameCache")
    @Override
    public User selectUserByUserName(String userName) throws BizExp {
        return convert(userDao.selectSecurityUserByUserName(userName));
    }

    private User convert(SecurityUserDP user){
        if(user!=null){
            User result=new User();
            result.setId(user.getId());
            result.setPassword(user.getPassword());
            result.setSalt(user.getSalt());
            result.setAccount(user.getAccount());
            result.setState(user.getState());
            result.setDisableReason(user.getDisableReason());
            return result;
        }
        return null;
    }
}
