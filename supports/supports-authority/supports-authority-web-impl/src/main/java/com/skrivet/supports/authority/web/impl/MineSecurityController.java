package com.skrivet.supports.authority.web.impl;

import com.skrivet.core.common.annotations.DynamicDataBase;
import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.plugins.security.core.annotations.RequiresUser;
import com.skrivet.plugins.security.core.service.PermissionSetService;
import com.skrivet.plugins.web.impl.annotation.SkrivetDoc;
import com.skrivet.plugins.web.impl.controller.BasicController;
import com.skrivet.supports.authority.web.core.PermissionSetVP;
import com.skrivet.web.core.annotations.ResMsg;
import com.skrivet.web.core.entity.ResponseBuilder;
import com.skrivet.web.core.entity.ResponseJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

@SkrivetDoc
@Controller
@RequestMapping("/authority/security")
@DynamicDataBase
@ApiSort(value = 9)
@Api(tags = "我的权限接口")
public class MineSecurityController extends BasicController {
    @Autowired
    private PermissionSetService permissionSetService;

    @ResMsg(tag = "我的权限信息")
    @ResponseBody
    @ApiOperation(value = "我的权限信息")
    @GetMapping("/permissionSet")
    @RequiresUser
    public ResponseJson<PermissionSetVP> permissionSet(LoginUser loginUser) {
        return ResponseBuilder.success(entityConvert.convert(permissionSetService.selectPermissionSetByUserId(loginUser.getId()), PermissionSetVP.class));
    }

}
