package com.skrivet.supports.code.dao.mybatis.template;

import com.skrivet.plugins.database.core.annotations.Dao;
import com.skrivet.plugins.database.mybatis.annotations.Page;
import com.skrivet.supports.code.dao.core.template.entity.select.TemplateDetailDP;
import com.skrivet.supports.code.dao.core.template.entity.select.TemplateListDP;
import com.skrivet.supports.code.dao.core.template.entity.select.TemplateSelectPageDQ;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Dao
public interface TemplateDao extends com.skrivet.supports.code.dao.core.template.TemplateDao {
    @Override
    public Long deleteById(@Param("id") String id);

    @Override
    public Long updateTemplateData(@Param("id") String id, @Param("data") String data);

    @Override
    public TemplateDetailDP selectOneById(@Param("id") String id);

    @Override
    @Page
    public List<TemplateListDP> selectPageList(TemplateSelectPageDQ condition);

    public List<TemplateListDP> selectByGroupId(@Param("groupId") String groupId);
}
