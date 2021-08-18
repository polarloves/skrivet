package com.skrivet.components.shiro.properties;

//账号配置
public class AccountProperties {
    //密码重试次数，>0时生效
    private int passwordRetryCount = -1;
    // 密码重试过多时，锁定时间，单位毫秒
    private long accountLockTime;
    //最多同时在线用户，-1表示不限制
    private int maxAccount = -1;

    public int getMaxAccount() {
        return maxAccount;
    }

    public void setMaxAccount(int maxAccount) {
        this.maxAccount = maxAccount;
    }

    public int getPasswordRetryCount() {
        return passwordRetryCount;
    }

    public void setPasswordRetryCount(int passwordRetryCount) {
        this.passwordRetryCount = passwordRetryCount;
    }

    public long getAccountLockTime() {
        return accountLockTime;
    }

    public void setAccountLockTime(long accountLockTime) {
        this.accountLockTime = accountLockTime;
    }
}
