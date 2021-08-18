package com.skrivet.core.common.config;

public class Regexps {
    public static final String REG_LETTERS=  "^[A-Za-z0-9]+$"; //英文/数字
    public static final String REG_PHONE=  "^1\\d{10}$"; //手机号的正则表达式
    public static final String REG_EMAIL="(^$)|^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$"; //邮箱的正则表达式
    public static final String REG_IDCARD="(^$)|(^\\d{15}$)|(^\\d{17}(x|X|\\d)$)"; //身份证号的正则表达式
}
