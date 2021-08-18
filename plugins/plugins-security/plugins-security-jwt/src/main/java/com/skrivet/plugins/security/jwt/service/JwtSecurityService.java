package com.skrivet.plugins.security.jwt.service;

import com.skrivet.components.jwt.subject.Action;
import com.skrivet.components.jwt.subject.Subject;
import com.skrivet.core.common.exception.NotSupportExp;
import com.skrivet.core.common.exception.account.AccountDisableExp;
import com.skrivet.core.common.exception.account.AccountNotFoundExp;
import com.skrivet.core.common.exception.account.IncorrectPasswordExp;
import com.skrivet.plugins.security.core.encryption.EncryptionService;
import com.skrivet.plugins.security.core.entity.ActiveUser;
import com.skrivet.plugins.security.core.entity.PermissionSet;
import com.skrivet.plugins.security.core.entity.User;
import com.skrivet.plugins.security.core.service.PermissionSetService;
import com.skrivet.plugins.security.core.service.SecurityService;
import com.skrivet.plugins.security.core.service.UserService;
import com.skrivet.plugins.security.jwt.util.JWTUtil;
import com.skrivet.plugins.security.jwt.util.SubjectUtil;

import java.util.List;

public class JwtSecurityService implements SecurityService {
    private UserService userService;
    private PermissionSetService permissionSetService;
    private String signKey;
    private long timeout;
    private EncryptionService encryptionService;

    public JwtSecurityService(UserService userService, PermissionSetService permissionSetService, EncryptionService encryptionServic, String signKey, long timeout) {
        this.userService = userService;
        this.permissionSetService = permissionSetService;
        this.signKey = signKey;
        this.timeout = timeout;
        this.encryptionService = encryptionServic;
    }

    @Override
    public List<ActiveUser> activeUser(String userId) {
        return null;
    }

    @Override
    public String currentUserId() {
        User user = currentUser();
        if (user != null) {
            return user.getId();
        }
        return null;
    }

    @Override
    public User currentUser() {
        Subject subject = SubjectUtil.getSubject();
        if (subject != null) {
            return (User) subject.getObject();
        }
        return null;
    }

    @Override
    public void shotOffBySessionId(String sessionId, String msg) {
        throw new NotSupportExp().variable("shotOffBySessionId");
    }

    @Override
    public void shotOffByUserId(String userId, String msg) {
        throw new NotSupportExp().variable("shotOffByUserId");
    }

    @Override
    public void removeTokenCache(String userName) {

    }

    @Override
    public void removePermissionCache(String userId) {

    }

    @Override
    public String login(String userName, String password, boolean checkPassword, boolean alwaysActive) {
        User user = userService.selectUserByUserName(userName);
        if (user == null) {
            throw new AccountNotFoundExp();
        }
        if (user.getState() != 1) {
            throw new AccountDisableExp(user.getDisableReason());
        }
        if (checkPassword) {
            if (!encryptionService.encryptionPassword(password, user.getSalt()).equals(user.getPassword())) {
                throw new IncorrectPasswordExp();
            }
        }
        PermissionSet permissionSet = permissionSetService.selectPermissionSetByUserId(user.getId());
        user.setPermissions(permissionSet.getPermissions());
        user.setRoles(permissionSet.getRoles());
        String token = JWTUtil.createToken(user.getId(), user.getAccount(), signKey, timeout);
        Subject subject = new Subject();
        subject.setToken(token);
        subject.setAction(Action.CREATE);
        subject.setObject(user);
        SubjectUtil.bindSubject(subject);
        SubjectUtil.noticeSubjectChanged(Action.CREATE);
        return token;
    }


    @Override
    public void logout() {
        SubjectUtil.noticeSubjectChanged(Action.DELETE);
    }

}
