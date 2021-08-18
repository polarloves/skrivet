package com.skrivet.supports.menu.web.impl.template;

import com.skrivet.core.common.annotations.DynamicDataBase;
import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.entity.PageList;
import com.skrivet.plugins.security.core.annotations.RequiresPermissions;
import com.skrivet.plugins.web.impl.annotation.SkrivetDoc;
import com.skrivet.plugins.web.impl.controller.BasicController;

import com.skrivet.supports.menu.service.core.template.MenuTemplateService;
import com.skrivet.supports.menu.service.core.template.entity.add.MenuTemplateAddSQ;
import com.skrivet.supports.menu.service.core.template.entity.select.MenuTemplateListSP;
import com.skrivet.supports.menu.service.core.template.entity.select.MenuTemplateSelectPageSQ;
import com.skrivet.supports.menu.service.core.template.entity.update.MenuTemplateUpdateSQ;
import com.skrivet.supports.menu.web.core.entity.template.add.MenuTemplateAddVQ;
import com.skrivet.supports.menu.web.core.entity.template.select.*;
import com.skrivet.supports.menu.web.core.entity.template.update.MenuTemplateUpdateVQ;
import com.skrivet.web.core.annotations.AuthMappingIgnore;
import com.skrivet.web.core.annotations.ResMsg;
import com.skrivet.web.core.entity.AuthMapping;
import com.skrivet.web.core.entity.ResponseBuilder;
import com.skrivet.web.core.entity.ResponseJson;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@SkrivetDoc
@Controller
@RequestMapping("/menu/template")
@ApiSort(value = 101)
@DynamicDataBase
@Api(tags = "菜单模板接口")
public class MenuTemplateController extends BasicController {
    @Autowired
    private MenuTemplateService menuTemplateService;

    @ApiOperationSupport(order = 1)
    @ResMsg(tag = "菜单模板模块所有接口所需权限")
    @ResponseBody
    @ApiOperation(value = "菜单模板模块所有接口所需权限")
    @GetMapping("/permissionList")
    @AuthMappingIgnore
    public ResponseJson<List<AuthMapping>> permissionList() {
        return ResponseBuilder.success(authMappingList());
    }

    @ApiOperationSupport(order = 2)
    @ResMsg(tag = "添加菜单模板")
    @ResponseBody
    @ApiOperation(value = "添加菜单模板")
    @PutMapping("/insert")
    @RequiresPermissions({"skrivet:menuTemplate:basic", "skrivet:menuTemplate:add", "skrivet:backstage"})
    public ResponseJson<String> insert(@Valid @RequestBody MenuTemplateAddVQ entity, LoginUser loginUser) {
        MenuTemplateAddSQ parameter = entityConvert.convert(entity, MenuTemplateAddSQ.class);
        String id = menuTemplateService.insert(parameter, loginUser);
        return ResponseBuilder.success(id);
    }

    @ApiOperationSupport(order = 3)
    @ResMsg(tag = "删除菜单模板")
    @ResponseBody
    @ApiOperation(value = "删除菜单模板")
    @DeleteMapping("/delete")
    @RequiresPermissions({"skrivet:menuTemplate:basic", "skrivet:menuTemplate:delete", "skrivet:backstage"})
    public ResponseJson<Long> delete(
            @NotNull(message = "主键编号不能为空")
            @ApiParam(value = "主键编号", required = true)
                    String id,
            LoginUser loginUser) {
        return ResponseBuilder.success(menuTemplateService.deleteById(id, loginUser));
    }


    @ApiOperationSupport(order = 4)
    @ResMsg(tag = "批量删除菜单模板")
    @ResponseBody
    @ApiOperation(value = "批量删除菜单模板")
    @DeleteMapping("/deleteMulti")
    @RequiresPermissions({"skrivet:menuTemplate:basic", "skrivet:menuTemplate:delete", "skrivet:backstage"})
    public ResponseJson<Long> deleteMulti(
            @NotNull(message = "主键编号不能为空")
            @ApiParam(value = "主键编号", required = true)
                    String[] ids, LoginUser loginUser) {
        return ResponseBuilder.success(menuTemplateService.deleteMultiById(ids, loginUser));
    }

    @ApiOperationSupport(order = 5)
    @ResMsg(tag = "修改菜单模板")
    @ResponseBody
    @ApiOperation(value = "修改菜单模板")
    @PutMapping("/update")
    @RequiresPermissions({"skrivet:menuTemplate:basic", "skrivet:menuTemplate:update", "skrivet:backstage"})
    public ResponseJson<Long> update(@Valid @RequestBody MenuTemplateUpdateVQ entity, LoginUser loginUser) {
        MenuTemplateUpdateSQ parameter = entityConvert.convert(entity, MenuTemplateUpdateSQ.class);
        return ResponseBuilder.success(menuTemplateService.update(parameter, loginUser));
    }


    @ApiOperationSupport(order = 6)
    @ResMsg(tag = "菜单模板分页列表")
    @ResponseBody
    @ApiOperation(value = "菜单模板分页列表")
    @GetMapping("/pageList")
    @RequiresPermissions({"skrivet:menuTemplate:basic", "skrivet:menuTemplate:list", "skrivet:backstage"})
    public ResponseJson<List<MenuTemplateListVP>> pageList(@Valid MenuTemplateSelectPageVQ entity, LoginUser loginUser) {
        PageList<MenuTemplateListSP> page = menuTemplateService.selectPageList(entityConvert.convert(entity, MenuTemplateSelectPageSQ.class), loginUser);
        List<MenuTemplateListVP> menuTemplateListVPList = entityConvert.convertList(page.getData(), MenuTemplateListVP.class);
        return ResponseBuilder.success(menuTemplateListVPList, page.getCount());
    }


    @ApiOperationSupport(order = 7)
    @ResMsg(tag = "根据主键编号查找菜单模板详情")
    @ResponseBody
    @ApiOperation(value = "根据主键编号查找菜单模板详情")
    @GetMapping("/selectOneById")
    @RequiresPermissions({"skrivet:menuTemplate:basic", "skrivet:menuTemplate:detail", "skrivet:backstage"})
    public ResponseJson<MenuTemplateDetailVP> selectOneById(
            @NotNull(message = "主键编号不能为空")
            @ApiParam(value = "主键编号", required = true)
                    String id, LoginUser loginUser) {
        return ResponseBuilder.success(entityConvert.convert(menuTemplateService.selectOneById(id, loginUser), MenuTemplateDetailVP.class));
    }


    @ApiOperationSupport(order = 8)
    @ResMsg(tag = "设置默认模板")
    @ResponseBody
    @ApiOperation(value = "设置默认模板")
    @PutMapping("/setDefault")
    @RequiresPermissions({"skrivet:menuTemplate:basic", "skrivet:menuTemplate:update", "skrivet:backstage"})
    public ResponseJson<Long> setDefault(@NotNull(message = "主键编号不能为空")
                                         @ApiParam(value = "主键编号", required = true)
                                                 String id, LoginUser loginUser) {
        return ResponseBuilder.success(menuTemplateService.setDefault(id, loginUser));
    }

    @ApiOperationSupport(order = 9)
    @ResMsg(tag = "用户菜单模板列表")
    @ResponseBody
    @ApiOperation(value = "用户菜单模板列表")
    @GetMapping("/selectUserTemplate")
    @RequiresPermissions(value = {"skrivet:user:menu", "skrivet:backstage"})
    public ResponseJson<String> selectUserTemplate(
            @ApiParam(value = "用户编号", required = true)
            @NotNull(message = "用户编号不能为空")
                    String userId,
            LoginUser loginUser) {
        String items = menuTemplateService.userTemplate(userId, loginUser);
        return ResponseBuilder.success(items);
    }

    @ApiOperationSupport(order = 10)
    @ResMsg(tag = "修改用户菜单模板")
    @ResponseBody
    @ApiOperation(value = "修改用户菜单模板")
    @PutMapping("/updateUserTemplate")
    @RequiresPermissions(value = {"skrivet:user:menu", "skrivet:backstage"})
    public ResponseJson updateUserTemplate(
            @ApiParam(value = "用户编号", required = true)
            @NotNull(message = "用户编号不能为空")
                    String userId,
            @ApiParam(value = "模板编号", required = true)
                    String templateId,
            LoginUser loginUser) {
        menuTemplateService.updateUserTemplate(userId, templateId, loginUser);
        return ResponseBuilder.success();
    }


    @ApiOperationSupport(order = 11)
    @ResMsg(tag = "所有菜单模板列表")
    @ResponseBody
    @ApiOperation(value = "所有菜单模板列表")
    @GetMapping("/selectList")
    @RequiresPermissions({"skrivet:menuTemplate:basic", "skrivet:menuTemplate:list", "skrivet:backstage"})
    public ResponseJson<List<MenuTemplateListVP>> selectList(LoginUser loginUser) {
        List<MenuTemplateListSP> items = menuTemplateService.selectList(loginUser);
        return ResponseBuilder.success(entityConvert.convertList(items, MenuTemplateListVP.class));
    }
}