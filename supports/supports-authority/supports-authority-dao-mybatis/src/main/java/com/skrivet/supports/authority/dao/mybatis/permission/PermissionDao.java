package com.skrivet.supports.authority.dao.mybatis.permission;

import com.skrivet.plugins.database.core.annotations.Dao;
import com.skrivet.supports.authority.dao.core.permission.entity.select.PermissionDetailDP;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Dao
public interface PermissionDao extends com.skrivet.supports.authority.dao.core.permission.PermissionDao {

    PermissionDetailDP selectOneById(@Param("id") String id);

    PermissionDetailDP selectOneByValue(@Param("value") String value);

    Long deleteById(@Param("id") String id);

    List<String> selectRolePermissionIds(@Param("roleId") String roleId);

    Long deleteRolePermissionsByRoleId(@Param("roleId") String roleId);

    public List<String> selectResourcePermissionIds(@Param("resourceId") String resourceId);

    public Long deleteResourcePermissionByResourceId(@Param("resourceId") String resourceId);

}
