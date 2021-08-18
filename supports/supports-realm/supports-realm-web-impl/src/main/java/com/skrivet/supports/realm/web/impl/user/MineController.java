package com.skrivet.supports.realm.web.impl.user;

import com.skrivet.core.common.annotations.DynamicDataBase;
import com.skrivet.core.common.annotations.Within;
import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.exception.NotSupportExp;
import com.skrivet.core.common.exception.account.IncorrectPasswordExp;
import com.skrivet.plugins.security.core.annotations.RequiresUser;
import com.skrivet.plugins.security.core.encryption.EncryptionService;
import com.skrivet.plugins.security.core.entity.User;
import com.skrivet.plugins.security.core.service.SecurityService;
import com.skrivet.plugins.web.impl.annotation.SkrivetDoc;
import com.skrivet.plugins.web.impl.controller.BasicController;
import com.skrivet.supports.realm.service.core.user.UserService;
import com.skrivet.supports.realm.service.core.user.entity.update.PasswordUpdateSQ;
import com.skrivet.supports.realm.service.core.user.entity.update.UserUpdateMineSQ;
import com.skrivet.supports.realm.web.core.user.entity.select.UserDetailVP;
import com.skrivet.supports.realm.web.core.user.entity.update.CurrentPasswordUpdateVQ;
import com.skrivet.supports.realm.web.core.user.entity.update.UserUpdateMineVQ;
import com.skrivet.web.core.annotations.ResMsg;
import com.skrivet.web.core.entity.ResponseBuilder;
import com.skrivet.web.core.entity.ResponseJson;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@SkrivetDoc
@Controller
@RequestMapping("/realm/mine")
@DynamicDataBase
@ApiSort(value = 11)
@Api(tags = "个人信息管理")
public class MineController extends BasicController {
    @Autowired
    private UserService userService;
    @Autowired
    private EncryptionService encryptionService;
    @Autowired
    private SecurityService securityService;

    @RequiresUser
    @ApiOperationSupport(order = 1)
    @ResMsg(tag = "修改自己的密码")
    @ResponseBody
    @ApiOperation(value = "修改自己的密码")
    @PutMapping("/changePassword")
    public ResponseJson changePassword(@RequestBody CurrentPasswordUpdateVQ passwordUpdateVQ, LoginUser loginUser) {
        PasswordUpdateSQ parameter = new PasswordUpdateSQ();

        User user = securityService.currentUser();
        if (!encryptionService.encryptionPassword(passwordUpdateVQ.getPassword(), user.getSalt()).equals(user.getPassword())) {
            //判断原始密码是否匹配
            throw new IncorrectPasswordExp("原始密码不正确!");

        }
        //设置新的密码盐值
        parameter.setSalt(encryptionService.generateSalt());
        //设置新的密码
        parameter.setPassword(encryptionService.encryptionPassword(passwordUpdateVQ.getCurrentPassword(), parameter.getSalt()));
        String userId = user.getId();
        parameter.setId(userId);
        userService.updatePassword(parameter, LoginUser.IGNORED);
        //删除用户缓存...
        securityService.removeTokenCache(user.getAccount());
        //踢出在线用户
        try {
            securityService.shotOffByUserId(user.getId(), "用户密码已被变更,请重新登录!");
        } catch (NotSupportExp e) {

        }
        securityService.logout();
        return ResponseBuilder.success();
    }


    @ApiOperationSupport(order = 2)
    @ResMsg(tag = "个人信息")
    @ResponseBody
    @ApiOperation(value = "个人信息")
    @GetMapping("/info")
    @RequiresUser
    public ResponseJson<UserDetailVP> info(LoginUser loginUser) {
        return ResponseBuilder.success(entityConvert.convert(userService.selectOneById(loginUser.getId(), LoginUser.IGNORED), UserDetailVP.class));
    }

    @ApiOperationSupport(order = 3)
    @ResMsg(tag = "修改个人信息")
    @ResponseBody
    @ApiOperation(value = "修改个人信息")
    @PutMapping("/updateAll")
    @RequiresUser
    public ResponseJson<Long> updateAll(@Valid @RequestBody UserUpdateMineVQ entity, LoginUser loginUser) {
        UserUpdateMineSQ parameter = entityConvert.convert(entity, UserUpdateMineSQ.class);
        parameter.setId(loginUser.getId());
        return ResponseBuilder.success(userService.updateMine(parameter, LoginUser.IGNORED));
    }

}
