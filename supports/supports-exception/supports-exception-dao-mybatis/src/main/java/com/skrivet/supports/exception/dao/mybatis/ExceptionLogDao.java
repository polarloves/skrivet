package com.skrivet.supports.exception.dao.mybatis;

import com.skrivet.plugins.database.core.annotations.Dao;
import com.skrivet.plugins.database.mybatis.annotations.Page;
import com.skrivet.supports.exception.dao.core.entity.select.ExceptionLogDetailDP;
import com.skrivet.supports.exception.dao.core.entity.	select.ExceptionLogListDP;
import com.skrivet.supports.exception.dao.core.entity.select.ExceptionLogSelectPageDQ;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Dao
public interface ExceptionLogDao extends com.skrivet.supports.exception.dao.core.ExceptionLogDao {
    public Long deleteById(@Param("id") String id);

    public ExceptionLogDetailDP selectOneById(@Param("id") String id);

    @Page
    public List<ExceptionLogListDP> selectPageList(ExceptionLogSelectPageDQ condition);
}