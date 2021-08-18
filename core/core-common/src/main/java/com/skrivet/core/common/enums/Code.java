package com.skrivet.core.common.enums;

public enum Code {
    SUCCESS("000000"),
    //普通的异常,由数据等原因引起的异常
    COMMON("000001"),
    COMMON_VALIDATION("000002"),
    COMMON_DATA_NOT_EXIST("000003"),
    COMMON_DATA_EXIST("000004"),
    COMMON_NOT_NULL("000005"),
    //由用户账号引起的异常
    ACCOUNT_NOT_LOGIN("100001"),
    ACCOUNT_UNKNOWN("100002"),
    ACCOUNT_LOCKED("100003"),
    ACCOUNT_DELETE("100004"),
    ACCOUNT_DISABLE("100005"),
    ACCOUNT_INCORRECT_CREDENTIALS("100006"),
    ACCOUNT_FORCE_DOWN_LINE("100007"),
    ACCOUNT_PERMISSION_DENIED("100008"),
    ACCOUNT_RETRY_LIMIT("100009"),
    ACCOUNT_INCORRECT_PASSWORD("100010"),
    ACCOUNT_AUTH_EXPIRE("100011"),
    ILLEGAL_TOKEN_EXPIRE("100012"),
    //服务器器的异常，因代码造成的系统问题
    SERVER_BEAN_CONVERT("900001"),
    SERVER_JSON_CONVERT("900002"),
    SERVER_CACHE("900003"),
    SERVER_NOT_SUPPORT("900004"),
    SERVER_SERIALIZATION("900005"),
    SERVER_UNKNOWN("999999");


    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    Code(String code) {
        this.code = code;
    }

}
