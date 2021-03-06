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
@Api(tags = "?????????????????????")
public class TemplateController extends BasicController {
    @Autowired
    private TemplateService templateService;
    @Autowired
    private FormService formService;

    @ApiOperationSupport(order = 1)
    @ResMsg(tag = "??????????????????????????????")
    @ResponseBody
    @ApiOperation(value = "??????????????????????????????")
    @GetMapping("/permissionList")
    @AuthMappingIgnore
    public ResponseJson<List<AuthMapping>> permissionList() {
        return ResponseBuilder.success(authMappingList());
    }

    @ApiOperationSupport(order = 2)
    @ResMsg(tag = "????????????")
    @ResponseBody
    @ApiOperation(value = "????????????")
    @PutMapping("/insert")
    @RequiresPermissions({"skrivet:template:basic", "skrivet:template:add", "skrivet:backstage"})
    public ResponseJson<String> insert(@Valid @RequestBody TemplateAddVQ entity, LoginUser loginUser) {
        TemplateAddSQ parameter = entityConvert.convert(entity, TemplateAddSQ.class);
        String id = templateService.insert(parameter, loginUser);
        return ResponseBuilder.success(id);
    }

    @ApiOperationSupport(order = 3)
    @ResMsg(tag = "????????????")
    @ResponseBody
    @ApiOperation(value = "????????????")
    @DeleteMapping("/delete")
    @RequiresPermissions({"skrivet:template:basic", "skrivet:template:delete", "skrivet:backstage"})
    public ResponseJson<Long> delete(
            @NotNull(message = "????????????????????????")
            @ApiParam(value = "????????????", required = true)
                    String id,
            LoginUser loginUser) {
        return ResponseBuilder.success(templateService.deleteById(id, loginUser));
    }


    @ApiOperationSupport(order = 4)
    @ResMsg(tag = "??????????????????")
    @ResponseBody
    @ApiOperation(value = "??????????????????")
    @DeleteMapping("/deleteMulti")
    @RequiresPermissions({"skrivet:template:basic", "skrivet:template:delete", "skrivet:backstage"})
    public ResponseJson<Long> deleteMulti(
            @NotNull(message = "????????????????????????")
            @ApiParam(value = "????????????", required = true)
                    String[] ids, LoginUser loginUser) {
        return ResponseBuilder.success(templateService.deleteMultiById(ids, loginUser));
    }

    @ApiOperationSupport(order = 5)
    @ResMsg(tag = "????????????")
    @ResponseBody
    @ApiOperation(value = "????????????")
    @PutMapping("/update")
    @RequiresPermissions({"skrivet:template:basic", "skrivet:template:update", "skrivet:backstage"})
    public ResponseJson<Long> update(@Valid @RequestBody TemplateUpdateVQ entity, LoginUser loginUser) {
        TemplateUpdateSQ parameter = entityConvert.convert(entity, TemplateUpdateSQ.class);
        return ResponseBuilder.success(templateService.update(parameter, loginUser));
    }

    @ApiOperationSupport(order = 6)
    @ResMsg(tag = "??????????????????")
    @ResponseBody
    @ApiOperation(value = "??????????????????")
    @PutMapping("/updateTemplateData")
    @RequiresPermissions({"skrivet:template:basic", "skrivet:template:update", "skrivet:backstage"})
    public ResponseJson<Long> updateTemplateData(@NotNull(message = "????????????????????????")
                                                 @ApiParam(value = "????????????", required = true)
                                                         String id,
                                                 @NotNull(message = "????????????????????????")
                                                 @ApiParam(value = "????????????", required = true)
                                                         String data, LoginUser loginUser) {
        return ResponseBuilder.success(templateService.updateTemplateData(id, data));
    }


    @ApiOperationSupport(order = 7)
    @ResMsg(tag = "??????????????????")
    @ResponseBody
    @ApiOperation(value = "??????????????????")
    @GetMapping("/pageList")
    @RequiresPermissions({"skrivet:template:basic", "skrivet:template:list", "skrivet:backstage"})
    public ResponseJson<List<TemplateListVP>> pageList(@Valid TemplateSelectPageVQ entity, LoginUser loginUser) {
        PageList<TemplateListSP> page = templateService.selectPageList(entityConvert.convert(entity, TemplateSelectPageSQ.class), loginUser);
        List<TemplateListVP> dictListVPList = entityConvert.convertList(page.getData(), TemplateListVP.class);
        return ResponseBuilder.success(dictListVPList, page.getCount());
    }


    @ApiOperationSupport(order = 8)
    @ResMsg(tag = "????????????")
    @ResponseBody
    @ApiOperation(value = "????????????")
    @GetMapping("/selectOneById")
    @RequiresPermissions({"skrivet:template:basic", "skrivet:template:detail", "skrivet:backstage"})
    public ResponseJson<TemplateDetailVP> selectOneById(
            @NotNull(message = "????????????????????????")
            @ApiParam(value = "????????????", required = true)
                    String id, LoginUser loginUser) {
        TemplateDetailVP result = entityConvert.convert(templateService.selectOneById(id, loginUser), TemplateDetailVP.class);
        result.setTemplateData(StringEscapeUtils.unescapeHtml4(result.getTemplateData()));
        return ResponseBuilder.success(result);
    }

    @ApiOperationSupport(order = 9)
    @ResMsg(tag = "??????????????????")
    @ResponseBody
    @ApiOperation(value = "??????????????????")
    @GetMapping("/groups")
    @RequiresPermissions({"skrivet:form:basic", "skrivet:backstage"})
    public ResponseJson<List<TemplateGroupVP>> groups(LoginUser loginUser) {
        return ResponseBuilder.success(entityConvert.convertList(templateService.groups(loginUser), TemplateGroupVP.class));
    }

    @ApiOperationSupport(order = 10)
    @ResMsg(tag = "????????????")
    @ResponseBody
    @ApiOperation(value = "????????????")
    @PostMapping("/debug")
    @RequiresPermissions({"skrivet:form:basic", "skrivet:backstage"})
    public ResponseJson<String> debug(String templateId, String data, LoginUser loginUser) {
        return ResponseBuilder.success(templateService.renderTemplate(formService.obtainFormData(templateId, loginUser), StringEscapeUtils.unescapeHtml4(data), loginUser));
    }

}
