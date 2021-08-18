package com.skrivet.supports.realm.dao.mybatis.dept;

import com.skrivet.plugins.database.core.annotations.Dao;
import com.skrivet.supports.realm.dao.core.dept.entity.select.DeptDetailDP;
import org.apache.ibatis.annotations.Param;

@Dao
public interface DeptDao extends com.skrivet.supports.realm.dao.core.dept.DeptDao {

    public Long deleteById(@Param("id") String id);

    public DeptDetailDP selectOneById(@Param("id") String id);
}
