package com.skrivet.supports.data.service.core.tree;


import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.entity.PageList;
import com.skrivet.supports.data.service.core.tree.entity.add.TreeAddSQ;
import com.skrivet.supports.data.service.core.tree.entity.select.*;
import com.skrivet.supports.data.service.core.tree.entity.update.TreeUpdateSQ;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 树结构数据的服务类
 *
 * @author PolarLoves
 */
public interface TreeService {
    public String insert(@Valid TreeAddSQ entity, LoginUser loginUser);

    public Long deleteById(@NotNull(message = "树结构编号不能为空") String id, LoginUser loginUser);

    public Long deleteMultiById(@NotNull(message = "树结构编号不能为空") String[] ids, LoginUser loginUser);

    public Long update(@Valid TreeUpdateSQ entity, LoginUser loginUser);

    public TreeDetailSP selectOneById(@NotNull(message = "树结构编号不能为空") String id, LoginUser loginUser);

    public List<TreeListSP> selectListByGroupId(@NotNull(message = "组编号不能为空") String groupId, LoginUser loginUser);

    public PageList<TreeListSP> selectPageList(@Valid TreeSelectPageSQ condition, LoginUser loginUser);

    public List<TreeGroupSP> groups(LoginUser loginUser);

}