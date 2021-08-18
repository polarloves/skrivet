package com.skrivet.core.common.convert.impl;

import com.skrivet.core.common.convert.CodeConvert;
import com.skrivet.core.common.convert.ConvertTemplate;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import com.skrivet.core.common.enums.Code;

public class CodeConvertImpl implements CodeConvert {
    private Map<String, ConvertTemplate> convertTemplateMap = new HashMap<>();
    private String unknown;

    public CodeConvertImpl() {
        setUnknown("服务器异常,请重试!");
        add(Code.SUCCESS.getCode(), null, "成功");
        add(Code.COMMON.getCode(), null, "操作异常");
        add(Code.COMMON_VALIDATION.getCode(), null, "非法的数据!");
        add(Code.COMMON_DATA_NOT_EXIST.getCode(), "{0}不存在", "数据不存在");
        add(Code.COMMON_DATA_EXIST.getCode(), "{0}已存在", "数据不存在");
        add(Code.COMMON_NOT_NULL.getCode(), "{0}不能为空", "数据不能为空");
        add(Code.ACCOUNT_NOT_LOGIN.getCode(), "用户{0}未登录", "用户未登录");
        add(Code.ACCOUNT_UNKNOWN.getCode(), "用户{0}不存在", "用户不存在");
        add(Code.ACCOUNT_DISABLE.getCode(), "账号已被禁用,禁用原因:{0}", "账号已被禁用");
        add(Code.ACCOUNT_LOCKED.getCode(), "用户已被锁定,将于{0}分钟内解锁", "用户已被锁定");
        add(Code.ACCOUNT_DELETE.getCode(), "用户{0}已被删除", "用户已被删除");
        add(Code.ACCOUNT_INCORRECT_CREDENTIALS.getCode(), "用户{0}密码不正确", "用户密码不正确");
        add(Code.ACCOUNT_FORCE_DOWN_LINE.getCode(), "{0}", "账号在另外一台设备上登录，您已被强制下线!");
        add(Code.ACCOUNT_PERMISSION_DENIED.getCode(), "用户{0}权限不足", "权限不足");
        add(Code.ACCOUNT_INCORRECT_PASSWORD.getCode(), "{0}", "密码不正确");
        add(Code.ACCOUNT_RETRY_LIMIT.getCode(), " 账号密码不匹配，您还有{0}次机会重新输入!", "账号密码不匹配");
        add(Code.ACCOUNT_AUTH_EXPIRE.getCode(), "{0}", "用户身份认证已过期,请重新登录！");
        add(Code.SERVER_BEAN_CONVERT.getCode(), "{0}转换失败", "转换失败");
        add(Code.SERVER_JSON_CONVERT.getCode(), "{0}转换失败", "转换失败");
        add(Code.ILLEGAL_TOKEN_EXPIRE.getCode(), "非法的用户凭证", "非法的用户凭证");
        add(Code.SERVER_CACHE.getCode(), null, "缓存异常");
        add(Code.SERVER_NOT_SUPPORT.getCode(), "不支持{0}", "此选项不被支持");
        add(Code.SERVER_SERIALIZATION.getCode(), "序列化{0}错误", "序列化异常");
        add(Code.SERVER_UNKNOWN.getCode(), null, "服务器异常,请重试");
    }


    @Override
    public String convert(String code, String template, Serializable[] variables) {
        if (StringUtils.isEmpty(template)) {
            ConvertTemplate convertTemplate = convertTemplateMap.get(code);
            if (convertTemplate == null) {
                return unknown;
            }
            if (StringUtils.isEmpty(convertTemplate.messageTemplate) || variables == null || variables.length == 0) {
                return convertTemplate.defaultMessage;
            }
            MessageFormat mf = new MessageFormat(convertTemplate.messageTemplate);
            return mf.format(variables);
        }
        MessageFormat mf = new MessageFormat(template);
        return mf.format(variables);
    }

    @Override
    public Map<String, ConvertTemplate> codes() {
        return convertTemplateMap;
    }

    public void add(String code, String template, String defaultMessage) {
        convertTemplateMap.put(code, new ConvertTemplate(defaultMessage, template));
    }

    public void setUnknown(String unknown) {
        this.unknown = unknown;
    }

}
