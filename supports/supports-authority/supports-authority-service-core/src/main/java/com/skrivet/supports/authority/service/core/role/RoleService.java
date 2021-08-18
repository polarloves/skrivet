package com.skrivet.supports.authority.service.core.role;

import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.supports.authority.service.core.role.entity.add.RoleAddSQ;
import com.skrivet.supports.authority.service.core.role.entity.select.RoleDetailSP;
import com.skrivet.supports.authority.service.core.role.entity.select.RoleListSP;
import com.skrivet.supports.authority.service.core.role.entity.update.RoleUpdateSQ;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface RoleService {
    public String insert(@Valid RoleAddSQ entity, LoginUser loginUser);

    public Long deleteById(@NotNull(message = "角色编号不能为空") String id, LoginUser loginUser);

    public Long deleteMultiById(@NotNull(message = "角色编号不能为空") String[] ids, LoginUser loginUser);

    public Long update(@Valid RoleUpdateSQ entity, LoginUser loginUser);

    public RoleDetailSP selectOneById(@NotNull(message = "角色编号不能为空") String id, LoginUser loginUser);

    public List<RoleListSP> selectList(LoginUser loginUser);

    public List<String> selectUserRoleIds(@NotNull(message = "角色编号不能为空") String userId, LoginUser loginUser);

    public void updateUserRoles(@NotNull(message = "用户编号不能为空") String userId, String[] roleIds, LoginUser loginUser);

}
