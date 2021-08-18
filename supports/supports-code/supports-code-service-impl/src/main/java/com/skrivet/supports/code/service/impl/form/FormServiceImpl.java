package com.skrivet.supports.code.service.impl.form;

import com.skrivet.core.common.annotations.DynamicDataBase;
import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.entity.PageList;
import com.skrivet.core.common.exception.DataNotExistExp;
import com.skrivet.plugins.security.core.annotations.RequiresPermissions;
import com.skrivet.plugins.service.core.BasicService;
import com.skrivet.supports.code.dao.core.form.FormDao;
import com.skrivet.supports.code.dao.core.form.MetaDataDao;
import com.skrivet.supports.code.dao.core.form.entity.add.ColumnAddDQ;
import com.skrivet.supports.code.dao.core.form.entity.add.FormAddDQ;
import com.skrivet.supports.code.dao.core.form.entity.select.*;
import com.skrivet.supports.code.dao.core.form.entity.update.FormUpdateDQ;
import com.skrivet.supports.code.service.core.form.FormService;
import com.skrivet.supports.code.service.core.form.entity.add.FormAddSQ;
import com.skrivet.supports.code.service.core.form.entity.select.*;
import com.skrivet.supports.code.service.core.form.entity.update.ColumnSQ;
import com.skrivet.supports.code.service.core.form.entity.update.ColumnUpdateSQ;
import com.skrivet.supports.code.service.core.form.entity.update.FormUpdateSQ;
import com.skrivet.supports.code.service.impl.form.handler.FormParameterHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("formService")
@DynamicDataBase
public class FormServiceImpl extends BasicService implements FormService {
    @Autowired
    private FormDao formDao;
    @Autowired
    private MetaDataDao metaDataDao;
    @Autowired
    private List<FormParameterHandler> formParameterHandlers;

    @PostConstruct
    public void init() {
        formParameterHandlers.sort((o1, o2) -> o1.order() - o2.order());
    }

    @RequiresPermissions({"skrivet:form:basic", "skrivet:form:add", "skrivet:backstage"})
    @Transactional("codeTM")
    @Override
    public String insert(FormAddSQ entity, LoginUser loginUser) {
        FormAddDQ formAddDQ = entityConvert.convert(entity, FormAddDQ.class);
        formAddDQ.setId(idGenerator.generate());
        formDao.insert(formAddDQ);
        return formAddDQ.getId();
    }

    @RequiresPermissions({"skrivet:form:basic", "skrivet:form:delete", "skrivet:backstage"})
    @Transactional("codeTM")
    @Override
    public Long deleteById(String id, LoginUser loginUser) {
        formDao.deleteColumnByFormId(id);
        return formDao.deleteById(id);
    }

    @RequiresPermissions({"skrivet:form:basic", "skrivet:form:delete", "skrivet:backstage"})
    @Transactional("codeTM")
    @Override
    public Long deleteMultiById(String[] ids, LoginUser loginUser) {
        FormService current = current(FormService.class);
        long result = 0;
        for (String id : ids) {
            result = result + current.deleteById(id, loginUser);
        }
        return result;
    }

    @RequiresPermissions({"skrivet:form:basic", "skrivet:form:update", "skrivet:backstage"})
    @Transactional("codeTM")
    @Override
    public Long update(FormUpdateSQ entity, LoginUser loginUser) {
        return formDao.update(entityConvert.convert(entity, FormUpdateDQ.class));
    }

    @RequiresPermissions({"skrivet:form:basic", "skrivet:form:update", "skrivet:backstage"})
    @Transactional("codeTM")
    @Override
    public Long updateColumn(ColumnUpdateSQ entity, LoginUser loginUser) {
        formDao.deleteColumnByFormId(entity.getFormId());
        if (!CollectionUtils.isEmpty(entity.getColumns())) {
            int i = 1;
            for (ColumnSQ columnSQ : entity.getColumns()) {
                ColumnAddDQ parameter = entityConvert.convert(columnSQ, ColumnAddDQ.class);
                parameter.setFormId(entity.getFormId());
                parameter.setId(idGenerator.generate());
                parameter.setOrderNum(i);
                formDao.insertColumn(parameter);
                i++;
            }
        }
        return entity.getColumns().size() + 0l;
    }

    @RequiresPermissions({"skrivet:form:basic", "skrivet:form:list", "skrivet:backstage"})
    @Override
    public PageList<FormListSP> selectPageList(FormSelectPageSQ condition, LoginUser loginUser) {
        PageList<FormListSP> page = new PageList<FormListSP>();
        FormSelectPageDQ request = entityConvert.convert(condition, FormSelectPageDQ.class);
        page.setCount(formDao.selectPageCount(request));
        page.setData(entityConvert.convertList(formDao.selectPageList(request), FormListSP.class));
        return page;
    }

    @RequiresPermissions({"skrivet:form:basic", "skrivet:form:detail", "skrivet:backstage"})
    @Override
    public FormDetailSP selectOneById(String id, LoginUser loginUser) {
        FormDetailSP formDetailSP = entityConvert.convert(formDao.selectOneById(id), FormDetailSP.class);
        return formDetailSP;
    }

    @RequiresPermissions({"skrivet:form:basic", "skrivet:form:list", "skrivet:backstage"})
    @Override
    public List<FormDictSP> selectFormDict(LoginUser loginUser) {
        return entityConvert.convertList(formDao.selectFormDict(), FormDictSP.class);
    }

    @RequiresPermissions({"skrivet:form:basic", "skrivet:form:list", "skrivet:backstage"})
    @Override
    public List<FormListSP> selectList(LoginUser loginUser) {
        return entityConvert.convertList(formDao.selectList(), FormListSP.class);
    }

    @RequiresPermissions({"skrivet:form:basic", "skrivet:form:detail", "skrivet:backstage"})
    @Override
    public List<ColumnSP> selectColumns(String id, LoginUser loginUser) {
        return entityConvert.convertList(formDao.selectColumnsByFormId(id), ColumnSP.class);
    }

    @RequiresPermissions({"skrivet:form:basic", "skrivet:form:detail", "skrivet:backstage"})
    @Override
    public Map<String, Object> obtainFormData(String id, LoginUser loginUser) {
        FormDetailDP formDetailDP = formDao.selectOneById(id);
        if (formDetailDP == null) {
            throw new DataNotExistExp();
        }
        if (formDetailDP.getFormType().equals("4")) {
            //对于子表而言，找到父表，在找到子表
            FormDetailDP parent = formDao.selectOneById(formDetailDP.getParentCode());
            Map<String, Object> all = obtainFormData(parent);
            List<Map<String, Object>> children = (List<Map<String, Object>>) all.get("children");
            for (Map<String, Object> item : children) {
                if (item.get("name").equals(formDetailDP.getModuleName())) {
                    return item;
                }
            }
            return null;
        }
        return obtainFormData(formDetailDP);
    }

    @RequiresPermissions({"skrivet:form:basic", "skrivet:form:update", "skrivet:backstage"})
    @Transactional("codeTM")
    @Override
    public boolean importTable(String name, LoginUser loginUser) {
        TableMetaDataDP tableMetaDataDP = metaDataDao.findMetaData(name);
        FormAddSQ formAddSQ = new FormAddSQ();
        formAddSQ.setFormName(tableMetaDataDP.getComment());
        formAddSQ.setDbTableName(tableMetaDataDP.getName());
        formAddSQ.setPackageName("com.skrivet.modules");
        formAddSQ.setAccessPath("/modules/" + tableMetaDataDP.getModuleName());
        formAddSQ.setFormType(1);
        formAddSQ.setTemplate("COMMON");
        formAddSQ.setProjectId(tableMetaDataDP.getModuleName());
        formAddSQ.setGroupId("skrivet-module");
        formAddSQ.setModuleName(tableMetaDataDP.getModuleName());
        formAddSQ.setAppendRemark(true);
        formAddSQ.setAppendRemarkCount(10);
        formAddSQ.setVersion("1.0.0-RELEASE");
        formAddSQ.setSkrivetVersion("1.0.0-RELEASE");
        String id = insert(formAddSQ, LoginUser.IGNORED);
        int i = 1;
        for (TableColumnMetaDataDP column : tableMetaDataDP.getColumns()) {
            ColumnAddDQ parameter = new ColumnAddDQ();
            parameter.setId(idGenerator.generate());
            parameter.setFormId(id);
            parameter.setOrderNum(i);
            parameter.setJavaName(column.getJavaName());
            parameter.setJavaType(column.getJavaType());
            parameter.setDbColumnName(column.getName());
            parameter.setFieldName(column.getRemark());
            parameter.setMatchType("1");
            parameter.setViewType("input");
            parameter.setPrimaryKey(column.isPrimary());
            Map<String, Object> validate = new HashMap<>(), extInfo = new HashMap<>(), display = new HashMap<>();
            validate.put("required", !column.isNullAble());
            validate.put("number", false);
            validate.put("phone", false);
            validate.put("email", false);
            validate.put("length", "0-" + column.getLength());
            extInfo.put("width", 12);
            display.put("query", true);
            display.put("sort", true);
            display.put("add", true);
            display.put("update", true);
            display.put("list", true);
            display.put("detail", true);
            display.put("listReturn", true);
            display.put("detailReturn", true);
            parameter.setValidate(validate);
            parameter.setDisplay(display);
            parameter.setExtInfo(extInfo);
            formDao.insertColumn(parameter);
            i++;
        }
        return true;
    }

    @Transactional("codeTM")
    @Override
    public boolean initTable(String id) {
        FormDetailDP formDetailDP = formDao.selectOneById(id);
        TableMetaDataDP tableMetaDataDP = metaDataDao.findMetaData(formDetailDP.getDbTableName());
        if (formDetailDP.isAppendRemark()) {
            //添加备注...
            boolean exist = false;
            for (TableColumnMetaDataDP tableColumnMetaDataDP : tableMetaDataDP.getColumns()) {
                if (tableColumnMetaDataDP.getName().equalsIgnoreCase("REMARK1")) {
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                //添加备注字段...
                for (int i = 1; i < 11; i++) {
                    metaDataDao.addColumn(formDetailDP.getDbTableName(), "REMARK" + i, 1, "备用字段" + i, 200);
                }

            }
        }
        if (formDetailDP.isAppendOperator()) {
            //添加操作信息
            boolean exist = false;
            for (TableColumnMetaDataDP tableColumnMetaDataDP : tableMetaDataDP.getColumns()) {
                if (tableColumnMetaDataDP.getName().equalsIgnoreCase("CREATE_BY")) {
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                metaDataDao.addColumn(formDetailDP.getDbTableName(), "CREATE_BY", 1, "创建人账号", 50);
                metaDataDao.addColumn(formDetailDP.getDbTableName(), "UPDATE_BY", 1, "修改人账号", 50);
                metaDataDao.addColumn(formDetailDP.getDbTableName(), "CREATE_DATE", 2, "创建时间", 0);
                metaDataDao.addColumn(formDetailDP.getDbTableName(), "UPDATE_DATE", 2, "修改时间", 0);
            }
        }
        return true;
    }

    private Map<String, Object> obtainFormData(FormDetailDP formDetailDP) {
        Map<String, Object> result = beanToMap(formDetailDP);
        result.put("columns", listToMap(formDao.selectColumnsByFormId(formDetailDP.getId())));
        formParameterHandlers.forEach(formParameterHandler -> formParameterHandler.process(result));
        if (formDetailDP.getFormType().equals("3")) {
            List<Map<String, Object>> childrenMaps = new ArrayList<>();
            List<FormListDP> children = formDao.children(formDetailDP.getId());
            children.forEach(child -> {
                FormDetailDP childDetail = formDao.selectOneById(child.getId());
                Map<String, Object> childrenMap = beanToMap(childDetail);
                childrenMap.put("parent", copyParentMap(result));
                childrenMap.put("columns", listToMap(formDao.selectColumnsByFormId(child.getId())));
                formParameterHandlers.forEach(formParameterHandler -> formParameterHandler.process(childrenMap));
                childrenMaps.add(childrenMap);
            });
            result.put("children", childrenMaps);
        }
        return result;
    }

    private Map<String, Object> copyParentMap(Map<String, Object> parent) {
        Map<String, Object> result = new HashMap<>();
        for (String key : parent.keySet()) {
            if (!key.equals("children")) {
                result.put(key, parent.get(key));
            }
        }
        return result;
    }

    public static <T> List<Map<String, Object>> listToMap(List<T> obj) {
        List<Map<String, Object>> result = new ArrayList<>(obj.size());
        obj.forEach(o -> result.add(beanToMap(o)));
        return result;
    }

    public static Map<String, Object> beanToMap(Object obj) {
        if (obj == null) {
            return new HashMap<String, Object>();
        }
        Map<String, Object> params = new HashMap<String, Object>(0);
        ReflectionUtils.doWithFields(obj.getClass(), field -> {
            ReflectionUtils.makeAccessible(field);
            params.put(field.getName(), field.get(obj));
        });
        return params;
    }
}
