package com.skrivet.core.common.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 树结构实体类
 *
 * @author n
 * @version 1.0
 */
public class TreeEntity implements Serializable {
    private static final long serialVersionUID = 2622248783118979948L;
    /**
     * 主键编号
     */
    private String id;
    /**
     * 父级编号，如果为空，则表示其为根节点
     */
    private String parentId;
    /**
     * 此节点展示的名称
     */
    private String text;
    /**
     * 此节点的值
     */
    private String value;
    /**
     * 排序号，在同一级中有效
     */
    private Long orderNum;
    /**
     * 子节点信息
     */
    private List<TreeEntity> children;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

    public List<TreeEntity> getChildren() {
        return children;
    }

    public void setChildren(List<TreeEntity> children) {
        this.children = children;
    }
}
