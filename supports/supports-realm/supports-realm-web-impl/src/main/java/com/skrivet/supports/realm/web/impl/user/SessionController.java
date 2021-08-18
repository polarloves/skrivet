package com.skrivet.supports.realm.web.impl.user;

import com.skrivet.core.common.annotations.DynamicDataBase;
import com.skrivet.plugins.security.core.annotations.RequiresUser;
import com.skrivet.plugins.security.core.service.SecurityService;
import com.skrivet.plugins.web.impl.annotation.SkrivetDoc;
import com.skrivet.plugins.web.impl.controller.BasicController;
import com.skrivet.plugins.web.impl.util.IpUtil;
import com.skrivet.supports.realm.service.core.user.UserService;
import com.skrivet.web.core.annotations.ResMsg;
import com.skrivet.web.core.entity.ResponseBuilder;
import com.skrivet.web.core.entity.ResponseJson;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

@SkrivetDoc
@Controller
@RequestMapping("/realm/session")
@DynamicDataBase
@ApiSort(value = 12)
@Api(tags = "用户登录管理")
public class SessionController extends BasicController {
    @Autowired
    private UserService userService;
    @Autowired
    private SecurityService securityService;

    @ApiOperationSupport(order = 1)
    @ResMsg(tag = "登录")
    @ResponseBody
    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public ResponseJson<String> login(@NotNull(message = "用户名不能为空")
                                      @ApiParam(value = "用户名", required = true) String userName,
                                      @NotNull(message = "密码不能为空")
                                      @ApiParam(value = "密码", required = true) String password, HttpServletRequest request) {
        String id = securityService.login(userName, password, true, false);
        userService.login(userName, IpUtil.getIpAddr(request));
        return ResponseBuilder.success(id);
    }

    @RequiresUser
    @ApiOperationSupport(order = 62)
    @ResMsg(tag = "退出登录")
    @ResponseBody
    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public ResponseJson logout() {
        securityService.logout();
        return ResponseBuilder.success();
    }
}
