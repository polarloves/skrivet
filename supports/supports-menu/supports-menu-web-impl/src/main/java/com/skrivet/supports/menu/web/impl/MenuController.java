package com.skrivet.supports.menu.web.impl;

import com.skrivet.core.common.annotations.DynamicDataBase;
import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.entity.PageList;
import com.skrivet.plugins.security.core.annotations.RequiresPermissions;
import com.skrivet.plugins.security.core.annotations.RequiresUser;
import com.skrivet.plugins.web.impl.annotation.SkrivetDoc;
import com.skrivet.plugins.web.impl.controller.BasicController;
import com.skrivet.supports.menu.service.core.item.MenuItemService;
import com.skrivet.supports.menu.service.core.item.entity.select.MenuItemListSP;
import com.skrivet.supports.menu.service.core.template.MenuTemplateService;
import com.skrivet.supports.menu.service.core.template.entity.select.MenuTemplateListSP;
import com.skrivet.supports.menu.service.core.template.entity.select.MenuTemplateSelectPageSQ;
import com.skrivet.supports.menu.web.core.entity.item.select.MenuItemListVP;
import com.skrivet.supports.menu.web.core.entity.template.select.MenuTemplateListVP;
import com.skrivet.supports.menu.web.core.entity.template.select.MenuTemplateSelectPageVQ;
import com.skrivet.web.core.annotations.ResMsg;
import com.skrivet.web.core.entity.ResponseBuilder;
import com.skrivet.web.core.entity.ResponseJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@SkrivetDoc
@Controller
@RequestMapping("/menu/mine")
@ApiSort(value = 110)
@DynamicDataBase
@Api(tags = "菜单接口")
public class MenuController extends BasicController {
    @Autowired
    private MenuTemplateService menuTemplateService;
    @Autowired
    private MenuItemService menuItemService;

    @ApiOperationSupport(order = 1)
    @ResMsg(tag = "我的菜单")
    @ResponseBody
    @ApiOperation(value = "我的菜单")
    @GetMapping("/list")
    @RequiresUser
    public ResponseJson<List<MenuItemListVP>> myMenu(LoginUser loginUser) {
        String menuId = menuTemplateService.userTemplate(loginUser.getId(), LoginUser.IGNORED);
        if (StringUtils.isEmpty(menuId)) {
            menuId = menuTemplateService.defaultMenuTemplateId();
        }
        List<String> templateIds = null;
        if (!StringUtils.isEmpty(menuId)) {
            templateIds = menuItemService.templateItemIds(menuId, LoginUser.IGNORED);
        }
        List<MenuItemListSP> allMenus = menuItemService.selectList(LoginUser.IGNORED);
        List<MenuItemListVP> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(templateIds)) {
            List<String> finalTemplateIds = templateIds;
            allMenus.stream().forEach(menuItemListSP -> {
                boolean matched = false;
                for (String id : finalTemplateIds) {
                    if (menuItemListSP.getId().equals(id)) {
                        matched = true;
                        break;
                    }
                }
                if (matched) {
                    result.add(entityConvert.convert(menuItemListSP, MenuItemListVP.class));
                }
            });
        }
        return ResponseBuilder.success(result);
    }

}
