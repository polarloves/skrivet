package com.skrivet.supports.data.dao.mybatis.tree;

import com.skrivet.plugins.database.core.annotations.Dao;
import com.skrivet.plugins.database.mybatis.annotations.Page;

import com.skrivet.supports.data.dao.core.tree.entity.select.TreeDetailDP;
import com.skrivet.supports.data.dao.core.tree.entity.select.TreeListDP;
import com.skrivet.supports.data.dao.core.tree.entity.select.TreeSelectPageDQ;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Dao
public interface TreeDao extends com.skrivet.supports.data.dao.core.tree.TreeDao {
    public Long deleteById(@Param("id") String id);

    public TreeDetailDP selectOneById(@Param("id") String id);

    public List<TreeListDP> selectListByGroupId(@Param("groupId") String groupId);

    @Page
    @Override
    public List<TreeListDP> selectPageList(TreeSelectPageDQ condition);

    @Override
    TreeDetailDP selectOneByValue(@Param("groupId") String groupId, @Param("value") String value);
}
