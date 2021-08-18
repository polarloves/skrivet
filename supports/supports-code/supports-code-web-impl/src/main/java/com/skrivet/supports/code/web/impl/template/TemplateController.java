package com.skrivet.supports.code.web.impl.template;

import com.skrivet.core.common.annotations.DynamicDataBase;
import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.entity.PageList;
import com.skrivet.plugins.security.core.annotations.RequiresPermissions;
import com.skrivet.plugins.web.impl.annotation.SkrivetDoc;
import com.skrivet.plugins.web.impl.controller.BasicController;
import com.skrivet.supports.code.service.core.form.FormService;
import com.skrivet.supports.code.service.core.template.TemplateService;
import com.skrivet.supports.code.service.core.template.entity.add.TemplateAddSQ;
import com.skrivet.supports.code.service.core.template.entity.select.TemplateListSP;
import com.skrivet.supports.code.service.core.template.entity.select.TemplateSelectPageSQ;
import com.skrivet.supports.code.service.core.template.entity.update.TemplateUpdateSQ;
import com.skrivet.supports.code.web.core.template.entity.add.TemplateAddVQ;
import com.skrivet.supports.code.web.core.template.entity.select.TemplateDetailVP;
import com.skrivet.supports.code.web.core.template.entity.select.TemplateGroupVP;
import com.skrivet.supports.code.web.core.template.entity.select.TemplateListVP;
import com.skrivet.supports.code.web.core.template.entity.select.TemplateSelectPageVQ;
import com.skrivet.supports.code.web.core.template.entity.update.TemplateUpdateVQ;
import com.skrivet.web.core.annotations.AuthMappingIgnore;
import com.skrivet.web.core.annotations.ResMsg;
import com.skrivet.web.core.entity.AuthMapping;
import com.skrivet.web.core.entity.ResponseBuilder;
import com.skrivet.web.core.entity.ResponseJson;
import io.swagger.annotations.*;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@SkrivetDoc
@Controller
@RequestMapping("/code/template")
@ApiSort(value = 15)
@DynamicDataBase
@Api(tags = "代码生成器模板")
public class TemplateController extends BasicController {
    @Autowired
    private TemplateService templateService;
    @Autowired
    private FormService formService;

    @ApiOperationSupport(order = 1)
    @ResMsg(tag = "模板所有接口所需权限")
    @ResponseBody
    @ApiOperation(value = "模板所有接口所需权限")
    @GetMapping("/permissionList")
    @AuthMappingIgnore
    public ResponseJson<List<AuthMapping>> permissionList() {
        return ResponseBuilder.success(authMappingList());
    }

    @ApiOperationSupport(order = 2)
    @ResMsg(tag = "添加模板")
    @ResponseBody
    @ApiOperation(value = "添加模板")
    @PutMapping("/insert")
    @RequiresPermissions({"skrivet:template:basic", "skrivet:template:add", "skrivet:backstage"})
    public ResponseJson<String> insert(@Valid @RequestBody TemplateAddVQ entity, LoginUser loginUser) {
        TemplateAddSQ parameter = entityConvert.convert(entity, TemplateAddSQ.class);
        String id = templateService.insert(parameter, loginUser);
        return ResponseBuilder.success(id);
    }

    @ApiOperationSupport(order = 3)
    @ResMsg(tag = "删除模板")
    @ResponseBody
    @ApiOperation(value = "删除模板")
    @DeleteMapping("/delete")
    @RequiresPermissions({"skrivet:template:basic", "skrivet:template:delete", "skrivet:backstage"})
    public ResponseJson<Long> delete(
            @NotNull(message = "数据编号不能为空")
            @ApiParam(value = "数据编号", required = true)
                    String id,
            LoginUser loginUser) {
        return ResponseBuilder.success(templateService.deleteById(id, loginUser));
    }


    @ApiOperationSupport(order = 4)
    @ResMsg(tag = "批量删除模板")
    @ResponseBody
    @ApiOperation(value = "批量删除模板")
    @DeleteMapping("/deleteMulti")
    @RequiresPermissions({"skrivet:template:basic", "skrivet:template:delete", "skrivet:backstage"})
    public ResponseJson<Long> deleteMulti(
            @NotNull(message = "数据编号不能为空")
            @ApiParam(value = "数据编号", required = true)
                    String[] ids, LoginUser loginUser) {
        return ResponseBuilder.success(templateService.deleteMultiById(ids, loginUser));
    }

    @ApiOperationSupport(order = 5)
    @ResMsg(tag = "修改模板")
    @ResponseBody
    @ApiOperation(value = "修改模板")
    @PutMapping("/update")
    @RequiresPermissions({"skrivet:template:basic", "skrivet:template:update", "skrivet:backstage"})
    public ResponseJson<Long> update(@Valid @RequestBody TemplateUpdateVQ entity, LoginUser loginUser) {
        TemplateUpdateSQ parameter = entityConvert.convert(entity, TemplateUpdateSQ.class);
        return ResponseBuilder.success(templateService.update(parameter, loginUser));
    }

    @ApiOperationSupport(order = 6)
    @ResMsg(tag = "修改模板数据")
    @ResponseBody
    @ApiOperation(value = "修改模板数据")
    @PutMapping("/updateTemplateData")
    @RequiresPermissions({"skrivet:template:basic", "skrivet:template:update", "skrivet:backstage"})
    public ResponseJson<Long> updateTemplateData(@NotNull(message = "数据编号不能为空")
                                                 @ApiParam(value = "数据编号", required = true)
                                                         String id,
                                                 @NotNull(message = "模板数据不能为空")
                                                 @ApiParam(value = "模板数据", required = true)
                                                         String data, LoginUser loginUser) {
        return ResponseBuilder.success(templateService.updateTemplateData(id, data));
    }


    @ApiOperationSupport(order = 7)
    @ResMsg(tag = "模板分页列表")
    @ResponseBody
    @ApiOperation(value = "模板分页列表")
    @GetMapping("/pageList")
    @RequiresPermissions({"skrivet:template:basic", "skrivet:template:list", "skrivet:backstage"})
    public ResponseJson<List<TemplateListVP>> pageList(@Valid TemplateSelectPageVQ entity, LoginUser loginUser) {
        PageList<TemplateListSP> page = templateService.selectPageList(entityConvert.convert(entity, TemplateSelectPageSQ.class), loginUser);
        List<TemplateListVP> dictListVPList = entityConvert.convertList(page.getData(), TemplateListVP.class);
        return ResponseBuilder.success(dictListVPList, page.getCount());
    }


    @ApiOperationSupport(order = 8)
    @ResMsg(tag = "模板详情")
    @ResponseBody
    @ApiOperation(value = "模板详情")
    @GetMapping("/selectOneById")
    @RequiresPermissions({"skrivet:template:basic", "skrivet:template:detail", "skrivet:backstage"})
    public ResponseJson<TemplateDetailVP> selectOneById(
            @NotNull(message = "数据编号不能为空")
            @ApiParam(value = "数据编号", required = true)
                    String id, LoginUser loginUser) {
        TemplateDetailVP result = entityConvert.convert(templateService.selectOneById(id, loginUser), TemplateDetailVP.class);
        result.setTemplateData(StringEscapeUtils.unescapeHtml4(result.getTemplateData()));
        return ResponseBuilder.success(result);
    }

    @ApiOperationSupport(order = 9)
    @ResMsg(tag = "模板分组数据")
    @ResponseBody
    @ApiOperation(value = "模板分组数据")
    @GetMapping("/groups")
    @RequiresPermissions({"skrivet:form:basic", "skrivet:backstage"})
    public ResponseJson<List<TemplateGroupVP>> groups(LoginUser loginUser) {
        return ResponseBuilder.success(entityConvert.convertList(templateService.groups(loginUser), TemplateGroupVP.class));
    }

    @ApiOperationSupport(order = 10)
    @ResMsg(tag = "调试模板")
    @ResponseBody
    @ApiOperation(value = "调试模板")
    @PostMapping("/debug")
    @RequiresPermissions({"skrivet:form:basic", "skrivet:backstage"})
    public ResponseJson<String> debug(String templateId, String data, LoginUser loginUser) {
        return ResponseBuilder.success(templateService.renderTemplate(formService.obtainFormData(templateId, loginUser), StringEscapeUtils.unescapeHtml4(data), loginUser));
    }

}
