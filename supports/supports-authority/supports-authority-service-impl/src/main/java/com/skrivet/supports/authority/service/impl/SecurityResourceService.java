package com.skrivet.supports.authority.service.impl;


import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.plugins.security.core.service.ResourceService;
import com.skrivet.supports.authority.service.core.permission.PermissionService;
import com.skrivet.supports.authority.service.core.permission.entity.select.PermissionListSP;
import com.skrivet.supports.authority.service.core.resource.entity.select.ResourceListSP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Order(Ordered.HIGHEST_PRECEDENCE)
@Service("securityResourceService")
public class SecurityResourceService implements ResourceService {
    @Autowired
    private com.skrivet.supports.authority.service.core.resource.ResourceService resourceService;
    @Autowired
    private PermissionService permissionService;

    @Override
    public Map<String, List<String>> resourcePermissions() {
        Map<String, List<String>> result = new HashMap<>();
        List<ResourceListSP> resourceListSPS = resourceService.selectList(LoginUser.IGNORED);
        List<PermissionListSP> permissionListSPList = permissionService.selectList(LoginUser.IGNORED);
        if (!CollectionUtils.isEmpty(resourceListSPS)) {
            resourceListSPS.stream().forEach(resourceListSP -> {
                if(resourceListSP.getType()==1){
                    List<String> permissionIds = permissionService.selectResourcePermissionIds(resourceListSP.getId(), LoginUser.IGNORED);
                    List<String> permissionList = new ArrayList<>();
                    if (!CollectionUtils.isEmpty(permissionIds)) {
                        permissionIds.stream().forEach(permissionId -> {
                            PermissionListSP permissionListSP = com.skrivet.core.toolkit.CollectionUtils.findOne(permissionListSPList, data -> permissionId.equals(data.getId()));
                            if (permissionListSP != null) {
                                permissionList.add(permissionListSP.getValue());
                            }
                        });
                    }
                    result.put(resourceListSP.getValue(), permissionList);
                }
            });
        }
        return result;
    }
}
