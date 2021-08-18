package com.skrivet.supports.authority.web.impl;

import com.skrivet.core.common.annotations.DynamicDataBase;
import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.plugins.security.core.annotations.RequiresPermissions;
import com.skrivet.plugins.security.core.annotations.RequiresUser;
import com.skrivet.plugins.security.core.entity.ActiveUser;
import com.skrivet.plugins.security.core.service.PermissionSetService;
import com.skrivet.plugins.security.core.service.SecurityService;
import com.skrivet.plugins.web.impl.annotation.SkrivetDoc;
import com.skrivet.plugins.web.impl.controller.BasicController;
import com.skrivet.supports.authority.web.core.PermissionSetVP;
import com.skrivet.web.core.annotations.ResMsg;
import com.skrivet.web.core.entity.ResponseBuilder;
import com.skrivet.web.core.entity.ResponseJson;
import io.swagger.annotations.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotNull;
import java.util.List;

@SkrivetDoc
@Controller
@RequestMapping("/authority/activeUser")
@DynamicDataBase
@ApiSort(value = 10)
@Api(tags = "活跃会话接口")
public class ActiveUserController extends BasicController {
    @Autowired
    private SecurityService securityService;


    @ResMsg(tag = "活跃会话列表")
    @ResponseBody
    @ApiOperation(value = "活跃会话列表")
    @GetMapping("/list")
    @RequiresUser
    @RequiresPermissions({"skrivet:backstage","skrivet:activeUser:list"})
    @ApiImplicitParams(
            value = {
                    @ApiImplicitParam(name = "userId", value = "用户编号", required = true, paramType = "body")
            }
    )
    public ResponseJson<List<ActiveUser>> activeUser(
            @NotNull(message = "用户编号不能为空") String userId) {
        return ResponseBuilder.success(securityService.activeUser(userId));
    }

    @ResMsg(tag = "踢出用户")
    @ResponseBody
    @ApiOperation(value = "踢出用户")
    @PostMapping("/shotOff")
    @RequiresUser
    @RequiresPermissions({"skrivet:backstage","skrivet:activeUser:shotOff"})
    @ApiImplicitParams(
            value = {
                    @ApiImplicitParam(name = "sessionId", value = "会话编号", required = true, paramType = "body"),
                    @ApiImplicitParam(name = "msg", value = "踢出原因", required = true, paramType = "body")
            }
    )
    public ResponseJson shotOffActiveUser(
            @NotNull(message = "会话编号不能为空") String sessionId,
            @NotNull(message = "踢出原因不能为空")
            @Length(max = 500, message = "踢出原因最多500位") String msg) {
        securityService.shotOffBySessionId(sessionId, msg);
        return ResponseBuilder.success();
    }
}
