package com.skrivet.plugins.security.core.service.impl;

import com.skrivet.plugins.security.core.entity.ActiveUser;
import com.skrivet.plugins.security.core.entity.User;
import com.skrivet.plugins.security.core.service.SecurityService;

import java.util.List;

public class EmptySecurityService implements SecurityService {
    @Override
    public List<ActiveUser> activeUser(String userId) {
        return null;
    }

    @Override
    public String currentUserId() {
        return null;
    }

    @Override
    public User currentUser() {
        return null;
    }

    @Override
    public void shotOffBySessionId(String sessionId, String msg) {

    }

    @Override
    public void shotOffByUserId(String userId, String msg) {

    }

    @Override
    public void removeTokenCache(String userName) {

    }

    @Override
    public void removePermissionCache(String userId) {

    }

    @Override
    public String login(String userName, String password, boolean checkPassword, boolean alwaysActive) {
        return null;
    }

    @Override
    public void logout() {

    }
}
