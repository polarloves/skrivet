package com.skrivet.supports.data.dao.core.tree;


import com.skrivet.supports.data.dao.core.tree.entity.add.TreeAddDQ;
import com.skrivet.supports.data.dao.core.tree.entity.select.*;
import com.skrivet.supports.data.dao.core.tree.entity.update.TreeUpdateDQ;

import java.util.List;

public interface TreeDao {
    public void insert(TreeAddDQ entity);

    public Long deleteById(String id);

    public Long update(TreeUpdateDQ entity);

    public TreeDetailDP selectOneById(String id);

    public TreeDetailDP selectOneByValue(String groupId, String value);

    public List<TreeListDP> selectPageList(TreeSelectPageDQ condition);

    public List<TreeListDP> selectListByGroupId(String groupId);

    public Long selectPageCount(TreeSelectPageDQ condition);

    public List<TreeGroupDP> groups();
}
