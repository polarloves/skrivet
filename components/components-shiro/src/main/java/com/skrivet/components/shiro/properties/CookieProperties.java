package com.skrivet.components.shiro.properties;

public class CookieProperties {
    /** 回话id **/
    private String name="sid";
    /** cookie过期时间,单位为秒，-1表示浏览器关闭就过期 **/
    private int maxAge=-1;
    private boolean enable=true;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }
}
