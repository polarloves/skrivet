package com.skrivet.supports.authority.dao.core.role;

import com.skrivet.supports.authority.dao.core.role.entity.add.RoleAddDQ;
import com.skrivet.supports.authority.dao.core.role.entity.add.UserRoleAddDQ;
import com.skrivet.supports.authority.dao.core.role.entity.select.RoleDetailDP;
import com.skrivet.supports.authority.dao.core.role.entity.select.RoleListDP;
import com.skrivet.supports.authority.dao.core.role.entity.update.RoleUpdateDQ;

import java.util.List;

public interface RoleDao {
    public void insert(RoleAddDQ entity);

    public Long deleteById(String id);

    public Long update(RoleUpdateDQ entity);

    public RoleDetailDP selectOneById(String id);

    public RoleDetailDP selectOneByValue(String value);

    public List<RoleListDP> selectList();

    public List<String> selectUserRoleIds(String userId);

    public Long deleteUserRolesByUserId(String userId);

    public void insertUserRoles(UserRoleAddDQ condition);
}
