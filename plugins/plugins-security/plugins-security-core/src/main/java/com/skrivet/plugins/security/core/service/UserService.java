package com.skrivet.plugins.security.core.service;

import com.skrivet.core.common.exception.BizExp;
import com.skrivet.plugins.security.core.entity.User;

public interface UserService {
    public User selectUserByUserName(String userName) throws BizExp;
}
