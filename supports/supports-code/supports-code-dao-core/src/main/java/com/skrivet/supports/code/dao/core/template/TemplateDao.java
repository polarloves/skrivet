package com.skrivet.supports.code.dao.core.template;

import com.skrivet.supports.code.dao.core.template.entity.add.TemplateAddDQ;
import com.skrivet.supports.code.dao.core.template.entity.select.TemplateDetailDP;
import com.skrivet.supports.code.dao.core.template.entity.select.TemplateGroupDP;
import com.skrivet.supports.code.dao.core.template.entity.select.TemplateListDP;
import com.skrivet.supports.code.dao.core.template.entity.select.TemplateSelectPageDQ;
import com.skrivet.supports.code.dao.core.template.entity.update.TemplateUpdateDQ;

import java.util.List;

public interface TemplateDao {
    public void insert(TemplateAddDQ entity);

    public Long deleteById(String id);

    public Long update(TemplateUpdateDQ entity);

    public Long updateTemplateData(String id, String data);

    public TemplateDetailDP selectOneById(String id);

    public List<TemplateListDP> selectPageList(TemplateSelectPageDQ condition);


    public Long selectPageCount(TemplateSelectPageDQ condition);

    public List<TemplateGroupDP> groups();

    public List<TemplateListDP> selectByGroupId(String groupId);

}
