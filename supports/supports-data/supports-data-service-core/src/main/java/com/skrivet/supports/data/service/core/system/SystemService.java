package com.skrivet.supports.data.service.core.system;


import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.entity.PageList;
import com.skrivet.supports.data.service.core.system.entity.add.SystemAddSQ;
import com.skrivet.supports.data.service.core.system.entity.select.*;
import com.skrivet.supports.data.service.core.system.entity.update.SystemUpdateSQ;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 系统的服务类
 *
 * @author PolarLoves
 */
public interface SystemService {
    public String insert(@Valid SystemAddSQ entity, LoginUser loginUser);

    public Long deleteById(@NotNull(message = "系统配置编号不能为空") String id, LoginUser loginUser);

    public Long deleteMultiById(@NotNull(message = "系统配置编号不能为空") String[] ids, LoginUser loginUser);

    public Long update(@Valid SystemUpdateSQ entity, LoginUser loginUser);

    public SystemDetailSP selectOneById(@NotNull(message = "系统配置编号不能为空") String id, LoginUser loginUser);

    public List<SystemListSP> selectListByGroupId(@NotNull(message = "组编号不能为空") String groupId, LoginUser loginUser);

    public SystemDetailSP selectByKeyGroupId(String groupId,  String key);

    public PageList<SystemListSP> selectPageList(@Valid SystemSelectPageSQ condition, LoginUser loginUser);

    public List<SystemGroupSP> groups(LoginUser loginUser);

}