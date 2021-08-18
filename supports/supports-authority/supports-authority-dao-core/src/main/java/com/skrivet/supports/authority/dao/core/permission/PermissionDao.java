package com.skrivet.supports.authority.dao.core.permission;

import com.skrivet.supports.authority.dao.core.permission.entity.add.PermissionAddDQ;
import com.skrivet.supports.authority.dao.core.permission.entity.add.ResourcePermissionAddDQ;
import com.skrivet.supports.authority.dao.core.permission.entity.add.RolePermissionAddDQ;
import com.skrivet.supports.authority.dao.core.permission.entity.select.PermissionDetailDP;
import com.skrivet.supports.authority.dao.core.permission.entity.select.PermissionListDP;
import com.skrivet.supports.authority.dao.core.permission.entity.update.PermissionUpdateDQ;

import java.util.List;


public interface PermissionDao {
    public void insert(PermissionAddDQ entity);

    public Long deleteById(String id);

    public Long update(PermissionUpdateDQ entity);

    public PermissionDetailDP selectOneById(String id);

    public PermissionDetailDP selectOneByValue(String value);

    public List<PermissionListDP> selectList();

    public List<String> selectRolePermissionIds(String roleId);

    public Long deleteRolePermissionsByRoleId(String roleId);

    public void insertRolePermissions(RolePermissionAddDQ condition);

    public List<String> selectResourcePermissionIds(String resourceId);

    public Long deleteResourcePermissionByResourceId(String resourceId);

    public void insertResourcePermissions(ResourcePermissionAddDQ condition);

}
