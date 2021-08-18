package com.skrivet.core.common.entity;

import java.io.Serializable;

/**
 * 名称-值实体
 *
 * @author n
 * @version 1.0
 */
public class NameValueEntity implements Serializable {
    /**
     * 名称
     */
    private String name;
    /**
     * 实体
     */
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
