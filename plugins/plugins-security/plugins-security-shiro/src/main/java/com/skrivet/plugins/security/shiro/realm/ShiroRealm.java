package com.skrivet.plugins.security.shiro.realm;

import com.skrivet.components.shiro.principal.SimpleByteSource;
import com.skrivet.core.common.exception.account.AccountDisableExp;
import com.skrivet.core.common.exception.account.AccountNotFoundExp;
import com.skrivet.core.common.exception.account.IncorrectPasswordExp;
import com.skrivet.plugins.security.core.entity.PermissionSet;
import com.skrivet.plugins.security.core.entity.User;
import com.skrivet.plugins.security.core.service.PermissionSetService;
import com.skrivet.plugins.security.core.service.UserService;
import com.skrivet.plugins.security.shiro.principal.ShiroPrincipal;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class ShiroRealm extends AuthorizingRealm implements PermissionSetAware {
    private UserService userService;
    private PermissionSetService permissionSetService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    // 登录凭证缓存key
    @Override
    protected Object getAuthenticationCacheKey(AuthenticationToken token) {
        // 以用户名缓存
        return (String) token.getPrincipal();
    }

    protected Object getAuthenticationCacheKey(PrincipalCollection principals) {
        return ((ShiroPrincipal) principals.getPrimaryPrincipal()).getUser().getAccount();
    }

    public void setPermissionSetService(PermissionSetService permissionSetService) {
        this.permissionSetService = permissionSetService;
    }

    //用户权限信息缓存的key，使用userId
    @Override
    protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
        return ((ShiroPrincipal) principals.getPrimaryPrincipal()).getUser().getId();
    }

    // 获取用户权限
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = ((ShiroPrincipal) principals.getPrimaryPrincipal()).getUser();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Set<String> roles = null;
        Set<String> permissions = null;
        if (permissionSetService != null) {
            PermissionSet permissionSet = permissionSetService.selectPermissionSetByUserId(user.getId());
            roles = permissionSet.getRoles();
            permissions = permissionSet.getPermissions();
        }
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    @Override
    protected void assertCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) throws AuthenticationException {
        ShiroPrincipal shiroPrincipal = (ShiroPrincipal) info.getPrincipals().getPrimaryPrincipal();
        validateUser(shiroPrincipal.getUser());
        super.assertCredentialsMatch(token, info);

    }

    private void validateUser(User user) {
        if (user.getState() != 1) {
            throw new AuthenticationException(new AccountDisableExp(user.getDisableReason()));
        }
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken shiroToken = (UsernamePasswordToken) authenticationToken;
        String userName = shiroToken.getUsername();
        User user = userService.selectUserByUserName(userName);
        if (user == null) {
            throw new AuthenticationException(new AccountNotFoundExp());
        }
        String password = user.getPassword();
        String salt = user.getSalt();
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(new ShiroPrincipal(user), password,
                new SimpleByteSource(salt.getBytes()), getName());
        return authenticationInfo;
    }

    @Override
    public PermissionSet getPermissionSet(String userId) {
        User user = new User();
        user.setId(userId);
        ShiroPrincipal shiroPrincipal = new ShiroPrincipal(user);
        PrincipalCollection principalCollection = new SimplePrincipalCollection(shiroPrincipal, getName());
        SimpleAuthorizationInfo authorizationInfo = (SimpleAuthorizationInfo) getAuthorizationInfo(principalCollection);
        PermissionSet permissionSet = new PermissionSet();
        permissionSet.setRoles(authorizationInfo.getRoles());
        permissionSet.setPermissions(authorizationInfo.getStringPermissions());
        return permissionSet;
    }
}
