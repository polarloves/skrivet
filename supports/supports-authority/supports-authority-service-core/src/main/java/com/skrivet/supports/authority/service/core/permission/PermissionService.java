package com.skrivet.supports.authority.service.core.permission;

import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.supports.authority.service.core.permission.entity.add.PermissionAddSQ;
import com.skrivet.supports.authority.service.core.permission.entity.select.PermissionDetailSP;
import com.skrivet.supports.authority.service.core.permission.entity.select.PermissionListSP;
import com.skrivet.supports.authority.service.core.permission.entity.update.PermissionUpdateSQ;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface PermissionService {
    public String insert(@Valid PermissionAddSQ entity, LoginUser loginUser);

    public Long deleteById(@NotNull(message = "权限编号不能为空") String id, LoginUser loginUser);

    public Long deleteMultiById(@NotNull(message = "权限编号不能为空") String[] ids, LoginUser loginUser);

    public Long update(@Valid PermissionUpdateSQ entity, LoginUser loginUser);

    public PermissionDetailSP selectOneById(@NotNull(message = "权限编号不能为空") String id, LoginUser loginUser);

    public List<PermissionListSP> selectList(LoginUser loginUser);

    public List<String> selectRolePermissionIds(@NotNull(message = "权限编号不能为空") String roleId, LoginUser loginUser);

    public void updateRolePermissions(@NotNull(message = "角色编号不能为空") String roleId, String[] permissionIds, LoginUser loginUser);

    public List<String> selectResourcePermissionIds(@NotNull(message = "资源编号不能为空") String resourceId, LoginUser loginUser);

    public void updateResourcePermissions(@NotNull(message = "资源编号不能为空") String resourceId, String[] permissionIds, LoginUser loginUser);

    public void reloadResourcePermissions();
}
