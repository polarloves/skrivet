package com.skrivet.supports.authority.service.impl;

import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.exception.BizExp;
import com.skrivet.plugins.security.core.entity.PermissionSet;
import com.skrivet.plugins.security.core.service.PermissionSetService;
import com.skrivet.supports.authority.service.core.permission.PermissionService;
import com.skrivet.supports.authority.service.core.permission.entity.select.PermissionListSP;
import com.skrivet.supports.authority.service.core.role.RoleService;
import com.skrivet.supports.authority.service.core.role.entity.select.RoleListSP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("permissionSetService")
public class PermissionSetServiceImpl implements PermissionSetService {
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleService roleService;


    @Override
    public PermissionSet selectPermissionSetByUserId(String userId) throws BizExp {
        PermissionSet permissionSet = new PermissionSet();
        List<PermissionListSP> permissionListSPList = permissionService.selectList(LoginUser.IGNORED);
        List<RoleListSP> roleListSPS = roleService.selectList(LoginUser.IGNORED);
        Set<String> permissions = new HashSet<>();
        Set<String> roles = new HashSet<>();
        List<String> roleIds = roleService.selectUserRoleIds(userId, LoginUser.IGNORED);
        Set<String> permissionIds = new HashSet<>();
        if (!CollectionUtils.isEmpty(roleIds)) {
            roleIds.stream().forEach(roleId -> {
                RoleListSP roleListSP = com.skrivet.core.toolkit.CollectionUtils.findOne(roleListSPS, data -> roleId.equals(data.getId()));
                if (roleListSP != null) {
                    roles.add(roleListSP.getValue());
                    List<String> rolePermissions = permissionService.selectRolePermissionIds(roleId, LoginUser.IGNORED);
                    if (!CollectionUtils.isEmpty(rolePermissions)) {
                        permissionIds.addAll(rolePermissions);
                    }
                }
            });
        }
        if (!CollectionUtils.isEmpty(permissionIds)) {
            permissionIds.stream().forEach(permissionId -> {
                PermissionListSP permissionListSP = com.skrivet.core.toolkit.CollectionUtils.findOne(permissionListSPList, data -> permissionId.equals(data.getId()));
                if (permissionListSP != null) {
                    permissions.add(permissionListSP.getValue());
                }
            });
        }
        permissionSet.setPermissions(permissions);
        permissionSet.setRoles(roles);
        return permissionSet;
    }


}
