package com.skrivet.components.shiro.properties;

public class SessionProperties {
    /** 是否删除无效的session **/
    private boolean deleteInvalid=true;
    /** 会话超时时间 **/
    private long timeout=30*60*1000;
    // session 存储key
    private String cacheName="shiroSession";


    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public boolean isDeleteInvalid() {
        return deleteInvalid;
    }

    public void setDeleteInvalid(boolean deleteInvalid) {
        this.deleteInvalid = deleteInvalid;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
