package com.skrivet.plugins.security.jwt.filter;

import com.auth0.jwt.impl.PublicClaims;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.skrivet.components.jwt.subject.Action;
import com.skrivet.components.jwt.subject.Subject;
import com.skrivet.core.common.exception.account.AccountAuthExpireExp;
import com.skrivet.plugins.security.core.entity.PermissionSet;
import com.skrivet.plugins.security.core.service.PermissionSetService;
import com.skrivet.plugins.security.core.service.UserService;
import com.skrivet.plugins.security.jwt.util.JWTUtil;
import com.skrivet.core.common.exception.BizExp;
import com.skrivet.core.common.exception.account.AccountNotLoginExp;
import com.skrivet.core.common.exception.account.PermissionDeniedExp;
import com.skrivet.plugins.security.core.entity.User;
import com.skrivet.plugins.security.core.holder.FilterHolder;
import com.skrivet.plugins.security.core.service.ResourceService;
import com.skrivet.plugins.security.jwt.util.SubjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class JWTFilter implements Filter {
    public static String tokenKey;
    public static String actionKey;
    private String signKey;
    private long timeout;
    private long refreshBeforeTimeout;
    private ResourceService resourceService;
    private Map<String, List<String>> sourcePermissions;
    private boolean initialized = false;
    private PathMatcher pathMatcher = new AntPathMatcher();
    private UrlPathHelper urlPathHelper = new UrlPathHelper();
    private PermissionSetService permissionSetService;
    private UserService userService;

    public synchronized void loadDataBasePermissions() {
        // 从数据库读取资源权限列表
        if (resourceService != null) {
            sourcePermissions = resourceService.resourcePermissions();
        }
        initialized = true;
    }

    public JWTFilter(ResourceService resourceService, UserService userService, PermissionSetService permissionSetService, String tokenKey, String actionKey, String signKey, long timeout, long refreshBeforeTimeout) {
        this.tokenKey = tokenKey;
        this.actionKey = actionKey;
        this.signKey = signKey;
        this.timeout = timeout;
        this.refreshBeforeTimeout = refreshBeforeTimeout;
        this.resourceService = resourceService;
        this.userService = userService;
        this.permissionSetService = permissionSetService;
    }

    private List<String> findRequiredPermissions(HttpServletRequest request) {
        List<String> result = new ArrayList<>();
        Map<String, List<String>> sourcePermissions = this.sourcePermissions;
        if (sourcePermissions != null && sourcePermissions.size() > 0) {
            for (String path : sourcePermissions.keySet()) {
                if (pathsMatch(path, request)) {
                    result.addAll(sourcePermissions.get(path));
                }
            }
        }
        return result;
    }

    protected boolean pathsMatch(String path, HttpServletRequest request) {
        String lookupPath = this.urlPathHelper.getLookupPathForRequest(request, null);
        return pathMatcher.match(path, lookupPath);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            if (!initialized) {
                loadDataBasePermissions();
            }
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            String token = httpServletRequest.getHeader(tokenKey);
            if (StringUtils.isEmpty(token)) {
                token = httpServletRequest.getParameter(tokenKey);
            }
            FilterHolder.setCurrentIdentify(token);
            if (!StringUtils.isEmpty(token)) {
                try {
                    Subject subject = new Subject();
                    subject.setToken(token);
                    try {
                        DecodedJWT jwt = JWTUtil.decodeToken(token, signKey);
                        String userId = jwt.getAudience().get(0);
                        String userName = jwt.getAudience().get(1);
                        PermissionSet permissionSet = permissionSetService.selectPermissionSetByUserId(userId);
                        User user = new User();
                        user.setAccount(userName);
                        user.setId(userId);
                        user.setRoles(permissionSet.getRoles());
                        user.setPermissions(permissionSet.getPermissions());
                        subject.setObject(user);
                        //token过期校验
                        if (timeout > 0) {
                            Date expireData = jwt.getClaim(PublicClaims.EXPIRES_AT).asDate();
                            //执行刷新token的逻辑
                            if (System.currentTimeMillis() > expireData.getTime() - refreshBeforeTimeout) {
                                String newToken = JWTUtil.createToken(userId, userName, signKey, timeout);
                                subject.setToken(newToken);
                                subject.setAction(Action.UPDATE);
                            }
                        }
                    } catch (AccountAuthExpireExp expireExp) {
                        //token超时
                        subject.setAction(Action.DELETE);
                        subject.setToken(null);
                        subject.setObject(null);
                    }
                    SubjectUtil.bindSubject(subject);
                } catch (BizExp e) {
                    FilterHolder.setException(e);
                }
            }
            Subject currentUser = SubjectUtil.getSubject();
            if (currentUser != null && currentUser.getAction() != null) {
                SubjectUtil.noticeSubjectChanged(currentUser.getAction(), (HttpServletResponse) servletResponse);
            }
            if (sourcePermissions != null && sourcePermissions.size() > 0) {
                List<String> requiredPermissions = findRequiredPermissions(httpServletRequest);
                if (requiredPermissions.size() > 0) {
                    //需要校验权限
                    if (currentUser == null) {
                        // 用户未登录
                        FilterHolder.setException(new AccountNotLoginExp());
                    } else if (currentUser.getObject() == null) {
                        FilterHolder.setException(new AccountAuthExpireExp());
                    } else {
                        Set<String> permissions = ((User) currentUser.getObject()).getPermissions();
                        // 开始验证权限
                        boolean flag = false;
                        for (String permission : requiredPermissions) {
                            if (!CollectionUtils.isEmpty(permissions)) {
                                if (!permissions.contains(permission)) {
                                    flag = true;
                                    break;
                                }
                            } else {
                                flag = true;
                                break;
                            }
                        }
                        if (flag) {
                            FilterHolder.setException(new PermissionDeniedExp());
                        }
                    }
                }
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            FilterHolder.clear();
            SubjectUtil.clear();
        }
    }


}
