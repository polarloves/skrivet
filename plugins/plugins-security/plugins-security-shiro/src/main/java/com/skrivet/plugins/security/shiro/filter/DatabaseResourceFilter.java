package com.skrivet.plugins.security.shiro.filter;

import com.skrivet.core.common.exception.account.AccountNotLoginExp;
import com.skrivet.core.common.exception.account.PermissionDeniedExp;
import com.skrivet.plugins.security.core.holder.FilterHolder;
import com.skrivet.plugins.security.core.service.ResourceService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseResourceFilter extends PathMatchingFilter {
    private ResourceService resourceService;
    private Map<String, List<String>> sourcePermissions;
    private boolean initialized = false;

    public synchronized void loadDataBasePermissions() {
        // 从数据库读取资源权限列表
        if (resourceService != null) {
            sourcePermissions = resourceService.resourcePermissions();
        }

    }

    private List<String> findRequiredPermissions(ServletRequest request) {
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

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        if (!initialized) {
            loadDataBasePermissions();
            initialized = true;
        }
        Subject currentUser = SecurityUtils.getSubject();
        if (sourcePermissions != null && sourcePermissions.size() > 0) {
            List<String> requiredPermissions = findRequiredPermissions(request);
            if (requiredPermissions.size() > 0) {
                //需要校验权限
                if (!currentUser.isAuthenticated()) {
                    // 用户未登录
                    FilterHolder.setException(new AccountNotLoginExp());
                } else {
                    // 开始验证权限
                    boolean flag = false;
                    for (String permission : requiredPermissions) {
                        if (!currentUser.isPermitted(permission)) {
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
        return true;
    }

    public void setResourceService(ResourceService resourceService) {
        this.resourceService = resourceService;
    }
}