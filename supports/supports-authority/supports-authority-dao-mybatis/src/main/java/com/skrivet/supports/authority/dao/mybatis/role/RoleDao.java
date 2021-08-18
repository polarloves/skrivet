package com.skrivet.supports.authority.dao.mybatis.role;

import com.skrivet.plugins.database.core.annotations.Dao;
import com.skrivet.supports.authority.dao.core.role.entity.select.RoleDetailDP;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Dao
public interface RoleDao extends com.skrivet.supports.authority.dao.core.role.RoleDao {
    @Override
    Long deleteById(@Param("id") String id);

    @Override
    RoleDetailDP selectOneById(@Param("id") String id);

    @Override
    RoleDetailDP selectOneByValue(@Param("value") String value);

    @Override
    List<String> selectUserRoleIds(@Param("userId") String userId);

    @Override
    Long deleteUserRolesByUserId(@Param("userId") String userId);
}
