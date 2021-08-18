package com.skrivet.supports.data.dao.mybatis.system;

import com.skrivet.plugins.database.core.annotations.Dao;
import com.skrivet.plugins.database.mybatis.annotations.Page;
import com.skrivet.supports.data.dao.core.system.entity.select.SystemDetailDP;
import com.skrivet.supports.data.dao.core.system.entity.select.SystemListDP;
import com.skrivet.supports.data.dao.core.system.entity.select.SystemSelectPageDQ;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Dao
public interface SystemDao extends com.skrivet.supports.data.dao.core.system.SystemDao {
    public Long deleteById(@Param("id") String id);

    public SystemDetailDP selectByGroupKey(@Param("groupId") String groupId, @Param("sysKey") String sysKey);

    public SystemDetailDP selectOneById(@Param("id") String id);
    @Page
    @Override
    public List<SystemListDP> selectPageList(SystemSelectPageDQ condition);
}
