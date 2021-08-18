package com.skrivet.supports.code.service.impl.template;

import com.skrivet.core.common.annotations.DynamicDataBase;
import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.entity.PageList;
import com.skrivet.core.common.exception.IgnoreLoggedExp;
import com.skrivet.core.common.exception.UnknownExp;
import com.skrivet.plugins.security.core.annotations.RequiresPermissions;
import com.skrivet.plugins.service.core.BasicService;
import com.skrivet.supports.code.dao.core.template.TemplateDao;
import com.skrivet.supports.code.dao.core.template.entity.add.TemplateAddDQ;
import com.skrivet.supports.code.dao.core.template.entity.select.TemplateSelectPageDQ;
import com.skrivet.supports.code.dao.core.template.entity.update.TemplateUpdateDQ;
import com.skrivet.supports.code.service.core.template.TemplateService;
import com.skrivet.supports.code.service.core.template.entity.add.TemplateAddSQ;
import com.skrivet.supports.code.service.core.template.entity.select.TemplateDetailSP;
import com.skrivet.supports.code.service.core.template.entity.select.TemplateGroupSP;
import com.skrivet.supports.code.service.core.template.entity.select.TemplateListSP;
import com.skrivet.supports.code.service.core.template.entity.select.TemplateSelectPageSQ;
import com.skrivet.supports.code.service.core.template.entity.update.TemplateUpdateSQ;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.StringWriter;
import java.util.List;
import java.util.Map;

@Service("templateService")
@DynamicDataBase
public class TemplateServiceImpl extends BasicService implements TemplateService {
    @Autowired
    private TemplateDao templateDao;

    @RequiresPermissions({"skrivet:template:basic", "skrivet:template:add", "skrivet:backstage"})
    @Transactional("codeTM")
    @Override
    public String insert(TemplateAddSQ entity, LoginUser loginUser) {
        TemplateAddDQ param = entityConvert.convert(entity, TemplateAddDQ.class);
        param.setId(idGenerator.generate());
        templateDao.insert(param);
        return param.getId();
    }

    @RequiresPermissions({"skrivet:template:basic", "skrivet:template:delete", "skrivet:backstage"})
    @Transactional("codeTM")
    @Override
    public Long deleteById(String id, LoginUser loginUser) {
        return templateDao.deleteById(id);
    }

    @RequiresPermissions({"skrivet:template:basic", "skrivet:template:delete", "skrivet:backstage"})
    @Transactional("codeTM")
    @Override
    public Long deleteMultiById(String[] ids, LoginUser loginUser) {
        long result = 0;
        for (String id : ids) {
            result = result + deleteById(id, loginUser);
        }
        return result;
    }

    @RequiresPermissions({"skrivet:template:basic", "skrivet:template:update", "skrivet:backstage"})
    @Transactional("codeTM")
    @Override
    public Long update(TemplateUpdateSQ entity, LoginUser loginUser) {
        return templateDao.update(entityConvert.convert(entity, TemplateUpdateDQ.class));
    }

    @RequiresPermissions({"skrivet:template:basic", "skrivet:template:update", "skrivet:backstage"})
    @Transactional("codeTM")
    @Override
    public Long updateTemplateData(String id, String data) {
        return templateDao.updateTemplateData(id, data);
    }

    @RequiresPermissions({"skrivet:template:basic", "skrivet:template:list", "skrivet:backstage"})
    @Override
    public PageList<TemplateListSP> selectPageList(TemplateSelectPageSQ condition, LoginUser loginUser) {
        PageList<TemplateListSP> page = new PageList<TemplateListSP>();
        TemplateSelectPageDQ request = entityConvert.convert(condition, TemplateSelectPageDQ.class);
        page.setCount(templateDao.selectPageCount(request));
        page.setData(entityConvert.convertList(templateDao.selectPageList(request), TemplateListSP.class));
        return page;
    }

    @RequiresPermissions({"skrivet:template:basic", "skrivet:template:list", "skrivet:backstage"})
    @Override
    public List<TemplateListSP> selectByGroupId(String groupId, LoginUser loginUser) {
        return entityConvert.convertList(templateDao.selectByGroupId(groupId), TemplateListSP.class);
    }

    @RequiresPermissions({"skrivet:template:basic", "skrivet:template:detail", "skrivet:backstage"})
    @Override
    public TemplateDetailSP selectOneById(String id, LoginUser loginUser) {
        return entityConvert.convert(templateDao.selectOneById(id), TemplateDetailSP.class);
    }

    @RequiresPermissions({"skrivet:template:basic", "skrivet:template:list", "skrivet:backstage"})
    @Override
    public List<TemplateGroupSP> groups(LoginUser loginUser) {
        return entityConvert.convertList(templateDao.groups(), TemplateGroupSP.class);
    }

    @Override
    public String renderTemplate(Map<String, Object> map, String templateData, LoginUser loginUser) {
        try {
            Configuration cfg = new Configuration();
            StringTemplateLoader stringLoader = new StringTemplateLoader();
            stringLoader.putTemplate("template", templateData);
            cfg.setURLEscapingCharset("UTF-8");
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateLoader(stringLoader);
            Template template = cfg.getTemplate("template", "utf-8");
            template.setEncoding("utf-8");
            StringWriter writer = new StringWriter();
            template.process(map, writer);
            writer.close();
            return writer.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
