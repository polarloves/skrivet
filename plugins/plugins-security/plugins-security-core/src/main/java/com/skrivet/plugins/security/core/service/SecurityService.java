package com.skrivet.plugins.security.core.service;

import com.skrivet.plugins.security.core.entity.ActiveUser;
import com.skrivet.plugins.security.core.entity.User;

import java.util.List;

public interface SecurityService {
    public List<ActiveUser> activeUser(String userId);

    public String currentUserId();

    public User currentUser();

    public void shotOffBySessionId(String sessionId, String msg);

    public void shotOffByUserId(String userId, String msg);

    public void removeTokenCache(String userName);

    public void removePermissionCache(String userId);

    public String login(String userName, String password, boolean checkPassword, boolean alwaysActive);

    public void logout();
}
