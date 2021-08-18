package com.skrivet.supports.data.dao.mybatis.dict;

import com.skrivet.plugins.database.core.annotations.Dao;
import com.skrivet.plugins.database.mybatis.annotations.Page;
import com.skrivet.supports.data.dao.core.dict.entity.select.DictDetailDP;
import com.skrivet.supports.data.dao.core.dict.entity.select.DictListDP;
import com.skrivet.supports.data.dao.core.dict.entity.select.DictSelectPageDQ;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Dao
public interface DictDao extends com.skrivet.supports.data.dao.core.dict.DictDao {
    public Long deleteById(@Param("id") String id);

    public DictDetailDP selectOneById(@Param("id") String id);

    public List<DictListDP> selectListByGroupId(@Param("groupId") String groupId);

    @Page
    @Override
    public List<DictListDP> selectPageList(DictSelectPageDQ condition);
}
