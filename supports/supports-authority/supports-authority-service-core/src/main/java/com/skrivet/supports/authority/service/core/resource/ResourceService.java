package com.skrivet.supports.authority.service.core.resource;

import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.entity.PageList;
import com.skrivet.supports.authority.service.core.resource.entity.add.ResourceAddSQ;
import com.skrivet.supports.authority.service.core.resource.entity.select.ResourceDetailSP;
import com.skrivet.supports.authority.service.core.resource.entity.select.ResourceListSP;
import com.skrivet.supports.authority.service.core.resource.entity.select.ResourceSelectPageSQ;
import com.skrivet.supports.authority.service.core.resource.entity.update.ResourceUpdateSQ;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface ResourceService {
    public String insert(@Valid ResourceAddSQ entity, LoginUser loginUser);

    public Long deleteById(@NotNull(message = "资源编号不能为空") String id, LoginUser loginUser);

    public Long deleteMultiById(@NotNull(message = "资源编号不能为空") String[] ids, LoginUser loginUser);

    public Long update(@Valid ResourceUpdateSQ entity, LoginUser loginUser);

    public ResourceDetailSP selectOneById(@NotNull(message = "资源编号不能为空") String id, LoginUser loginUser);

    public List<ResourceListSP> selectList(LoginUser loginUser);

    public PageList<ResourceListSP> selectPageList(@Valid ResourceSelectPageSQ condition, LoginUser loginUser);


}
