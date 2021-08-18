package com.skrivet.supports.menu.web.impl.item;

import com.skrivet.core.common.annotations.DynamicDataBase;
import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.plugins.security.core.annotations.RequiresPermissions;
import com.skrivet.plugins.web.impl.annotation.SkrivetDoc;
import com.skrivet.plugins.web.impl.controller.BasicController;

import com.skrivet.supports.menu.service.core.item.MenuItemService;
import com.skrivet.supports.menu.service.core.item.entity.add.MenuItemAddSQ;
import com.skrivet.supports.menu.service.core.item.entity.select.MenuItemListSP;
import com.skrivet.supports.menu.service.core.item.entity.update.MenuItemUpdateSQ;
import com.skrivet.supports.menu.web.core.entity.item.add.MenuItemAddVQ;
import com.skrivet.supports.menu.web.core.entity.item.select.*;
import com.skrivet.supports.menu.web.core.entity.item.update.MenuItemUpdateVQ;
import com.skrivet.web.core.annotations.AuthMappingIgnore;
import com.skrivet.web.core.annotations.ResMsg;
import com.skrivet.web.core.entity.AuthMapping;
import com.skrivet.web.core.entity.ResponseBuilder;
import com.skrivet.web.core.entity.ResponseJson;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@SkrivetDoc
@Controller
@RequestMapping("/menu/item")
@ApiSort(value = 101)
@DynamicDataBase
@Api(tags = "菜单项接口")
public class MenuItemController extends BasicController {
    @Autowired
    private MenuItemService menuItemService;

    @ApiOperationSupport(order = 1)
    @ResMsg(tag = "菜单项模块所有接口所需权限")
    @ResponseBody
    @ApiOperation(value = "菜单项模块所有接口所需权限")
    @GetMapping("/permissionList")
    @AuthMappingIgnore
    public ResponseJson<List<AuthMapping>> permissionList() {
        return ResponseBuilder.success(authMappingList());
    }

    @ApiOperationSupport(order = 2)
    @ResMsg(tag = "添加菜单项")
    @ResponseBody
    @ApiOperation(value = "添加菜单项")
    @PutMapping("/insert")
    @RequiresPermissions({"skrivet:menuItem:basic", "skrivet:menuItem:add", "skrivet:backstage"})
    public ResponseJson<String> insert(@Valid @RequestBody MenuItemAddVQ entity, LoginUser loginUser) {
        MenuItemAddSQ parameter = entityConvert.convert(entity, MenuItemAddSQ.class);
        String id = menuItemService.insert(parameter, loginUser);
        return ResponseBuilder.success(id);
    }

    @ApiOperationSupport(order = 3)
    @ResMsg(tag = "删除菜单项")
    @ResponseBody
    @ApiOperation(value = "删除菜单项")
    @DeleteMapping("/delete")
    @RequiresPermissions({"skrivet:menuItem:basic", "skrivet:menuItem:delete", "skrivet:backstage"})
    public ResponseJson<Long> delete(
            @NotNull(message = "编号不能为空")
            @ApiParam(value = "编号", required = true)
                    String id,
            LoginUser loginUser) {
        return ResponseBuilder.success(menuItemService.deleteById(id, loginUser));
    }


    @ApiOperationSupport(order = 5)
    @ResMsg(tag = "修改菜单项")
    @ResponseBody
    @ApiOperation(value = "修改菜单项")
    @PutMapping("/update")
    @RequiresPermissions({"skrivet:menuItem:basic", "skrivet:menuItem:update", "skrivet:backstage"})
    public ResponseJson<Long> update(@Valid @RequestBody MenuItemUpdateVQ entity, LoginUser loginUser) {
        MenuItemUpdateSQ parameter = entityConvert.convert(entity, MenuItemUpdateSQ.class);
        return ResponseBuilder.success(menuItemService.update(parameter, loginUser));
    }


    @ApiOperationSupport(order = 6)
    @ResMsg(tag = "菜单项列表")
    @ResponseBody
    @ApiOperation(value = "菜单项列表")
    @GetMapping("/list")
    @RequiresPermissions({"skrivet:menuItem:basic", "skrivet:menuItem:list", "skrivet:backstage"})
    public ResponseJson<List<MenuItemListVP>> list(LoginUser loginUser) {
        List<MenuItemListSP> list = menuItemService.selectList(loginUser);
        List<MenuItemListVP> menuListVPItemList = entityConvert.convertList(list, MenuItemListVP.class);
        return ResponseBuilder.success(menuListVPItemList);
    }


    @ApiOperationSupport(order = 7)
    @ResMsg(tag = "根据编号查找菜单项详情")
    @ResponseBody
    @ApiOperation(value = "根据编号查找菜单项详情")
    @GetMapping("/selectOneById")
    @RequiresPermissions({"skrivet:menuItem:basic", "skrivet:menuItem:detail", "skrivet:backstage"})
    public ResponseJson<MenuItemDetailVP> selectOneById(
            @NotNull(message = "编号不能为空")
            @ApiParam(value = "编号", required = true)
                    String id, LoginUser loginUser) {
        return ResponseBuilder.success(entityConvert.convert(menuItemService.selectOneById(id, loginUser), MenuItemDetailVP.class));
    }


    @ApiOperationSupport(order = 8)
    @ResMsg(tag = "模板菜单项列表")
    @ResponseBody
    @ApiOperation(value = "模板菜单项列表")
    @GetMapping("/selectTemplateItemIds")
    @RequiresPermissions(value = {"skrivet:menuItem:template:list", "skrivet:backstage"})
    public ResponseJson<List<String>> selectTemplateItemIds(
            @ApiParam(value = "模板编号", required = true)
            @NotNull(message = "模板编号不能为空")
                    String templateId,
            LoginUser loginUser) {
        List<String> items = menuItemService.templateItemIds(templateId, loginUser);
        return ResponseBuilder.success(items);
    }

    @ApiOperationSupport(order = 9)
    @ResMsg(tag = "修改模板菜单")
    @ResponseBody
    @ApiOperation(value = "修改模板菜单")
    @PutMapping("/updateTemplateItems")
    @RequiresPermissions(value = {"skrivet:menuItem:template:update", "skrivet:backstage"})
    public ResponseJson updateTemplateItems(
            @ApiParam(value = "模板编号", required = true)
            @NotNull(message = "模板编号不能为空")
                    String templateId,
            @ApiParam(value = "菜单项编号", required = true)
                    String[] itemIds,
            LoginUser loginUser) {
        menuItemService.updateTemplateItems(templateId, itemIds, loginUser);
        return ResponseBuilder.success();
    }
}