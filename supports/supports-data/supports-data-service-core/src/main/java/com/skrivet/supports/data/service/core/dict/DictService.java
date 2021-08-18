package com.skrivet.supports.data.service.core.dict;


import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.entity.PageList;
import com.skrivet.supports.data.service.core.dict.entity.add.DictAddSQ;
import com.skrivet.supports.data.service.core.dict.entity.select.*;
import com.skrivet.supports.data.service.core.dict.entity.update.DictUpdateSQ;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 字典数据的服务类
 *
 * @author PolarLoves
 */
public interface DictService {
    public String insert(@Valid DictAddSQ entity, LoginUser loginUser);

    public Long deleteById(@NotNull(message = "字典编号不能为空") String id, LoginUser loginUser);

    public Long deleteMultiById(@NotNull(message = "字典编号不能为空") String[] ids, LoginUser loginUser);

    public Long update(@Valid DictUpdateSQ entity, LoginUser loginUser);

    public DictDetailSP selectOneById(@NotNull(message = "字典编号不能为空") String id, LoginUser loginUser);

    public List<DictListSP> selectListByGroupId(@NotNull(message = "组编号不能为空") String groupId, LoginUser loginUser);

    public PageList<DictListSP> selectPageList(DictSelectPageSQ condition, LoginUser loginUser);

    public List<DictGroupSP> groups(LoginUser loginUser);

}