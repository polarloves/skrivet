package com.skrivet.supports.authority.dao.core.resource;


import com.skrivet.supports.authority.dao.core.resource.entity.add.ResourceAddDQ;
import com.skrivet.supports.authority.dao.core.resource.entity.select.ResourceDetailDP;
import com.skrivet.supports.authority.dao.core.resource.entity.select.ResourceListDP;
import com.skrivet.supports.authority.dao.core.resource.entity.select.ResourceSelectPageDQ;
import com.skrivet.supports.authority.dao.core.resource.entity.update.ResourceUpdateDQ;

import java.util.List;


public interface ResourceDao {
    public void insert(ResourceAddDQ entity);

    public Long deleteById(String id);

    public Long update(ResourceUpdateDQ entity);

    public ResourceDetailDP selectOneById(String id);

    public ResourceDetailDP selectOneByValue(String value);

    public List<ResourceListDP> selectList();

    public List<ResourceListDP> selectPageList(ResourceSelectPageDQ condition);

    public Long selectPageCount(ResourceSelectPageDQ condition);

  }
