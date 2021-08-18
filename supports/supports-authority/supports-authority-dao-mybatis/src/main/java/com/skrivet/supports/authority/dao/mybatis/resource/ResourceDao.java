package com.skrivet.supports.authority.dao.mybatis.resource;


import com.skrivet.plugins.database.core.annotations.Dao;
import com.skrivet.plugins.database.mybatis.annotations.Page;
import com.skrivet.supports.authority.dao.core.resource.entity.select.ResourceDetailDP;
import com.skrivet.supports.authority.dao.core.resource.entity.select.ResourceListDP;
import com.skrivet.supports.authority.dao.core.resource.entity.select.ResourceSelectPageDQ;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Dao
public interface ResourceDao extends com.skrivet.supports.authority.dao.core.resource.ResourceDao {

    public Long deleteById(@Param("id") String id);

    @Page
    List<ResourceListDP> selectPageList(ResourceSelectPageDQ condition);


}
