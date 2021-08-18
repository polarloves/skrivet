package com.skrivet.supports.code.web.impl.form;

import com.skrivet.core.common.annotations.DynamicDataBase;
import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.entity.PageList;
import com.skrivet.core.common.exception.DataNotExistExp;
import com.skrivet.core.toolkit.ExceptionToolkit;
import com.skrivet.plugins.security.core.annotations.RequiresPermissions;
import com.skrivet.plugins.web.impl.annotation.SkrivetDoc;
import com.skrivet.plugins.web.impl.controller.BasicController;
import com.skrivet.supports.code.service.core.form.FormService;
import com.skrivet.supports.code.service.core.form.entity.add.FormAddSQ;
import com.skrivet.supports.code.service.core.form.entity.select.FormListSP;
import com.skrivet.supports.code.service.core.form.entity.select.FormSelectPageSQ;
import com.skrivet.supports.code.service.core.form.entity.update.ColumnUpdateSQ;
import com.skrivet.supports.code.service.core.form.entity.update.FormUpdateSQ;
import com.skrivet.supports.code.service.core.template.TemplateService;
import com.skrivet.supports.code.service.core.template.entity.select.TemplateDetailSP;
import com.skrivet.supports.code.service.core.template.entity.select.TemplateListSP;
import com.skrivet.supports.code.web.core.form.entity.add.FormAddVQ;
import com.skrivet.supports.code.web.core.form.entity.select.*;
import com.skrivet.supports.code.web.core.form.entity.update.ColumnUpdateVQ;
import com.skrivet.supports.code.web.core.form.entity.update.FormUpdateVQ;
import com.skrivet.web.core.annotations.AuthMappingIgnore;
import com.skrivet.web.core.annotations.ResMsg;
import com.skrivet.web.core.entity.AuthMapping;
import com.skrivet.web.core.entity.ResponseBuilder;
import com.skrivet.web.core.entity.ResponseJson;
import io.swagger.annotations.*;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@SkrivetDoc
@Controller
@RequestMapping("/code/form")
@ApiSort(value = 14)
@DynamicDataBase
@Api(tags = "代码生成器表单")
public class FormController extends BasicController {
    @Autowired
    private FormService formService;
    @Autowired
    private TemplateService templateService;

    @ApiOperationSupport(order = 1)
    @ResMsg(tag = "表单所有接口所需权限")
    @ResponseBody
    @ApiOperation(value = "表单所有接口所需权限")
    @GetMapping("/permissionList")
    @AuthMappingIgnore
    public ResponseJson<List<AuthMapping>> permissionList() {
        return ResponseBuilder.success(authMappingList());
    }

    @ApiOperationSupport(order = 2)
    @ResMsg(tag = "添加表单")
    @ResponseBody
    @ApiOperation(value = "添加表单")
    @PutMapping("/insert")
    @RequiresPermissions({"skrivet:form:basic", "skrivet:form:add", "skrivet:backstage"})
    public ResponseJson<String> insert(@Valid @RequestBody FormAddVQ entity, LoginUser loginUser) {
        FormAddSQ parameter = entityConvert.convert(entity, FormAddSQ.class);
        String id = formService.insert(parameter, loginUser);
        return ResponseBuilder.success(id);
    }

    @ApiOperationSupport(order = 3)
    @ResMsg(tag = "删除表单")
    @ResponseBody
    @ApiOperation(value = "删除表单")
    @DeleteMapping("/delete")
    @RequiresPermissions({"skrivet:form:basic", "skrivet:form:delete", "skrivet:backstage"})
    public ResponseJson<Long> delete(
            @NotNull(message = "数据编号不能为空")
            @ApiParam(value = "数据编号", required = true)
                    String id,
            LoginUser loginUser) {
        return ResponseBuilder.success(formService.deleteById(id, loginUser));
    }


    @ApiOperationSupport(order = 4)
    @ResMsg(tag = "批量删除表单")
    @ResponseBody
    @ApiOperation(value = "批量删除表单")
    @DeleteMapping("/deleteMulti")
    @RequiresPermissions({"skrivet:form:basic", "skrivet:form:delete", "skrivet:backstage"})
    public ResponseJson<Long> deleteMulti(
            @NotNull(message = "数据编号不能为空")
            @ApiParam(value = "数据编号", required = true)
                    String[] ids, LoginUser loginUser) {
        return ResponseBuilder.success(formService.deleteMultiById(ids, loginUser));
    }

    @ApiOperationSupport(order = 5)
    @ResMsg(tag = "修改表单")
    @ResponseBody
    @ApiOperation(value = "修改表单")
    @PutMapping("/update")
    @RequiresPermissions({"skrivet:form:basic", "skrivet:form:update", "skrivet:backstage"})
    public ResponseJson<Long> update(@Valid @RequestBody FormUpdateVQ entity, LoginUser loginUser) {
        FormUpdateSQ parameter = entityConvert.convert(entity, FormUpdateSQ.class);
        return ResponseBuilder.success(formService.update(parameter, loginUser));
    }

    @ApiOperationSupport(order = 6)
    @ResMsg(tag = "修改表单列")
    @ResponseBody
    @ApiOperation(value = "修改表单列")
    @PutMapping("/updateColumn")
    @RequiresPermissions({"skrivet:form:basic", "skrivet:form:update", "skrivet:backstage"})
    public ResponseJson<Long> updateColumn(@Valid @RequestBody ColumnUpdateVQ entity, LoginUser loginUser) {
        ColumnUpdateSQ parameter = entityConvert.convert(entity, ColumnUpdateSQ.class);
        return ResponseBuilder.success(formService.updateColumn(parameter, loginUser));
    }

    @ApiOperationSupport(order = 7)
    @ResMsg(tag = "表单分页列表")
    @ResponseBody
    @ApiOperation(value = "表单分页列表")
    @GetMapping("/pageList")
    @RequiresPermissions({"skrivet:form:basic", "skrivet:form:list", "skrivet:backstage"})
    public ResponseJson<List<FormListVP>> pageList(@Valid FormSelectPageVQ entity, LoginUser loginUser) {
        PageList<FormListSP> page = formService.selectPageList(entityConvert.convert(entity, FormSelectPageSQ.class), loginUser);
        List<FormListVP> dictListVPList = entityConvert.convertList(page.getData(), FormListVP.class);
        return ResponseBuilder.success(dictListVPList, page.getCount());
    }

    @ApiOperationSupport(order = 8)
    @ResMsg(tag = "所有表单")
    @ResponseBody
    @ApiOperation(value = "所有表单")
    @GetMapping("/selectList")
    @RequiresPermissions({"skrivet:form:basic", "skrivet:form:list", "skrivet:backstage"})
    public ResponseJson<List<FormListVP>> selectList(LoginUser loginUser) {
        return ResponseBuilder.success(entityConvert.convertList(formService.selectList(loginUser), FormListVP.class));
    }

    @ApiOperationSupport(order = 9)
    @ResMsg(tag = "表单列数据")
    @ResponseBody
    @ApiOperation(value = "表单列数据")
    @GetMapping("/selectColumnsByFormId")
    @RequiresPermissions({"skrivet:form:basic", "skrivet:form:list", "skrivet:backstage"})
    public ResponseJson<List<ColumnVP>> selectColumnsByFormId(
            @NotNull(message = "数据编号不能为空")
            @ApiParam(value = "数据编号", required = true)
                    String id, LoginUser loginUser) {
        return ResponseBuilder.success(entityConvert.convertList(formService.selectColumns(id, loginUser), ColumnVP.class));
    }

    @ApiOperationSupport(order = 10)
    @ResMsg(tag = "表单字典")
    @ResponseBody
    @ApiOperation(value = "表单字典")
    @GetMapping("/selectFormDict")
    @RequiresPermissions({"skrivet:form:basic", "skrivet:form:list", "skrivet:backstage"})
    public ResponseJson<List<FormDictVP>> selectFormDict(LoginUser loginUser) {
        return ResponseBuilder.success(entityConvert.convertList(formService.selectFormDict(loginUser), FormDictVP.class));
    }

    @ApiOperationSupport(order = 11)
    @ResMsg(tag = "表单详情")
    @ResponseBody
    @ApiOperation(value = "表单详情")
    @GetMapping("/selectOneById")
    @RequiresPermissions({"skrivet:form:basic", "skrivet:form:detail", "skrivet:backstage"})
    public ResponseJson<FormDetailVP> selectOneById(
            @NotNull(message = "数据编号不能为空")
            @ApiParam(value = "数据编号", required = true)
                    String id, LoginUser loginUser) {
        return ResponseBuilder.success(entityConvert.convert(formService.selectOneById(id, loginUser), FormDetailVP.class));
    }

    @ApiOperationSupport(order = 12)
    @ResMsg(tag = "表单数据")
    @ResponseBody
    @ApiOperation(value = "表单数据")
    @GetMapping("/obtainFormData")
    @RequiresPermissions({"skrivet:form:basic", "skrivet:backstage"})
    public ResponseJson<Map<String, Object>> obtainFormData(
            @NotNull(message = "数据编号不能为空")
            @ApiParam(value = "数据编号", required = true)
                    String id, LoginUser loginUser) {
        return ResponseBuilder.success(formService.obtainFormData(id, loginUser));
    }
    @ApiOperationSupport(order = 14)
    @ResMsg(tag = "导入数据库表")
    @ResponseBody
    @ApiOperation(value = "导入数据库表")
    @GetMapping("/importTable")
    @RequiresPermissions({"skrivet:form:basic", "skrivet:form:update","skrivet:backstage"})
    public ResponseJson<Boolean> importTable(
            @NotNull(message = "数据表不能为空")
            @ApiParam(value = "数据表", required = true)
                    String table, LoginUser loginUser) {
        return ResponseBuilder.success(formService.importTable(table,loginUser));
    }
    @ApiOperationSupport(order = 13)
    @ResMsg(tag = "代码生成")
    @ResponseBody
    @ApiOperation(value = "代码生成")
    @GetMapping("/genCode/{id}")
    @RequiresPermissions({"skrivet:form:basic", "skrivet:backstage"})
    public void genCode(@PathVariable("id") String id, HttpServletResponse response) {
        try {
            formService.initTable(id);
            Map<String, Object> data = formService.obtainFormData(id, LoginUser.IGNORED);
            List<TemplateListSP> templates = templateService.selectByGroupId(data.get("template").toString(), LoginUser.IGNORED);
            if (data != null) {
                ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
                List<TemplateDetailSP> details = new ArrayList<>();
                for (TemplateListSP template : templates) {
                    details.add(templateService.selectOneById(template.getId(), LoginUser.IGNORED));
                }
                for(TemplateDetailSP detail:details){
                    detail.setTemplateData(StringEscapeUtils.unescapeHtml4(detail.getTemplateData()));
                }
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;filename="+data.get("nameCn")+".zip");
                for (TemplateDetailSP template : details) {
                    if (template.getScope().equals("main")) {
                        writeToZip(zos, data, template);
                    }
                }
                List<Map<String, Object>> children = (List<Map<String, Object>>) data.get("children");
                if (!CollectionUtils.isEmpty(children)) {
                    for (Map<String, Object> child : children) {
                        formService.initTable(child.get("id").toString());
                        for (TemplateDetailSP template : details) {
                            if (template.getScope().equals("children")) {
                                writeToZip(zos, child, template);
                            }
                        }
                    }
                }
                zos.close();
            } else {
                throw new DataNotExistExp();
            }
        } catch (Exception e) {
            try {
                response.setStatus(500);
                response.setContentType("text/html; charset=utf-8");
                response.getOutputStream().write(ExceptionToolkit.getStackMsg(e).getBytes("UTF-8"));
                response.getOutputStream().flush();
                response.getOutputStream().close();
            } catch (Exception b) {

            }

        }
    }

    private void writeToZip(ZipOutputStream zos, Map<String, Object> data, TemplateDetailSP template) throws Exception {
        String path = templateService.renderTemplate(data, (template.getTemplateType() == 1 ? "front" : "background") + template.getPath(), LoginUser.IGNORED);
        ZipEntry entry = new ZipEntry(path);
        zos.putNextEntry(entry);
        zos.write(templateService.renderTemplate(data, template.getTemplateData(), LoginUser.IGNORED).getBytes("UTF-8"));
    }

}
